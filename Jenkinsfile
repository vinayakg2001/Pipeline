pipeline {
    agent any
    tools {
        maven "MAVEN"
        jdk "JDK"
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
     }
}