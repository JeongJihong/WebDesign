# 운영

## 운영에 필요한 정보

1. aws ec2 server
2. nginx
3. SSL 인증서(Certbot)
4. maria-db
5. java
6. folder path

<br>

1. aws-ec2 서버를 사용하여 배포를 진행.

username : ubuntu

<br>

2. nginx

```bash
sudo certbot --nginx -d example.com$ sudo apt-get update
sudo apt-get upgrade
sudo apt-get install nginx

#/etc/nginx/site-available/default 파일을 변경하여 port와 SSL 설정을 해줌

===============현재 설정===========

server {
#       listen 80 default_server;
#       listen [::]:80 default_server;
#       return 301 https://i5b302.p.ssafy.io$request_uri;

        # SSL configuration
        #
        listen 443 ssl;
        server_name i5b302.p.ssafy.io www.i5b302.p.ssafy.io;
        ssl_certificate /etc/letsencrypt/live/i5b302.p.ssafy.io/fullchain.pem;

        ssl_certificate_key /etc/letsencrypt/live/i5b302.p.ssafy.io/privkey.pem;
        # listen [::]:443 ssl default_server;
        root /home/ubuntu/b302/dist;

        # Add index.php to the list if you are using PHP
#       index index.html index.htm index.nginx-debian.html;
        index index.html index.htm;

#       server_name _;
#       server_name i5b302.p.ssafy.io www.i5b302.p.ssafy.io;

        location / {
                # First attempt to serve request as file, then
                # as directory, then fall back to displaying a 404.
        #       try_files $uri $uri/ =404;
                try_files $uri $uri/ /index.html;
        }

        location /api {
                proxy_pass http://localhost:8080/api;
                proxy_redirect off;
                charset utf-8;

                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
                proxy_set_header X-NginX-Proxy true;
        }

}

server {
        listen 80 default_server;
        listen [::]:80 default_server;
        return 301 https://i5b302.p.ssafy.io$request_uri;

}
server {
				root /home/ubuntu/b302/dist;

        # Add index.php to the list if you are using PHP
#       index index.html index.htm index.nginx-debian.html;
        index index.html index.htm;

#       server_name _;
		    server_name i5b302.p.ssafy.io; # managed by Certbot

        location / {
                # First attempt to serve request as file, then
                # as directory, then fall back to displaying a 404.
        #       try_files $uri $uri/ =404;
                try_files $uri $uri/ /index.html;
        }

        location /api {
                proxy_pass http://localhost:8080/api;
                proxy_redirect off;
                charset utf-8;

                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
                proxy_set_header X-NginX-Proxy true;
        }
			listen [::]:443 ssl ipv6only=on; # managed by Certbot
	    listen 443 ssl; # managed by Certbot
	    ssl_certificate /etc/letsencrypt/live/i5b302.p.ssafy.io/fullchain.pem; # managed by Certbot
	    ssl_certificate_key /etc/letsencrypt/live/i5b302.p.ssafy.io/privkey.pem; # managed by Certbot
	    include /etc/letsencrypt/options-ssl-nginx.conf; # managed by Certbot
	    ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem; # managed by Certbot

}
server {
	    if ($host = i5b302.p.ssafy.io) {
	        return 301 https://$host$request_uri;
	    } # managed by Certbot


	        listen 80 ;
	        listen [::]:80 ;
	    server_name i5b302.p.ssafy.io;
	    return 404; # managed by Certbot

}
                                                                                                                      205,0-1       Bot
```

<br>

3. CertBot(SSL)

- Firebase Alarm(FCM), KakaoMap, Social Login을 사용하려면 Https 요청이 필요.
- CertBot을 사용하여 무료 SSL(Let's Encrypt) 인증서를 발급받고 서버에 적용

```bash
sudo add-apt-repository ppa:certbot/certbot
sudo apt install python-certbot-nginx
sudo certbot --nginx -d i5b302.p.ssafy.io
# https://i5b302.p.ssafy.io 로 접속하기 위해서

#SSL 자동갱신 등록 (90일)
sudo certbot renew --dry-run

#If 수동 갱신하려면
sudo certbot renew
```

<br>
4. Maria-db(Docker)

- Docker를 사용하여 MariaDB 설치(Docker설치는 따로 설명하지 않음)

```bash
$ docker run --name maria-db -p 3306:3306 -e MYSQL_ROOT_PASSWORD=ssafy -d mariadb
$ docker exec -it maria-db mysql -u root -p
# 실행 후 비밀번호란에 위에 설정해준 비밀번호 입력
# root 계정으로 로그인한 상태이므로 새로운 계정 생성.
# "ssafy" 이름으로 계정 생성
# Database 계정생성은 설명하지 않음

$ docker exec -it maria-db mysql -u ssafy -p
#로그인 후 sql-query.txt 내용 적용
MariaDB [(none)]> show databases;
# "commonpjt" table이 보이면 성공
```

<br>
5. java

- 설치

```bash
# install the necessary dependencies
sudo apt-get -q update
sudo apt-get -yq install gnupg curl

# add Azul's public key
sudo apt-key adv \
  --keyserver hkp://keyserver.ubuntu.com:80 \
  --recv-keys 0xB1998361219BD9C9

# download and install the package that adds
# the Azul APT repository to the list of sources
curl -O https://cdn.azul.com/zulu/bin/zulu-repo_1.0.0-2_all.deb

# install the package
sudo apt-get install ./zulu-repo_1.0.0-2_all.deb

# update the package sources
sudo apt-get update

# install zulu-8
apt-get install zulu-8
```

- 환경변수 등록

```bash
> vi /etc/profile

...

export JAVA_HOME=/usr/lib/jvm/zulu-8-amd64

...
```

<br>
6. Folder Path

- 기존 nginx dist 경로는 "/var/www/dist/html" , nginx설정을 /home/ubuntu/b302/~ 로 해놓았기 때문에 "/home/ubuntu/ 폴더안에 b302 폴더를 생성

<br>

## 배포하는 방법

1. 수동배포(준비물)

- dist폴더
- .jar file

---

[https://lab.ssafy.com/s05-webmobile2-sub3/S05P13B302](https://lab.ssafy.com/s05-webmobile2-sub3/S05P13B302) 경로의 "master" branch clone 후

```bash
#frontend Folder
> yarn install
> yarn run build

#backend Folder
> mvn package

# dist 폴더와 jar 파일을 ec-2 AWS server "/home/ubuntu/b302/" 경로에 Copy

#AWS server (Location : "/home/ubuntu/b302/")
sudo service nginx start
sudo java -jar webcuration-0.0.1-SNAPSHOT.jar
```

2. Docker Jenkins를 통해서 CICD구축

```bash
docker run -d -u root -p 5000:8080 --name=jenkins jenkins/jenkins
#5000포트로 오픈
docker logs jenkins
# 패스워드 확인 후 설치

```

- Nodejs, Gitlab, Publish over SSH 등 필요한 요소 설치
- Global Tool Configuration 에서 maven과 nodejs 경로를 설정해줌.
  참고로 jenkins docker 내부에 maven과 nodejs를 설치해야함

```bash
apt update
apt install maven
# mvn -v 으로 버전확인 가능
apt install nodejs
apt install npm
# nodejs -v 으로 버전확인가능
```

- System Configuration에서 Gitlab Token을 받아와 연동
- Pipeline 생성 (key값 gitlab webhook과 연동. push branch "release"로 설정)

- pipeline shell script 배포용

```bash
pipeline {
    agent any
        tools {
        jdk "java"
        maven "mvn"
        nodejs "nodejs"
    }

    stages{
        stage("clone"){
            steps{
                cleanWs()
                echo "Clone SSAFY Gitlab"
                git branch : 'release', changelog: false, credentialsId: 'henry9489',  poll: false, url: "https://lab.ssafy.com/s05-webmobile2-sub3/S05P13B302"
            }
            post{
                success{
                    echo "Clone Success"
                }
            }
        }
        stage('build'){
            steps{
                dir('frontend'){
                    echo "Build FrontEnd Vue.js"
                    sh "npm install"
                    sh "npm run build"
                    sh "tar -cvzf dist.tar dist deploy.sh killboot.sh"
            }
                dir('backend'){
                        echo "Build BackEnd SpringBoot"
                        sh "mvn package"
                        sh "mv /var/jenkins_home/workspace/aws_pipe/backend/target/webcuration-0.0.1-SNAPSHOT.jar /var/jenkins_home/workspace/aws_pipe/frontend"
                }
            }
        }
        stage('dist transfer') {
            steps([$class: 'BapSshPromotionPublisherPlugin']) {
                dir("frontend"){
                sshPublisher(
                    continueOnError: false, failOnError: true,
                    publishers: [
                        sshPublisherDesc(
                            configName: "aws_pipe",//Jenkins 시스템 정보에 사전 입력한 서버 ID
                            verbose: true,
                            transfers: [
                                sshTransfer(execCommand : "rm -rf /home/ubuntu/b302/webcuration-0.0.1-SNAPSHOT.jar;sleep 1"),
                                sshTransfer(execCommand : "rm -rf /home/ubuntu/b302/dist.tar;sleep 1"),
                                sshTransfer(
                                    sourceFiles: "**/*.tar", //전송할 파일
                                    removePrefix: "", //파일에서 삭제할 경로가 있다면 작성
                                    remoteDirectory: "/", //배포할 위치
                                    execCommand: "tar -xvf /home/ubuntu/b302/dist.tar -C /home/ubuntu/b302/" //원격지에서 실행할 커맨드
                                ),
                                sshTransfer(execCommand : "sudo service nginx restart"),
                                sshTransfer(execCommand : "sleep 3")
                            ]
                        )
                    ]
                )
            }

        }
            post{
                success{
                    echo "finish frontend"
                }
            }
        }
        stage('jar transfer') {
            steps([$class: 'BapSshPromotionPublisherPlugin']) {
                dir("frontend"){
                    sshPublisher(
                        continueOnError: false, failOnError: true,
                        publishers: [
                            sshPublisherDesc(
                                configName: "aws_pipe",
                                verbose: true,
                                transfers: [
                                    sshTransfer(
                                        sourceFiles: "**/*.jar",
                                        removePrefix: "",
                                        remoteDirectory: "/",
                                        execCommand: "sudo chmod 744 /home/ubuntu/b302/killboot.sh"
                                    ),
                                    sshTransfer(execCommand : "sh /home/ubuntu/b302/killboot.sh;")

                                ]
                            )
                        ]
                    )
                }
        }
        post{
            success{
                echo "backend finish"
            }
        }
    }
        stage('restart server') {
            steps([$class: 'BapSshPromotionPublisherPlugin']) {
                sshPublisher(
                    continueOnError: false, failOnError: true,
                    publishers: [
                        sshPublisherDesc(
                            configName: "aws_pipe",//Jenkins 시스템 정보에 사전 입력한 서버 ID
                            verbose: true,
                            transfers: [
																		sshTransfer(execCommand: "sudo chmod 744 /home/ubuntu/b302/deploy.sh"),
                                    sshTransfer(execCommand:"sh /home/ubuntu/b302/deploy.sh")
		                                sshTransfer(execCommand:"ps -ef | grep java")
                                // sshTransfer(execCommand:"sudo rm /home/ubuntu/b302/deploy.sh")

                            ]
                        )
                    ]
                )
        }
        post{
            success{
                echo "CICD Finish"
            }
        }
    }

    }
}
```

## 발생할 수 있는 오류

- 접속 후 아무행동없이 5시간이 지났을 때 Token만료 오류 발생 가능성 → 로그아웃 후 재로그인
- 알람이 foreground 상태에서 안옵니다! → 정상입니다. background 상태일 때만 알람이 나타납니다.
- 약속 목록을 보는데 지도가 오류가 나서 안나옵니다! → 지도 우측상단에 새로고침 버튼 클릭
