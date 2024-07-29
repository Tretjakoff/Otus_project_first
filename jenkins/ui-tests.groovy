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
        REMOTE_URL = "http://172.26.0.2:4444/wd/hub"
        IS_REMOTE = "true"
        TOKEN = "6537216971:AAE6M7PU0BF1Reie_O9eFYcSP7bB2wEJTgc"
        CHAT_ID = "-1002046571003"
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
                        def parts = line.split(':', 2) // Split only at the first ':'
                        if (parts.length == 2) {
                            configMap[parts[0].trim()] = parts[1].trim()
                        }
                    }

                    env.BASE_URL = configMap['BASE_URL'] ?: ''
                    env.BROWSER_NAME = configMap['BROWSER_NAME'] ?: ''
                    env.BROWSER_VERSION = configMap['BROWSER_VERSION'] ?: ''

                    echo "Configuration parsed successfully: BASE_URL=${env.BASE_URL}, BROWSER_NAME=${env.BROWSER_NAME}, BROWSER_VERSION=${env.BROWSER_VERSION}, REMOTE_URL=${env.REMOTE_URL}, IS_REMOTE=${env.IS_REMOTE}"
                }
            }
        }
        stage('Build and Test') {
            steps {
                script {
                    // Создание временного скрипта с командами
                    writeFile file: 'docker_script.sh', text: """
                        #!/bin/bash
                        rm -rf ${DOCKER_HOME}/allure-results/* ${DOCKER_HOME}/allure-report/*
                        mvn clean test -D=chrome -DremoteUrl=${REMOTE_URL}
                        allure generate ${DOCKER_HOME}/allure-results --clean -o ${DOCKER_HOME}/allure-report
                    """

                    // Запуск временного скрипта внутри текущего контейнера
                    sh """
                        chmod +x docker_script.sh
                        ./docker_script.sh

                        # Копирование содержимого результатов и отчетов
                        cp -r ${DOCKER_HOME}/allure-results/. ${ALLURE_RESULTS}/
                        cp -r ${DOCKER_HOME}/allure-report/. ${ALLURE_REPORT}/
                    """
                }
            }
        }
    }
    post {
        always {
            script {
                // Генерация отчета Allure с помощью плагина Jenkins
                allure includeProperties: false, jdk: '', reportBuildPolicy: 'ALWAYS', results: [[path: "${ALLURE_RESULTS}"]]

                // Подготовка и отправка сообщения в Telegram
                def buildStatus = currentBuild.currentResult
                env.MESSAGE = "${env.JOB_NAME} ${buildStatus.toLowerCase()} for build #${env.BUILD_NUMBER}\n****************************************"
                def allureReportUrl = "${env.BUILD_URL}allure"
                try {
                    def summaryFile = "${ALLURE_REPORT}/widgets/summary.json"
                    if (fileExists(summaryFile)) {
                        def summary = sh(script: "cat ${summaryFile}", returnStdout: true).trim()
                        def jsonSlurper = new groovy.json.JsonSlurper()
                        def summaryJson = jsonSlurper.parseText(summary)
                        def passed = summaryJson.statistic.passed
                        def failed = summaryJson.statistic.failed
                        def skipped = summaryJson.statistic.skipped
                        def total = summaryJson.statistic.total
                        def error = total - passed - failed - skipped
                        env.REPORT_SUMMARY = "Passed: ${passed}, Failed: ${failed}, Error: ${error} , Skipped: ${skipped}\nTotal: ${total}"
                    } else {
                        env.REPORT_SUMMARY = "Summary report not found: ${summaryFile}"
                    }
                } catch (Exception e) {
                    env.REPORT_SUMMARY = "Failed to read Allure report: ${e.message}"
                }
                sh """
                    curl -X POST -H 'Content-Type: application/json' -d '{
                        "chat_id": "${env.CHAT_ID}",
                        "text": "${env.MESSAGE}\\n${env.REPORT_SUMMARY}\\nAllure Report: ${allureReportUrl}",
                        "disable_notification": false
                    }' https://api.telegram.org/bot${env.TOKEN}/sendMessage
                """
            }
        }
    }
}