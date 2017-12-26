#!/usr/bin/groovy
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
				sh "git describe"
				echo "GIT version: ${GIT_VERSION}"
			    sh "mvn -f pom.xml clean install"				
	        }
		}
	}
}