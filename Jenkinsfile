pipeline {
    agent { docker { image 'webratio/ant' } }
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