pipeline {
    agent any

    tools {
        maven "Maven 3.6.3"
    }

    stages {
        stage('Testing') {
            steps {
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
