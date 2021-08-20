# 운영

## 운영에 필요한 정보

1. aws ec2 server
2. nginx
3. SSL 인증서(Certbot)
4. maria-db
5. java
6. 외부 서비스
7. folder path

## 환경

- Intellij 2021.1.1 (Ultimate Edition)
- Java Zulu 8
- Spring Boot Dependencies

```bash
<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.2.2.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.web.curation</groupId>
	<artifactId>webcuration</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>webcuration</name>
	<description>Web curation project for Spring Boot</description>

	<properties>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-aop</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.6.1</version>
		</dependency>
		<dependency>
            <groupId>org.json</groupId>
            <artifactId>json</artifactId>
            <version>20180813</version>
        </dependency>

		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		<dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
			<exclusions>
				<exclusion>
					<groupId>org.junit.vintage</groupId>
					<artifactId>junit-vintage-engine</artifactId>
				</exclusion>
			</exclusions>
		</dependency>

		<dependency>
			<groupId>commons-io</groupId>
			<artifactId>commons-io</artifactId>
			<version>2.6</version>
		</dependency>

		<!-- 디비 셋팅 주석 -->
		<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>

		<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
		</dependency>

		<!-- Spring Security 주석 -->
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-test</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-config</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-core</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-web</artifactId>
		</dependency>
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt</artifactId>
			<version>0.9.1</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-oauth2-client</artifactId>
		</dependency>

		<!--	Firebase Alarm	-->

		<dependency>
			<groupId>com.squareup.okhttp3</groupId>
			<artifactId>okhttp</artifactId>
			<version>4.9.1</version>
		</dependency>

		<dependency>
			<groupId>com.google.firebase</groupId>
			<artifactId>firebase-admin</artifactId>
			<version>8.0.0</version>
		</dependency>

		<!-- https://mvnrepository.com/artifact/com.google.code.gson/gson -->
		<dependency>
			<groupId>com.google.code.gson</groupId>
			<artifactId>gson</artifactId>
			<version>2.8.5</version>
		</dependency>

	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<version>2.4.4</version>
			</plugin>
		</plugins>
	</build>
```

- Vuejs Dependencies

```bash
"dependencies": {
    "axios": "^0.21.1",
    "babel-eslint": "^10.0.3",
    "babel-preset-env": "^1.7.0",
    "bootstrap": "^5.0.2",
    "bootstrap-vue": "^2.21.2",
    "core-js": "^3.4.3",
    "email-validator": "^2.0.4",
    "firebase": "^7.6.1",
    "jwt-decode": "^3.1.2",
    "local-lib": "^0.1.0",
    "node-sass": "^6.0.1",
    "password-validator": "^5.0.3",
    "sass-loader": "^10.2.0",
    "vue": "^2.6.14",
    "vue-infinite-loading": "^2.4.5",
    "vue-jwt-decode": "^0.1.0",
    "vue-kakao-login": "^1.0.5",
    "vue-router": "^3.5.2",
    "vue-slick-carousel": "^1.0.6",
    "vuex": "^3.6.2"
  },
```

1. aws-ec2 서버를 사용하여 배포를 진행.

username : ubuntu

1. nginx

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

1. CertBot(SSL)

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

1. Maria-db(Docker)

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

1. java

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

1. 외부 서비스

- 카카오 로그인 : 카카오API서비스 페이지 내에서 설정. (i5b302.p.ssafy.io로 설정)

- FirebaseCloudMessage

```bash
firebase.initializeApp({
  apiKey: "AIzaSyBF2i3yoTsOyPq8ftdBLVtxrSCUTMX1cvM",
  projectId: "fcm-springboot-dbe2f",
  messagingSenderId: "263809922923",
  appId: "1:263809922923:web:cd880ed771dc7122813e2e",
});
```

1. Folder Path

- 기존 nginx dist 경로는 "/var/www/dist/html" , nginx설정을 /home/ubuntu/b302/~ 로 해놓았기 때문에 "/home/ubuntu/ 폴더안에 b302 폴더를 생성

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

1. Docker Jenkins를 통해서 CICD구축

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

# 시연시나리오

**[ 온보딩 화면 ]**

- 서비스를 이용하기 전 항상 온보딩 화면을 보여주며 총 4페이지로 구성되어 있습니다.
- 각 페이지에서는 모두 로그인, 회원하기 버튼이 있으며, 해당 버튼을 클릭하면 기능에 해당하는 페이지로 이동합니다.

![Untitled](%Image/Untitled.png)

**[ 회원가입 ]**

- 닉네임과 이메일을 입력한 뒤 중복확인 버튼을 선택하여 중복인지 확인해줍니다.
- 닉네임
  - 중복확인 버튼을 눌렀을 때 중복이 아니라면 ‘사용 가능한 닉네임입니다.’ 중복이라면 ‘중복된 닉네임 입니다!’ 라는 알림창을 띄워줍니다.
- 이메일
  - 형식이 다르다면 ‘이메일 형식이 아닙니다.’라는 문구가 출력됩니다.
  - 형식이 맞지만 이미 존재하는 이메일이라면 ‘중복된 이메일입니다.’는 알림창을 띄워주고, 새로운 이메일이라면 ‘사용 가능한 이메일 입니다.’ 라는 알림창을 띄워줍니다.
- 비밀번호는 영문, 숫자, 특수문자 포함 8자리 이상이어야 하고, 아니라면 ‘영문, 숫자, 특수문자 포함 8자리 이상이어야 합니다.’라는 문구를 출력합니다.
- 비밀번호와 비밀번호 확인까지 일치하면 가입하기 버튼이 활성화됩니다.

![Untitled](%Image/Untitled%201.png)

**[ 로그인 ]**

- 로그인 창에 회원가입된 이메일과 비밀번호를 입력하고 로그인 버튼을 클릭하면 로그인이 되며 메인 피드로 이동합니다.
- 카카오계정으로 로그인을 클릭하면 카카오 로그인 기능 사용가능 합니다. 처음 가입하는 사용자라면 닉네임, 프로필 사진, 이메일 사용 권한 동의 창이 뜨고 동의를 진행합니다. 동의하면 자동으로 가입이 완료되고 사용가능 합니다. 이미 카카오 계정으로 가입한 계정이라면 바로 로그인 되고 서비스를 이용할 수 있습니다.

![Untitled](%Image/Untitled%202.png)

**[ 유저검색 ]**

- 상단 네비게이션 바 중앙에 있는 유저검색 버튼을 클릭하면, 닉네임을 통해 검색할 수 있습니다.
- 최근검색에서는 사용자가 최근에 검색한 닉네임을 나타내줍니다.

![Untitled](%Image/Untitled%203.png)

**[ 프로필 페이지 ]**

- 프로필 페이지에서는 우측상단의 프로필 수정 버튼을 누르면 자신의 프로필사진 수정과 소개글을 비밀번호까지 변경할 수 있습니다.
  - 수정 이 후 저장하기 버튼을 누르면 자신의 프로필이 수정됩니다.
- 유저가 작성한 게시글, 약속을 볼수있습니다.
- 팔로워, 팔로우에 대한 생성한 게시글,약속 역시 파악할 수 있습니다.

![Untitled](%Image/Untitled%204.png)

![Untitled](%Image/Untitled%205.png)

**[ 게시글 작성 ]**

- 하단 우측의 Create 버튼을 클릭하면 게시글 생성 페이지로 이동하여 게시글을 작성할 수 있습니다.
- 게시글 작성에서는 말 그대로 게시글을 작성할 수 있으며, 이미지 업로드 버튼을 클릭하여 사진을 업로드 하고, 아래의 글 입력 박스에서 게시글 내용을 입력할 수 있습니다.
- 사진과 글은 모두 필수로 입력해야 하며, 둘 중 하나라도 입력되지 않았다면 게시글 생성이 되지 않습니다.
- 사진과 글을 모두 작성한 뒤 전송 버튼을 클릭하면 게시글이 작성되고, 메인 피드에서 바로 확인할 수 있습니다.

![Untitled](%Image/Untitled%206.png)

**[ Home ]**

- 본인이 작성한글은 피드에 올라옵니다.
  - 해당 피드는 저를 팔로우 하고 있는 사람들에게만 보여집니다.

**[ 알람 ]**

- 알람 페이지에서는 좋아요, 팔로우, 팔로우들의 약속생성의 알림이 기록됩니다.
- 제 글에 좋아요가 눌리면 Like 탭에, 팔로우 신청이 오면 Follow 탭에, 그리고 약속 초대가 오면 Promise 탭에 기록이 생깁니다.

![Untitled](%Image/Untitled%207.png)

**[ 약속리스트 ]**

- 약속이 생성되면은 약속들을 정리해주는 페이지입니다.
- upcoming 약속과 waiting 약속으로 구분이 됩니다.

  - upcoming 약속 : 자신 포함 다른 유저가 생성한 약속
  - waiting 약속 : 참여한 약속

  ![Untitled](%Image/Untitled%208.png)

**[ 약속 생성 페이지]**

- 약속 생성페이지는 말 그대로 약속을 생성할 수 있습니다.
  - 제목, 사진, 내용, 시간, 장소, 인원, 타입을 입력함으로써 약속의 정보를 구체화합니다.
- 장소 검색시 주변검색을 먼저 검색한 후 나온 지도에서 약속장소를 클릭하여 정확한 장소를 정합니다.

![Untitled](%Image/Untitled%209.png)

![Untitled](%Image/Untitled%2010.png)

**[약속 생성 후 메인피드]**

- 생성한 약속은 메인 피드에서도 확인이 가능하며, 약속 리스트에서도 확인이 가능합니다.
- 메인피드에서 약속정보는 포스트잇의 형태로 기존 게시글과 구별되게 해놓았습니다.

**[ 다들 어디 ]**

- 지금어디는 약속리스트에서 약속을 클릭한 후 우측상단의 ‘다들 어디’를 클릭하면 참가한 분들의 위치를 지도로 볼 수 있습니다.
- 참가자의 위치와 약속 장소까지의 거리를 계산하여 얼마나 걸리는지를 시간으로 나타내줍니다.
- 약속 시간이 한 시간 안으로 다가왔을 때만 '다들 어디' 항목이 활성화 됩니다.

![Untitled](%Image/Untitled%2011.png)

**[ 약속참가 후 -  프로필- 피플포인트 ]**

- 약속을 참가한 후에 프로필에 들어가면 프로필사진 하단에 피플 포인트가 있습니다.
- 약속에 참여하게 되면 피플 포인트가 쌓이게 됩니다. 반대로 약속을 취소하거나 파기를하면 그반대로 피플포인트가 감소됩니다.

![Untitled](%Image/Untitled%204.png)
