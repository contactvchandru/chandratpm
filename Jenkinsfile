pipeline
    {
      tools {
        maven 'maven.3.9.3'
      }
       agent any
       stages
        {
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
         stage('Build Image') {
            steps {
              sh "rm -rf ocp && mkdir -p ocp/deployments"
              sh "pwd && ls -la target "
              sh "cp target/chandratpm-0.0.1-SNAPSHOT.jar ocp/deployments" 

              script {
                openshift.withCluster() {
                  openshift.withProject() {
                    openshift.selector("bc", "chandratpm").startBuild("--from-file=target/chandratpm-0.0.1-SNAPSHOT.jar","--follow", "--wait=true")
                  }
                }
              }
            }
          }
         stage('deploy') {
            when {
              expression {
                openshift.withCluster() {
                  openshift.withProject() {
                    return !openshift.selector('dc', 'chandratpm').exists()
                  }
                }
              }
            }
            steps {
              script {
                openshift.withCluster() {
                  openshift.withProject() {
                    def app = openshift.newApp("chandratpm", "--as-deployment-config")
                    app.narrow("svc").expose();
                  }
                }
              }
            }
          }
        }
    }
