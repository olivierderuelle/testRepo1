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
				sh "git describe"
			    sh "mvn -f pom.xml clean install"				
	        }
		}
	}
}