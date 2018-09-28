pipeline {
    agent { docker { image 'frekele/java:jdk8u172' } }
    stages {
        stage('build') {
            steps {
                sh 'ant'
            }
        }
    }
}