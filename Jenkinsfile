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
				sh "java -version"
				echo "GIT version: ${GIT_VERSION}"
			    sh "mvn -f pom.xml clean install"
				sh "docker build -f dockerFile test1:${GIT_VERSION} --build-arg WAR_FILE=target/test1.war .")
	        }
		}
		//stage('Staging') {
	    //    steps{
	    //        container = image.run("-p 11111:8080")
	    //    }
		//}
	}
}