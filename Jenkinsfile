pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Checkout the source code from the repository
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Run Maven build
                bat 'mvn clean install'
            }
        }

        stage('Deploy') {
            steps {
                // Deploy your application
                bat 'java -jar target\\Xero-0.0.1-SNAPSHOT-shaded.jar'
            }
        }
    }
}
