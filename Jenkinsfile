pipeline {

    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run tests') {
            steps {
                sh 'chmod +x run-tests.sh'
                sh './run-tests.sh'
            }
        }
    }

    post {
        always {
            script {
                try {
                    allure results: [[path: 'allure-results']]
                } catch (Exception e) {
                    echo "Allure report generation failed: ${e.getMessage()}"
                }
            }
        }
    }
}