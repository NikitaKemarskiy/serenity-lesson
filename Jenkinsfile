pipeline {
    agent any

    tools {
        maven "Maven 3.6.3"
    }

    environment {
        API_BASE_URL = credentials('API_BASE_URL')
        API_KEY = credentials('API_KEY')
    }

    stages {
        stage('Testing') {
            steps {
                sh "echo \">>> API BASE URL: $API_BASE_URL\""
                sh "echo \">>> API KEY: $API_KEY\""
                sh "mvn test"
            }

            post {
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
            }
        }
    }
}
