pipeline {
    agent { docker { image 'frekele/ant:1.10.2-jdk8' } }
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