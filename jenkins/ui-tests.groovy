pipeline {
    agent {
        docker {
            image 'localhost:5005/maven'
            args '--privileged --env-file .env'
        }
    }
    environment {
        MAVEN_LOCAL_REPO = "${WORKSPACE}/.m2/repository"
        ALLURE_RESULTS = "${WORKSPACE}/allure-results"
        ALLURE_REPORT = "${WORKSPACE}/allure-report"
        BUILD_NUMBER = "${env.BUILD_NUMBER}"
        JOB_NAME = "${env.JOB_NAME}"
        DOCKER_HOME = "/home/ubuntu/ui-test"
    }
    parameters {
        text(name: 'YAML_CONFIG', defaultValue: '', description: 'YAML Configuration')
    }
    stages {
        stage('Prepare Environment') {
            steps {
                script {
                    sh 'mkdir -p ${ALLURE_RESULTS} && rm -rf ${ALLURE_RESULTS}/*'
                    sh 'mkdir -p ${ALLURE_REPORT} && rm -rf ${ALLURE_REPORT}/*'

                    def configText = params.YAML_CONFIG
                    def configMap = [:]
                    configText.split('\n').each { line ->
                        def parts = line.split(':', 2)
                        if (parts.length == 2) {
                            configMap[parts[0].trim()] = parts[1].trim()
                        }
                    }

                    env.BASE_URL = configMap['BASE_URL'] ?: ''
                    env.BROWSER_NAME = configMap['BROWSER_NAME'] ?: ''
                    env.IS_REMOTE = configMap['IS_REMOTE'] ?: ''
                    env.REMOTE_URL = configMap['REMOTE_URL'] ?: ''

                    echo "Configuration parsed successfully: BASE_URL=${env.BASE_URL}, BROWSER_NAME=${env.BROWSER_NAME}, REMOTE_URL=${env.REMOTE_URL}"
                }
            }
        }
        stage('Build and Test') {
            steps {
                script {
                    sh '''
                        # Выполнение команд внутри Docker контейнера
                        rm -rf ${DOCKER_HOME}/allure-results/* ${DOCKER_HOME}/allure-report/*
                        mvn clean test -Dmaven.repo.local=${MAVEN_LOCAL_REPO}
                        allure generate ${DOCKER_HOME}/allure-results --clean -o ${DOCKER_HOME}/allure-report
                    '''
                }
            }
        }
    }
    post {
        always {
            script {
                // Генерация отчета Allure с помощью плагина Jenkins
                allure includeProperties: false, jdk: '', reportBuildPolicy: 'ALWAYS', results: [[path: "${ALLURE_RESULTS}"]]
            }
        }
    }
}