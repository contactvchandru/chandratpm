pipeline {
	agent any

	environment {
		mavenHome = tool 'jenkins-maven'
	}

	tools {
		jdk 'java-17'
	}

	stages {

		stage('Build'){
			steps {
				
				bat "${mavenHome}/bin/mvn clean install -DskipTests"
			}
		}

		stage('Test'){
			steps{
				bat "${mavenHome}/bin/mvn test"
			}
		}
		
	}
}
