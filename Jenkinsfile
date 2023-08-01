pipeline {

      tools {
        maven 'maven.3.9.3'
      }
      agent any
  stages {
      stage('Build App')
          {
            steps
             {
              git branch: 'master', url: 'https://github.com/contactvchandru/chandratpm.git'
              script {
                  def pom = readMavenPom file: 'pom.xml'
                  version = pom.version
              }
               sh "mvn -Dintegration-tests.skip=true -Dunit-tests.skip=true clean install"
            }
          }
    stage('Create Container Image') {
      steps {
        echo 'Create Container Image..'
        
        script {
          openshift.withCluster() {
            openshift.withProject("chandra-tpm") {
                def buildConfigExists = openshift.selector("bc", "codelikethewind").exists()

                if(!buildConfigExists){
                    openshift.newBuild("--name=codelikethewind --allow-missing-imagestream-tags","--docker-image=registry.redhat.io/jboss-eap-7/eap74-openjdk8-openshift-rhel7","--binary=true")
                }

                openshift.selector("bc", "codelikethewind").startBuild("--from-file=target/chandratpm-0.0.1-SNAPSHOT.jar", "--follow")

            }

          }
        }
      }
    }
    stage('Deploy') {
      steps {
        echo 'Deploying....'
        script {
          openshift.withCluster() {
            openshift.withProject("chandra-tpm") {

              def deployment = openshift.selector("dc", "codelikethewind")

              if(!deployment.exists()){
                openshift.newApp('codelikethewind --allow-missing-imagestream-tags', "--as-deployment-config").narrow('svc').expose()
              }

              timeout(5) { 
                openshift.selector("dc", "codelikethewind").related('pods').untilEach(1) {
                  return (it.object().status.phase == "Running")
                  }
                }

            }

          }
        }
      }
    }
  }
 }
