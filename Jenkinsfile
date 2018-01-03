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
                }
				echo "GIT version: ${GIT_VERSION}"
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
				echo "GIT version: ${GIT_VERSION}"
			    sh "mvn -f pom.xml package"
				sh "docker build -t test1:${GIT_VERSION} ."
				sh "docker tag test1:${GIT_VERSION} test1:latest"
	        }
		}
		stage('Push To AWS ECR') {
	        steps{
				echo "GIT version: ${GIT_VERSION}"
				script {
					loginAwsEcrInfo = sh (script: '/usr/local/bin/aws ecr get-login --no-include-email --region us-east-2',returnStdout: true).trim()
                }
				echo "Retrieved AWS Login: ${loginAwsEcrInfo}"
                sh "${loginAwsEcrInfo}"
				echo "GIT version: ${GIT_VERSION}"
				sh "docker tag test1:${GIT_VERSION} 575331706869.dkr.ecr.us-east-2.amazonaws.com/test1:${GIT_VERSION}"
				sh "docker tag test1:latest 575331706869.dkr.ecr.us-east-2.amazonaws.com/test1:latest"
				sh "docker push 575331706869.dkr.ecr.us-east-2.amazonaws.com/test1:${GIT_VERSION}"
				sh "docker push 575331706869.dkr.ecr.us-east-2.amazonaws.com/test1:latest"
			}
		}
		/*
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
		*/
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
		/*stage('Approval') {
	        steps{
				timeout(time:1, unit:'DAYS') {
					input 'Approve Deployment to Production?'
				}
	        }
		}
		*/
		stage('Prod Deployment') {
	        steps{
				//sh "aws ecs register-task-definition --cli-input-json file://${workspace}/awsTaskDefinitionTest1.json"
				//sh "aws ecs register-task-definition --cli-input-json file://${workspace}/awsTaskDefinitionTest1.json | egrep \"revision\" | awk '{print \$2}'"
				sh "rm awsTaskDefinitionTest1TEMP.json"
				sh "sed 's/VAR-TAG-NAME/${GIT_VERSION}/g' awsTaskDefinitionTest1.json >> awsTaskDefinitionTest1TEMP.json" 
				//sh "sed -i 's/VAR-CONTAINER-NAME/${GIT_VERSION}/g' awsTaskDefinitionTest1TEMP.json"
				script {
					ecsTaskLatestRevision = sh (script: "/usr/local/bin/aws ecs register-task-definition --cli-input-json file://${workspace}/awsTaskDefinitionTest1TEMP.json | egrep \"revision\" | awk '{print \$2}'",returnStdout: true).trim()
				}
				echo "Retrieved AWS Latest Task Revision: ${ecsTaskLatestRevision}"
				sh "aws ecs update-service --cluster od-cluster1 --service od-service-5 --task-definition odTaskDefinition1:${ecsTaskLatestRevision}"
	        }
		}
	}
}