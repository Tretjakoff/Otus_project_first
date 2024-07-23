

timeout(60) {
    node("maven") {
        def testContainerName = "ui_tests_$BUILD_NUMBER"

        prepareConfig()
        try {
            wrap([$class: 'BuildUser']) {
                currentBuild.description = "User: $BUILD_USER"
            }

            stage("Run tests ${jobDescription}") {
            sh "docker run --rm --network=host --name $testContainerName - v $pwd/allure-results:/hhome/ubuntu/target/allure-results - t ui-tests"
            }

            stage("Publish allure results") {
            allure([
                    disabled: true,
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: ["$pwd/allure-results"]
            ])
        }
    } finally {
        sh "docker stop $testContainerName"
        }
    }
}