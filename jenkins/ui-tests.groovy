pipeline {
    agent {
        label 'maven'
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
                        # Запуск Docker контейнера и выполнение команд внутри него
                        CONTAINER_ID=$(docker run --privileged -d  --env-file .env\
                            -v ${MAVEN_LOCAL_REPO}:${MAVEN_LOCAL_REPO} \
                            localhost:5005/maven \
                            /bin/bash -c "rm -rf ${DOCKER_HOME}/allure-results/* ${DOCKER_HOME}/allure-report/* && \
                            mvn clean test -Dmaven.repo.local=${MAVEN_LOCAL_REPO} && \
                            allure generate ${DOCKER_HOME}/allure-results --clean -o ${DOCKER_HOME}/allure-report")
                        
                        # Просмотр логов выполнения тестов
                        docker logs -f $CONTAINER_ID

                        # Копирование содержимого результатов и отчетов из контейнера
                        docker cp $CONTAINER_ID:/home/ubuntu/ui-test/allure-results/. ${ALLURE_RESULTS}/
                        docker cp $CONTAINER_ID:/home/ubuntu/ui-test/allure-report/. ${ALLURE_REPORT}/

                        docker stop $CONTAINER_ID || true
                        docker rm $CONTAINER_ID || true
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