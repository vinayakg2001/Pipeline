pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                script {
                    // Clean workspace before checking out
                    deleteDir()

                    // Checkout the Git repository
                    checkout([$class: 'GitSCM', branches: [[name: '*/main']], userRemoteConfigs: [[url: 'https://github.com/vinayakg2001/Pipeline.git']]])
                }
            }
        }

        stage('Build and Run') {
            steps {
                script {
                    // Assuming you have a Maven project
                    bat 'mvn clean install'

                    // Replace 'YourMainClass' with the main class of your Java application
//                     bat 'javac TestRunner.java'
//                     bat 'java TestRunner'
                }
            }
        }
    }

    post {
        success {
            echo 'The pipeline has successfully run!'
        }
        failure {
            echo 'The pipeline has failed!'
        }
    }
}
