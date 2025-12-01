pipeline {
    agent {
        kubernetes {
            yaml """
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: docker
    image: docker:dind
    securityContext:
      privileged: true
    env:
    - name: DOCKER_TLS_CERTDIR
      value: ""
    volumeMounts:
    - name: docker-sock
      mountPath: /var/run/docker.sock
  - name: kubectl
    image: bitnami/kubectl:latest
    command:
    - sleep
    args:
    - "99d"
  volumes:
  - name: docker-sock
    hostPath:
      path: /var/run/docker.sock
  serviceAccountName: jenkins
"""
        }
    }
    
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/your-repo/your-app.git'
            }
        }
        
        stage('Build Docker Image') {
            steps {
                container('docker') {
                    script {
                        sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin <account-id>.dkr.ecr.us-east-1.amazonaws.com'
                        def image = docker.build("<account-id>.dkr.ecr.us-east-1.amazonaws.com/your-app:${env.BUILD_NUMBER}")
                        image.push()
                        image.push("latest")
                    }
                }
            }
        }
        
        stage('Deploy to Kubernetes') {
            steps {
                container('kubectl') {
                    sh '''
                        kubectl set image deployment/your-app your-app=your-app:${BUILD_NUMBER} -n your-namespace
                        kubectl rollout status deployment/your-app -n your-namespace
                    '''
                }
            }
        }
    }
}