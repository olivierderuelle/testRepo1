#!/usr/bin/groovy
pipeline {
	agent any
    tools { 
        maven 'M3' 
    }
	stages {
		stage('Compile') {
	        steps{
	            checkout scm
			    sh "mvn -f pom.xml clean install"
	        }
		}
	}
}