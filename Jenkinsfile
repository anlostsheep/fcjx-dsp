pipeline {
    agent any

    tools {
        maven 'maven'
        jdk 'jdk1.8'
    }

    stages {
        // 测试分支
        stage('Deliver for development') {
            when {
                branch 'dev'
            }
            steps {
                sh '''
                    mvn clean package deploy -DskipTests
                '''
            }
        }

        // 生产分支
        stage('Deliver for production') {
            when {
                branch 'master'
            }
            steps {
                sh '''
                    mvn clean package deploy -DskipTests
                '''
            }
        }
    }
}