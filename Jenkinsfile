#!/usr/bin/groovy
pipeline {
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
						sh "mvn -f pom.xml clean install"
					}
				}
			}
		}
	}
}