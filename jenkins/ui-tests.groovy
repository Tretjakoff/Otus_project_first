timeout(60) {
    node("maven") {
        def testContainerName = "ui_tests_$BUILD_NUMBER"
        try {
            wrap([$class: 'BuildUser']) {
                currentBuild.description = "User: $BUILD_USER"
            }
            stage("Check PATH") {
                sh 'echo $PATH'
            }

            stage("Run tests ${jobDescription}") {
            sh """
                mvn clean test
            """
            }

            stage("Publish allure results") {
            allure([
                    disabled: true,
                    includeProperties: false,
                    reportBuildPolicy: 'ALWAYS',
                    results: ["$pwd/allure-results"]
            ])
        }
    } finally {
        sh "docker stop $testContainerName"
        }
    }
}