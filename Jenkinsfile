pipeline {
    agent { docker { image 'frekele/ant' } }
    stages {
        stage('Build') {
            steps {
                sh 'ant -version'
            }
        }
        stage('Test') {
            steps {
                sh 'echo "Hello World"'
            }
        }
    }
}