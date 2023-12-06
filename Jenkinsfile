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
               stage ('Scan and Build Jar File') {
                       steps {
                           withSonarQubeEnv(installationName: 'Production SonarQubeScanner', credentialsId: 'squ_66d32884289bda4620279f00be2531f49e7a906b') {
                           bat '${mavenHome}/bin/mvn sonar:sonar'
                      }
               }
           }
		
	}
}
