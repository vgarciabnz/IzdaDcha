pipeline {
    agent { docker { image 'cryptomator/centos-jdk-ant-x64' } }
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