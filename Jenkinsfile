pipeline {
    agent { docker { image 'frekele/java:jdk8u172' } }
    stages {
        stage('Build') {
            steps {
                sh 'ant'
            }
        }
        stage('Test') {
            steps {
                sh 'echo "Hello World"'
            }
        }
    }
}