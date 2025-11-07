pipeline {

    // https://www.jenkins.io/doc/book/pipeline/syntax/#agent 
	agent any
	
	// 자동 트리거(trigger) 설정
	// https://www.jenkins.io/doc/book/pipeline/syntax/#triggers
	// 단, 본 Jenkinsfile 파일 설정에서는 jenkins 메뉴에서의 Trigger로 대체(replace) 
	// : Triggers =>  GitHub hook trigger for GITScm polling
		
//	triggers {
	
//	    githubPush()	    
	    // upstream(upstreamProjects: "SpringBootJWTAuthGradle", threshold: hudson.model.Result.SUCCESS)
//	}

	stages {
	
	    // 
        stage('Stage-1 : Checkout Jenkins for Github(repo)') {
        
            // build-step : https://www.jenkins.io/doc/pipeline/steps/pipeline-build-step/
            // 참고) SCM : Software Configuration Management(소프트웨어 형상 관리)의 약자
            // Ant, Maven, Gradle 같은 빌드툴이나 Jenkins, Github Action 등의 CI/CD툴도 프로젝트 형상관리툴로 간주함. 
            steps {
                // 참고) jenkins가 기본적으로 체크아웃을 하지만 파이프라인(pipeline)에서 아래와 같이 기입하는 이유는                
                // checkout 전에 local reference repo를 업데이트 하고자 할때는 기본적인 jenkins의 checkout을 건너뛰고
                // 나중에 아래와 같이 checkout scm 해야 한다.
                // 만약 파이프라인에 여러개의 플랫폼 노드가 존재할 경우는 reference repo에 같은 경로를 사용할 수 없으며, 
                // Windows Stage는 Linux 파이프파인에서 위치를 재정의해야 된다.
                // github 저장소 사용시 내부적으로 "checkout scm"이 수행되어야"만" git 커밋이 처리된 것으로 간주한다.
                
                // 참고 링크) https://www.reddit.com/r/jenkinsci/comments/piz3ar/is_checkout_scm_usefull_in_pipeline_if_jenkins/?tl=ko
                checkout scm
            }
            
        }
	
	    // Github repo(저장소)를 jenkins workspace로 clone(복사) : jenkins에서 빌딩(building)하기 조치
	    // 만약 이미 빌딩을 마친 소스를 github에 올려서 전개하였다면 ROOT.war를 구성하여 
	    // Tomcat에 복사/배포하는 단계(Stage-4)로 바로 전개하도록 코딩할 수 있다.    
	    // 기본적으로 소스가 Github에 저장되어 있다는 것을 전제로 프로세스 시작
        stage('Stage-2 : Clone Github') {
        
            steps {
                git branch: 'master', 
                credentialsId: 'gym_reservation_token',
                url: 'https://github.com/KimSooHeon-cpu/backend.git'
            }
            
            post {
            	success {
                    sh 'echo "Clone Github Repository Successfully"'
                }
                failure{
                    error 'Clone Github Repository Failed'
                }
       		}
        }		      
        
         // React에서 빌딩된 html/css/js 파일들을 Spring Project의  
        stage('Stage-3 : Copy From React Project To Spring Project') {
		
			steps {
			    
				script {
				
					// 기존의 static 폴더의 정적 빌드 파일(index*.html, *.css, *.js) 지우기
					sh 'rm -rf /var/jenkins_home/workspace/frontend/src/main/resources/static/index*.html'
					sh 'rm -rf /var/jenkins_home/workspace/frontend/src/main/resources/static/assets/*'
					
					// 추가 : 먼저 static, template 경로 있는지 점검하고 없으면 폴더 생성
					sh "cd /var/jenkins_home/workspace/frontend/src/main/resources/"
					sh "pwd"
					
					sh """
						 DIR_NAME_STATIC = "static"
						 DIR_NAME_TEMPLATES = "templates"
						 
						 if [ ! -d "\${DIR_NAME_STATIC}" ] ; then
						     echo "static 폴더가 없습니다. 폴더 생성하도록 하겠습니다."
						     mkdir -p "\${DIR_NAME_STATIC}"
						 fi
						 
						 if [ ! -d "\${DIR_NAME_TEMPLATES}" ] ; then
						     echo "templates 폴더가 없습니다. 폴더 생성하도록 하겠습니다."
						     mkdir -p "\${DIR_NAME_TEMPLATES}"
						 fi     
					"""
				
			    	// css, js -> static 경로(정적 파일 전용 경로)에 복사
					sh 'cp /var/jenkins_home/workspace/frontend/dist/assets/* /var/jenkins_home/workspace/backend/src/main/resources/static/assets'
					 
					// html 파일은 templates(thymeleaf 등의 동적 파일 경로, thymeleaf를 사용하지 않지만 Controller에서 정적 html로 포워딩(forwarding)할 때 인식함) 폴더에 복사
					sh 'cp /var/jenkins_home/workspace/frontend/dist/*.html /var/jenkins_home/workspace/backend/src/main/resources/templates'				    
				}

			}
		          
  		} //
          
		
		stage('Stage-4 : Gradle Build in Jenkins & make ROOT.war') {
		
			steps {							
				sh 'chmod +x gradlew' // gradlew 빌드 프로그램에 대한 실행 권한(permission : 숫자로는 777) 설정
				sh './gradlew clean build' // jar, war 파일 작성 및 테스트 모두 수행				
				// sh './gradlew clean build --exclude-task test' // 빌딩시 테스트 제외
				// sh './gradlew bootWar' // 빌딩시 war 파일만 작성
				sh 'ls -la ./build/libs' 
				sh 'cd /var/jenkins_home/workspace/backend' // jenkins workspace 에 위치한 프로젝트 폴더로 이동
				sh 'pwd' // 폴더 위치 확인
				sh 'ls -la'
				sh 'cp build/libs/gym-k.war build/libs/ROOT.war' 
				// 빌딩된 war 파일을 Tomcat root contextPath("/") 실행할 프로젝트용(ROOT.war)으로 복사
				// ROOT.war 형태로 프로젝트 파일이 인식되어야 Tomcat에서는 Root Project로 간주하여 실행함				
			}
			
			post {
				success {
                   echo 'Gradle Build & Copy Successfully'
                }
                failure{
                   error 'Gradle Build & Copy Failed'
               }
            }
		}
		
		// Using Docker with Pipeline ex) https://www.jenkins.io/doc/book/pipeline/docker/
		stage('Stage-5 : Docker CP(Copy) From Jenkins To Tomcat & Tomcat Webapps Launching(Deployment)') {
		
			steps {
			    
				script {
				
				    sh 'docker cp /var/jenkins_home/workspace/backend/build/libs/ROOT.war tomcat_10_1:/usr/local/tomcat/webapps'
				    // jenkins 워크스페이스에서 빌딩된 war 파일을 Tomcat의 webapps 폴더에 복사하면 Tomcat이 알아서 압축을 이완하여(풀어서)
				    // 실행 가능하도록 프로젝트를 배포(deploy)한다.				    
				}

			}
		          
  		} //

	}
	
}