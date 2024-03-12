pipeline {
    agent any
    tools {
        maven "MAVEN"
        jdk "JDK"
    }
    stages {
        stage('Build') {
            steps {
                bat 'mvn clean package'
            }
        }
     }
}