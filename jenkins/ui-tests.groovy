timeout(60) {
    node("maven") {
        def testContainerName = "ui_tests_$BUILD_NUMBER"
        try {
            wrap([$class: 'BuildUser']) {
                currentBuild.description = "User: $BUILD_USER"
            }

            stage("Run tests ${jobDescription}") {
            sh """
                docker run -privileged --rm --network=host \
                -e BROWSER_NAME=$BROWSER_NAME \
                -e SELENOID_URL=http://localhost:8090/wd/hub \
                --name $testContainerName \
                -v $pwd/allure-results:/home/ubuntu/target/allure-results \
                -v $pwd:/home/ubuntu/project \
                -w /home/ubuntu/project \
                -t ui_tests \
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