pipeline {
	options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }
	agent any
	environment {
        V1 = 'default'
    }
    tools { 
        maven 'M3' 
		jdk 'java8'
    }
	stages {
		stage('Compile') {
	        steps{
	            checkout scm
				script {
					GIT_VERSION = sh (script: 'git describe',returnStdout: true).trim()
					withEnv(['V1=' + GIT_VERSION]) {
                        sh "echo $V1"
                    }
                }
				echo "GIT version: ${GIT_VERSION}"
				echo "v1: $V1"
				sh "java -version"
			    sh "mvn -f pom.xml clean compile"
	        }
		}
		stage('Unit Test') {
	        steps{
			    sh "mvn -f pom.xml test"
	        }
		}
		stage('Build') {
	        steps{
			    sh "mvn -f pom.xml package"
				sh "docker build -t test1:${GIT_VERSION} ."
	        }
		}
		stage('Push To AWS ECR') {
	        steps{
				script {
					loginAwsEcrInfo = sh (script: '/usr/local/bin/aws ecr get-login --no-include-email --region us-east-2',returnStdout: true).trim()
                }
				echo "Retrieved AWS Login: ${loginAwsEcrInfo}"
                sh '${loginAwsEcrInfo}'
				echo "v1: $V1"
				sh 'docker tag test1:${GIT_VERSION} 575331706869.dkr.ecr.us-east-2.amazonaws.com/test1:${GIT_VERSION}'
				sh 'docker push 575331706869.dkr.ecr.us-east-2.amazonaws.com/test1:${GIT_VERSION}'
			}
		}
		stage('Push To DockerHub') {
	        steps{
				 withCredentials(
					 [[
						 $class: 'UsernamePasswordMultiBinding',
						 credentialsId: 'DOCKER_CLOUD_CREDENTIAL_ID',
						 passwordVariable: 'DOCKERHUB_PASSWORD',
						 usernameVariable: 'DOCKERHUB_USERNAME'
					 ]]
				 ) {
					 sh "docker login --username ${env.DOCKERHUB_USERNAME} --password ${env.DOCKERHUB_PASSWORD}"
					 sh "docker tag test1:${GIT_VERSION} olivierderuelle/test1:${GIT_VERSION}"
					 sh "docker push olivierderuelle/test1:${GIT_VERSION}"
				 }
			}
		}
		stage('Staging') {
	        steps{
				sh "docker stop test1"
				sh "docker rm test1"
				sh "docker run -d -p 11111:8080 --name test1 test1:${GIT_VERSION}"
	        }
		}
		stage('Integration Test') {
	        steps{
				echo "to implement"
	        }
		}
		stage('Approval') {
	        steps{
				timeout(time:1, unit:'DAYS') {
					input 'Approve Deployment to Production?'
				}
	        }
		}
		stage('Prod Deployment') {
	        steps{
				echo "to implement"
	        }
		}
	}
}