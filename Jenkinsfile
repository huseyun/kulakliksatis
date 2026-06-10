pipeline {
    agent any

    environment {
        // Jenkins'te "Secret text" credential olarak ekleyeceğin ID'ler.
        // docker compose, ${DB_PASSWORD} / ${JWT_SECRET_KEY} için bunları okur.
        DB_PASSWORD    = credentials('kulaklik-db-password')
        JWT_SECRET_KEY = credentials('kulaklik-jwt-secret')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/huseyun/kulakliksatis.git'
            }
        }

        stage('Build') {
            steps {
                sh 'docker compose build'
            }
        }

        stage('Deploy') {
            steps {
                sh 'docker compose down --remove-orphans'
                sh 'docker compose up -d'
            }
        }

        stage('Health Check') {
            steps {
                sh 'sleep 30'
                sh 'curl -fsS http://host.docker.internal/ || echo "Frontend henuz hazir degil"'
            }
        }
    }

    post {
        success { echo 'Deploy basarili: kulakliksatis calisiyor.' }
        failure { echo 'Deploy basarisiz: loglari kontrol et.' }
        always  { sh 'docker image prune -f' }
    }
}