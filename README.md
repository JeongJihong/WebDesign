# Pipl

<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/0d43e80f-2446-4dc5-be4d-2b245936ed5e/main-icon.svg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210730%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210730T023117Z&X-Amz-Expires=86400&X-Amz-Signature=cc79d401d2d211ce2db5d1ed2d8c37b6c55af16f8f2051ca1e48017c94b7ddfb&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22main-icon.svg%22" width= "10%">    **Pipl** 은 **Pi**ck a **pl**an 의 줄임말로, SNS 상에서 모임/약속을 쉽게 잡아주는 소셜 플랫폼입니다.

<br>

<br>

<img src="https://shields.io/badge/vue--cli-v4.1.1-plastic" style="width: 10%;"> <img src="https://shields.io/badge/spring--boot-red" style="width: 8.3%;"> <img src="https://shields.io/badge/maven-v3.6.3-hotpink" style="width: 10%;"> <img src="https://shields.io/badge/zulu%208-blue" style="width: 5.4%;"> <img src="https://shields.io/badge/MySQL-blue" style="width: 6%;">

<br>

**Pipl 개발 페이지**  &#10140;  [Notion](https://www.notion.so/472849a205114733b53d218219d4b8a7)  /  [Jira](https://jira.ssafy.com/secure/RapidBoard.jspa?rapidView=9511&projectKey=S05P12B302)  /  [WireFrame](https://www.figma.com/file/GzAF1xAtlr1X2IS0iNzggx?embed_host=notion&kind=&node-id=0%3A1&viewer=1)

<br>

**Pipl 개발자**

​	**FE**    팀장    정종우    jongs3030@naver.com

​	**BE**    팀원    김백준    henry9489@gmail.com

​	**FE**    팀원    신형식    shs950930@gmail.com

​	**FE**    팀원    이두호    dhdev5ba@gmail.com

​	**BE**    팀원    정지홍    jjh1731@naver.com

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
- [개발현황](#개발현황)



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

  <img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/0d43e80f-2446-4dc5-be4d-2b245936ed5e/main-icon.svg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210730%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210730T023117Z&X-Amz-Expires=86400&X-Amz-Signature=cc79d401d2d211ce2db5d1ed2d8c37b6c55af16f8f2051ca1e48017c94b7ddfb&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22main-icon.svg%22" style="width: 25%">     <img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/c1fc4306-3000-4635-a170-769300682963/main-icon-2.svg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210730%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210730T023242Z&X-Amz-Expires=86400&X-Amz-Signature=e289dfbdb336a618dabaa222067d5cfddfd963e2cb5feb19c9630fb25c4ab4b2&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22main-icon-2.svg%22" style="width: 25%">

<br>

### 핵심 라이브러리

<br>

### ERD

<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/ca8e6475-b443-4ccc-bbd8-5c46c1a71a5d/_.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210729%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210729T023738Z&X-Amz-Expires=86400&X-Amz-Signature=b35f0c4ef9f7a549f87e67491d52b22c40ef7f82524e492c43974efc24f36774&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22_.png%22">

<br>

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

### 개발현황

#### 로그인

<img src ="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/3f2121b0-3aa2-4dc0-a344-7816a30442f6/login.gif?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210729%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210729T041816Z&X-Amz-Expires=86400&X-Amz-Signature=f3b210be241912b9f6f5f57bff5c2f820cdd087c9308cce4b70a09740645d922&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22login.gif%22" width="25%">         <img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/fad4f7b8-cae7-4178-8dd5-94aa51646060/login2.gif?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210729%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210729T042120Z&X-Amz-Expires=86400&X-Amz-Signature=8a0b9f1391692d181cf4542937ed0375545ca7c33e4adbf1ec3f978f121f14eb&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22login2.gif%22" width="25%">        <img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/7280bbce-f8e3-4c7f-8ba1-b9bbcb3a2376/login3.gif?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210729%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210729T042258Z&X-Amz-Expires=86400&X-Amz-Signature=6857aa81d16ea8b00e1d79098e4473674a180283765b69c09fcbf2851c13804a&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22login3.gif%22" width="25%">

로그인 사용자만 이용할 수 있는 SNS 서비스이기 때문에, 로그인하여 vuex 및 localStorage 에 저장되는 token 이 없으면 Login 페이지로 이동합니다.

<br>

#### 회원가입

<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/1f39567e-d3a1-41a6-b2ca-8189a80bb1e0/signup.gif?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210729%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210729T043819Z&X-Amz-Expires=86400&X-Amz-Signature=e2620d94392ac1474783aa6b1ed568d4178564878eac4061c8803acff39bc1e1&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22signup.gif%22" width=25%>

고유한 닉네임, 이메일을 가지도록 **중복확인 기능**을 추가하였습니다.

닉네임, 이메일 그리고 비밀번호가 일차적으로 Frontend 에서 유효성 검사를 통해 통과시에만 가입하기 버튼이 활성화됩니다.

<br>

#### 프로필

<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/c101254c-4064-41ad-a43b-d15ce7a40e67/myProfile.gif?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210729%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210729T051513Z&X-Amz-Expires=86400&X-Amz-Signature=e46c963652141ff71fe417b5803e0d1874bd2ce3d5796f2d935bf76d43936b88&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22myProfile.gif%22" width="25%">        <img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/c89efba5-0595-4696-a860-23ac413b2d06/profile_change.gif?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210729%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210729T044321Z&X-Amz-Expires=86400&X-Amz-Signature=ecdcb7a20b00b22027f4a438dc01bdf473970d8d2bcdba3807cb04612b48b6bd&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22profile_change.gif%22" width="25%">

Vue 의 Route.js 에서 Parameter 에 nickname 을 담아 보내, 해당 정보로 프로필을 렌더링합니다.

프로필 정보 수정 페이지에서 닉네임과 본인 소개글 및 프로필 이미지를 수정할 수 있습니다.

<br>

#### 팔로워/팔로잉 리스트

<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/b18dc3ea-512d-46c4-89bf-c13b816ec0f6/follower.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210729%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210729T044757Z&X-Amz-Expires=86400&X-Amz-Signature=3eefa36b5f25356882312f39ef075d6ce73dbd114434ab1974abc2c1045d5e20&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22follower.png%22" width="25%">        <img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/cb943cf0-2a38-4ae5-af8d-f10544c5dc95/following.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210729%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210729T044823Z&X-Amz-Expires=86400&X-Amz-Signature=336d6fd43e5fb7d9b2c6b5975e43079c0541e71bf676ac5885ec4d3492873985&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22following.png%22" width="25%">

bootstrap-vue 의 b-tap 을 활용하여 한 페이지 속에서 팔로잉/팔로워 리스트를 구현하였습니다.

<br>

#### 알림/요청

<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/9251682e-8435-4447-a9a5-4939f082c889/alarm.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210729%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210729T044930Z&X-Amz-Expires=86400&X-Amz-Signature=7c68ebd416a25289366c00183b8a2483c04a1d35497edb3277a8f528c7700c15&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22alarm.png%22" width="25%">

<br>

#### 유저 검색

<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/136d0ec3-ef2b-411e-bc1f-cf2ba384fc3c/search.gif?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210729%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210729T045032Z&X-Amz-Expires=86400&X-Amz-Signature=70e13caf764f843f50843ce1b2e74bc764e010156e428a2bb1a433295603e15a&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22search.gif%22" width="25%">

검색 페이지 방문 시, 혹은 검색창이 비어있을 경우 최근 검색 기록을 보여줍니다.

검색 시 해당 단어가 포함된 결과를 보여줍니다.

<br>

#### 게시글/피드

<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/b286754d-6411-446d-8cda-846764f8b683/FeedMain_Navbar.gif?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210729%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210729T045213Z&X-Amz-Expires=86400&X-Amz-Signature=9161ff4af9f8c354765ce8c70dc45465d2c11c95fd5f41bb1f12eb9e5a446966&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22FeedMain_Navbar.gif%22" width="25%">

<br>

#### 스크랩

<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/2e1e634b-c8a2-44b3-8055-e13436772374/scrap.gif?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210729%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210729T063305Z&X-Amz-Expires=86400&X-Amz-Signature=14e69825019b0609792f4c11d7d41327beed76ba6164936f4b785cf963f46e17&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22scrap.gif%22" width="25%">

스크랩 기능을 이용하여 따로 보관하고 싶은 게시글을 관리할 수 있습니다.

<br>

#### 댓글

<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/f5cedb46-3a69-4d1e-86d2-408a39dafc9c/comments.gif?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210729%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210729T063410Z&X-Amz-Expires=86400&X-Amz-Signature=fa7e4f752ae16a9589745a9c2f1dff3d48d5a4e50c037094ffdef396485f622e&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22comments.gif%22" width="25%">

게시글 속 댓글보기를 통해 확인할 수 있는 댓글 페이지입니다.

댓글을 작성할 수 있으며, 본인이 작성한 댓글을 수정 및 삭제 가능합니다.

<br>

