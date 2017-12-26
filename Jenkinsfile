pipeline {
	agent any
    tools { 
        maven 'M3' 
		jdk 'java8'
    }
    environment { 
        GIT_VERSION = sh (script: 'git describe',returnStdout: true).trim()
    }
	stages {
		stage('Compile') {
	        steps{
	            checkout scm
				script {
					OD = sh (script: 'git describe',returnStdout: true).trim()
                }
				echo "OD GIT version: ${OD}"
				sh "java -version"
				echo "GIT version: ${GIT_VERSION}"
			    sh "mvn -f pom.xml clean install"
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
	}
}