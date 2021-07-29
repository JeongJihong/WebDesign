# Pipl

<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/0d43e80f-2446-4dc5-be4d-2b245936ed5e/main-icon.svg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210729%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210729T012331Z&X-Amz-Expires=86400&X-Amz-Signature=e5440ec3ad5b1c8cef118f6577af1fd4a5af80e433b54606b43008ecd864bec7&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22main-icon.svg%22" width= "10%">    **Pipl** 은 **Pi**ck a **pl**an 의 줄임말로, SNS 상에서 모임/약속을 쉽게 잡아주는 소셜 플랫폼입니다.

<br>

<br>

<img src="https://shields.io/badge/vue--cli-v4.1.1-plastic" style="width: 10%;"> <img src="https://shields.io/badge/spring--boot-red" style="width: 8.3%;"> <img src="https://shields.io/badge/maven-v3.6.3-hotpink" style="width: 10%;"> <img src="https://shields.io/badge/zulu%208-blue" style="width: 5.4%;"> <img src="https://shields.io/badge/MySQL-blue" style="width: 6%;">

<br>

**Pipl 개발 페이지**  &#10140;  [Notion](https://www.notion.so/472849a205114733b53d218219d4b8a7)    [Jira](https://jira.ssafy.com/secure/RapidBoard.jspa?rapidView=9511&projectKey=S05P12B302)   [WireFrame](https://www.figma.com/file/GzAF1xAtlr1X2IS0iNzggx?embed_host=notion&kind=&node-id=0%3A1&viewer=1)

<br>

<br>

### 목차

- [프로젝트 소개](#프로젝트-소개)   
- [프로젝트 명세](#프로젝트-명세)
  - [배포 환경](#배포-환경)
  - [개발 환경](#개발-환경)
  - [Design Resources](#design-resources)
  - [핵심 라이브러리](#핵심-라이브러리)
- [ERD](#erd)
- [SWAGGER](#swagger)
- [개발 현황](#개발%20현황)



<br>

### 프로젝트 소개
Pipl 은 SNS 상에서 모임/약속을 쉽게 잡아주는 소셜 플랫폼입니다.

모임/약속을 잡는 가장 첫 단추인, 사람들에게 말하는 과정을 Pipl 을 이용하여 **손쉽게 전달**할 수 있습니다.

팔로우/팔로워와 태그 기반으로, 잡고자 하는 약속을 특정 사람들에게 알릴 수 있습니다.

약속을 잡는데 성공하면, 사용자 GPS 정보 사용 동의 하에 약속 1시간 전부터 약속 대상자들의 위치를 확인할 수 있습니다. 

<br>

### 프로젝트 명세
#### 배포 환경
- __URL__ : 
- __배포 여부__ : X
- __접속 가능__ : X
- __HTTPS 적용__ : X
- __PORT__ : Vue (3000), Spring-Boot (8080)

<br>

#### 개발 환경
##### Front-end
- __Framework__ : Vue.js
- __지원 환경__ : Web
- __담당자__ : 신형식, 이두호, 정종우
  - 신형식 : 회원정보(로그인, 회원가입, 비밀번호 변경), 게시글(조회, 생성, 수정), 댓글
  - 이두호 : 헤더(네비게이션, 라우팅 관리), 검색, 알림, 스크랩
  - 정종우 : 프로필(조회, 수정), 팔로우/팔로워, 스크랩

<br>

##### Back-end
- __Framework__ : Spring boot
- __Database__ : MySQL
- __담당자__ : 김백준, 정지홍
  - 공통 : 게시글
  - 김백준 : 회원정보(JWT 인증), 검색, 알림
  - 정지홍 : 팔로우/팔로워, 댓글, 스크랩

<br>

##### Design
- __Framework 사용__ : O
  - [Bootstrap-Vue](https://bootstrap-vue.org/)
- __Design Tool 사용__ :
- __담당자__ : 신형식, 이두호, 정종우

<br>

#### Design Resources
__외부 템플릿 또는 에셋__ (이미지 또는 링크 첨부)

<br>

__자체 제작 산출물__ (필요시 이미지 또는 설명 첨부)
- LOGO

  <img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/0d43e80f-2446-4dc5-be4d-2b245936ed5e/main-icon.svg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210729%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210729T012331Z&X-Amz-Expires=86400&X-Amz-Signature=e5440ec3ad5b1c8cef118f6577af1fd4a5af80e433b54606b43008ecd864bec7&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22main-icon.svg%22" style="width: 25%">     <img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/c1fc4306-3000-4635-a170-769300682963/main-icon-2.svg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210729%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210729T012255Z&X-Amz-Expires=86400&X-Amz-Signature=7c0ee2aae5bb9ff31f306a285202f69eed889e94f11a75f95e40481d78b622cc&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22main-icon-2.svg%22" style="width: 25%">

<br>

### 핵심 라이브러리

<br>

### ERD

<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/ca8e6475-b443-4ccc-bbd8-5c46c1a71a5d/_.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210729%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210729T023738Z&X-Amz-Expires=86400&X-Amz-Signature=b35f0c4ef9f7a549f87e67491d52b22c40ef7f82524e492c43974efc24f36774&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22_.png%22">

<br>

<br>

### SWAGGER

> 노션 SWAGGER  &#10140;  [Here](https://www.notion.so/SWAGGER-6c59eedd1def4d37b52969b0b17bd27c)

<br>

| REST API                             | Method | 설명                                                         | 현황 |
| ------------------------------------ | :----: | ------------------------------------------------------------ | :--: |
| account/checkJWT                     |  GET   | 로그인 후 반환 받은 Token을 사용하여 회원정보를 체크합니다.<br />**(Need Token)** | Done |
| account/login                        |  GET   | 로그인을 합니다.                                             | Done |
| account/signup                       |  POST  | 회원가입을 합니다.                                           | Done |
| account/profile                      | DELETE | 회원 탈퇴를 합니다.<br />**(Need Token)**                    | Done |
| account/profile                      |  PUT   | 유저 닉네임을 수정합니다.<br />**(Need Token)**              | Done |
|                                      |        |                                                              |      |
| account/profile/{nickname}/follower  |  GET   | 해당 유저의 팔로우 리스트를 반환합니다.                      | Done |
| account/profile/{nickname}/following |  GET   | 해당 유저의  팔로잉  리스트를 반환합니다.                    | Done |
| account/profile/follow               |  POST  | 다른 유저에게 팔로우 요청합니다.                             | Done |
| account/profile/follow               | DELETE | 팔로우 요청을 거부한다.                                      | Done |
| account/profile/follow               |  PUT   | 팔로우 요청을 승인한다.                                      | Done |
| account/changePassword               |  PUT   | 본인의 비밀번호를 변경합니다.<br />**(Need Token)**          | Done |
|                                      |        |                                                              |      |
| article/                             |  GET   | 메인페이지(피드: 최신 글 순)를 반환합니다.                   |      |
| article/                             |  GET   | 유저의 전체 게시글 정보(간략)를 반환합니다.<br />**(Need Token)** |      |
| article/                             |  POST  | 해당 유저의 새로운 게시글을 생성합니다.<br />**(Need Token)** |      |
| article/{nickname}                   |  GET   | 해당 유저의 프로필 정보, 팔로잉 유무, 피드 정보를 얻어옵니다. |      |
| article/{articleid}                  |  GET   | 해당 유저의 특정 게시글의 상세정보(좋아요 수, 댓글 수 포함)를 반환합니다. | Done |
| article/{articleid}                  |  PUT   | 해당 유저의 특정 게시글의 정보를 수정합니다.<br />**(Need Token)** |      |
| article/{articleid}                  | DELETE | 해당 유저의 특정 게시글을 삭제합니다.<br />**(Need Token)**  | Done |
| article/{articleid}/like             |  POST  | 해당 유저의 특정 게시글을 좋아요 요청을 보냅니다.            |      |
| article/{articleid}/like             | DELETE | 해당 유저의 특정 게시글을 좋아요 취소 요청을 보냅니다.       |      |
| article/{articleid}/comment          |  GET   | 해당 유저의 특정 게시글의 댓글 리스트 정보를 반환합니다.     | Done |
| article/{articleid}/comment          |  POST  | 해당 유저의 특정 게시글에 댓글을 작성합니다.<br />**(Need Token)** | Done |
| article/comment/{commentid}          |  PUT   | 해당 유저의 특정 게시글 속 특정 댓글을 수정합니다.<br />**(Need Token)** | Done |
| article/comment/{commentid}          | DELETE | 해당 유저의 특정 게시글 속 특정 댓글을 삭제합니다.<br />**(Need Token)** | Done |
|                                      |        |                                                              |      |
| search/                              |  GET   | (검색어와 유사한 or 이미 검색했던) 유저 닉네임을 검색합니다  | Done |
| search/                              |  POST  | (최근 검색, 검색한 결과 정보를 얻기 위해) 검색한 값을 DB에 저장합니다. | Done |
|                                      |        |                                                              |      |
| alarm/                               |  GET   | 1. 다른 사용자가 내 게시글에 단 댓글 정보를 최신순 ???개로 반환합니다.<br />2. 팔로우 요청 리스트를 반환합니다. |      |
| alarm/{nickname}                     | DELETE | Follow DB 상에서 해당 팔로우 요청을 삭제합니다.              |      |
| alarm/{nickname}                     |  POST  | Follow DB 상에서 해당 팔로우 요청을 수락(False → True) 합니다. |      |
|                                      |        |                                                              |      |
| scrap/                               |  GET   | 본인이 스크랩한 게시글 리스트를 반환합니다.<br />**(Need Token)** |      |
| scrap/{articleid}                    |  POST  | 해당 게시글을 본인의 스크랩 리스트에 추가합니다.<br />**(Need Token)** |      |
| scrap/{scrapid}                      | DELETE | 스크랩한 해당 게시글을 삭제한다.<br />**(Need Token)**       |      |

<br>

### 개발 현황

| 명세서                       |                 담당                 | 현황 |
| ---------------------------- | :----------------------------------: | :--: |
| 계정 설정                    |     FE : 신형식<br />BE : 김백준     | Done |
| 본인 프로필 / 타 유저 프로필 |     FE : 정종우<br />BE : 김백준     |      |
| 팔로잉 / 팔로워 리스트       |     FE : 정종우<br />BE : 정지홍     |      |
| 알림 / 요청                  |     FE : 이두호<br />BE : 김백준     |      |
| 헤더                         |             FE : 이두호              | Done |
| 유저 검색                    |     FE : 이두호<br />BE : 김백준     | Done |
| 게시글(피드)                 | FE : 신형식<br />BE : 김백준, 정지홍 |      |
| 스크랩                       | FE : 정종우, 이두호<br />BE : 정지홍 |      |
| 댓글                         |     FE : 신형식<br />BE : 정지홍     |      |

<br>
