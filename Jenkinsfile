pipeline {
	agent any
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
				sh "docker build -t test1:${GIT_VERSION} ."
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
					input message:'Approve Deployment to Production?', submitter: 'it-ops'
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