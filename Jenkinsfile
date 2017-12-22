#!/usr/bin/groovy

node {
   lock('full') {
	  stage('Compile') {
	  milestone()
	  checkout scm
      def mvn_version = 'M3'
	  jdk = tool name: 'java8'
	  env.JAVA_HOME = "${jdk}"
      withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {
		sh "echo 'jdk installation path is: ${jdk}'"
		sh "${jdk}/bin/java -version"
	    sh "mvn -f test1/pom.xml clean compile"
	  }
   }
   stage('Test') {
      def mvn_version = 'M3'
      withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {
	    sh "mvn -f test1/pom.xml test"
	  }
   }   
   stage('Build') {
   	  milestone()	  
		def mvn_version = 'M3'
		withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {
			GIT_VERSION = sh (
				script: 'git describe',
				returnStdout: true
			).trim()
			echo "GIT VERSION: ${GIT_VERSION}"
			sh "mvn -f test1/pom.xml docker:stop"
			sh "mvn -f test1/pom.xml package docker:build"
		}	 
   }
   stage ('Staging') {
   	  milestone()     
		// replace below with the mvn docker plugin run command as here we got no idea the version tag to run!!!
		// also call mvn docker plugin to stop the container as the port is already in use!!!
		// bat "docker run --name=test1 -p 11111:8080 -d test1:odtag1"
		def mvn_version = 'M3'
		withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {			
			sh "mvn -f test1/pom.xml docker:run"
		}
   }
  // stage ('Approval') {
   //		timeout(time:1, unit:'DAYS') {
   // 		input message:'Approve deployment to Production?', submitter: 'it-ops'
	//	}
  // }
   stage ('Production') {
	  def mvn_version = 'M3'
		withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {			
			sh "mvn -f test1/pom.xml docker:push"
	  }
   }
   }
}