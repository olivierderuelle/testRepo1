#!/usr/bin/groovy
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
				sh "java -version"
			    sh "mvn -f pom.xml clean install"
				GIT_VERSION = sh (script: 'git describe',returnStdout: true).trim()
				echo "GIT version: ${GIT_VERSION}"
	        }
		}
	}
}