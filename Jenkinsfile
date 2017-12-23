#!/usr/bin/groovy

node {
   lock('full') {
      def container
	  stage('Compile') {
	  checkout scm
      def mvn_version = 'M3'
	  jdk = tool name: 'java8'
	  env.JAVA_HOME = "${jdk}"
      withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {
		sh "echo 'jdk installation path is: ${jdk}'"
		sh "${jdk}/bin/java -version"
	    sh "mvn -f pom.xml clean compile"
	  }
   }
   stage('Test') {
      def mvn_version = 'M3'
      withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {
	    sh "mvn -f pom.xml test"
	  }
   }
   stage('Build') {
		def mvn_version = 'M3'
		withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {
			GIT_VERSION = sh (
				script: 'git describe',
				returnStdout: true
			).trim()
			echo "GIT VERSION: ${GIT_VERSION}"
			def image = docker.build("test1:${GIT_VERSION} --build-arg WAR_FILE=${workspace}/target/test1-0.0.1-SNAPSHOT.war")
			container = image.run("-p 11111:8080")
		}
   }
    // stage ('Staging') {
		// replace below with the mvn docker plugin run command as here we got no idea the version tag to run!!!
		// also call mvn docker plugin to stop the container as the port is already in use!!!
		// bat "docker run --name=test1 -p 11111:8080 -d test1:odtag1"
	//	def mvn_version = 'M3'
	//	withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {			
	//		sh "mvn -f pom.xml docker:run"
	//	}
    //  }
   stage ('Approval') {
  		timeout(time:1, unit:'DAYS') {
    		input message:'Approve deployment to Production?', submitter: 'it-ops'
		}
   }
  // stage ('Production') {
//	  def mvn_version = 'M3'
//		withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {			
//			sh "mvn -f pom.xml docker:push"
//	  }
  // }
   }
}