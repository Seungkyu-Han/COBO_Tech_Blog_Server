# ![CoBo](https://team-cobo.site/public/image/favicon/favicon-32x32.png) CoBo 기술 블로그

- 목적: CoBo팀이 개발 과정에서 생기는 문제들과 그 문제들을 해결해가는 과정을 기록하기 위한 블로그를 개발

- 개발 기간: 22.07.21 ~ 22.08.29

- [블로그 링크](https://team-cobo.site)

- [프론트엔드 깃허브](https://github.com/JongHyeok-Park/COBO_Tech_Blog)

- [백엔드 깃허브](https://github.com/Seungkyu-Han/COBO_Tech_Blog_Server)

## 목차

- [1. 프로젝트 계획](#프로젝트-계획)

- [2. 구성원 및 업무 분담](#구성원-및-업무-분담)

- [3. 페이지 설계](#페이지-설계)

- [4. DB 모델링](#DB-모델링)

- [5. 핵심 기능](#핵심-기능)

- [6. 사용한 기술](#사용한-기술)

- [7. 느낀 점](#느낀-점)

## 프로젝트 계획

1. 소개
    - 팀과 팀원들에 대한 소개 및 contact 할 수 있는 방법
    - 팀에서 진행했던 프로젝트들에 대한 소개
2. 글 작성
    - 팀원들은 로그인 한 후 기술 관련 글을 작성 가능
    - 모든 사용자는 작성한 글을 볼 수 있음


## 구성원 및 업무 분담

박종혁
- 팀장
- 프론트엔드
- 디자인 설계

한승규
- 백엔드
- 배포 환경 구축
- DB 설계


## 페이지 설계

- [페이지 설계.pdf](https://cobo-blog.s3.ap-northeast-2.amazonaws.com/pdf/Tech+Blog+Design.pdf)

## DB 모델링
<img src = "https://cobo-blog.s3.ap-northeast-2.amazonaws.com/img/blog-db.png" alt="db-model" width="900">

## 핵심 기능

### 카카오 로그인

> 카카오 로그인을 통해 사용자의 정보를 얻고 회원이라면 토큰들을 쿠키로 저장

```
- kakao Developer에서 제공하는 로그인을 통해 코드를 받아 옴

- 프론트에서 해당 코드를 파라미터에 넣어 다시 서버의 API를 호출함

- 해당 사용자의 id가 있다면, 로그인에 성공하고 없다면 로그인에 실패

- 사용자의 AccessToken과 RefreshToken을 JWT를 이용하여 생성한 후 발급

- 프론트에서는 받은 AccessToken과 RefreshToken을 쿠키를 이용하여 지정된 시간만큼 저장함

- 인증이 필요한 API에는 AccessToken을 가져와 Authorization에 넣고, AccessToken이 만료되었다면 RefreshToken을 이용하여 재발급

- Authorization 헤더를 확인한 후 유효한 토큰이면, 해당 토큰에서 사용자의 정보를 추출하고 유효하지 않다면 403 에러를 반환
```

### 글 업로드

> 작성한 글을 JavaScript를 통해 서버로 넘겨주고, 서버는 해당 데이터를 문자열로 S3에 저장

```
- 글 작성을 하기 위해 권장되지 않는 기능인 Document: execCommand()메소드를 사용함

- 각 버튼의 기능들은 JS로 기능을 부여하고, Selection과 Range객체를 사용하여 document내의 커서가 위치한 콘텐츠의 정보를 가져옴

- 이미지를 삽입할 때는 이미지 파일을 브라우저에서 받으면 서버에 API 호출하여 해당 이미지의 정보를 전달함

- 이미지를 받은 서버에서 해당 이미지 id값을 전달해주면 해당 id값을 이미지 dataset 속성에 할당하고 이미지를 마지막 커서 위치에 삽입

- 글 작성이 완료 되면 title, FileList, content, .... 등을 JSON으로 서버에 전달.

- 서버는 해당 Json에서 글의 본문에 해당하는 내용을 AWS의 S3를 이용하여 업로드 함

- 업로드 과정에서 파일의 내용은 중복이 되지 않도록 UUID를 이용하여 저장하고, 해당 파일의 이름은 DB에 TechPost의 정보와 함께 저장

- 후에 글을 조회하면 ID를 이용해 DB의 S3 파일 이름을 검색 한 후 해당 데이터를 읽어 클라이언트로 Json에 넣어 반환
```

[Document: execCommand() 참조 링크](https://developer.mozilla.org/ko/docs/Web/API/Document/execCommand)

### 조회수
> Redis를 사용하여 DB에 데이터를 한 번에 반영

```
- 스프링부트 실행 시 DB의 모든 TechPost의 Id를 확인 한 후 Redis에 키 값을 생성하여 저장

- 글을 게시할 때 해당 Id로 Redis에 키 값을 생성하여 저장, 삭제할 때 해당 키 값을 삭제

- 페이지에 접속 혹은 TechPost를 조회할 때 Redis에 저장되어 있는 값을 1씩 증가

- 동일 접속자에 대한 증가를 막기 위해 페이지에 접속하고 조회수를 증가할 때 프론트에서 hitCookie를 생성하여 저장

- 조회수를 확인 할 때, 해당 쿠키가 있으면 파라미터로 false를 전달하고 없으면 파라미터로 true를 전달하여 서버에게 알림

- 파라미터로 true가 들어온다면 redis의 데이터를 증가시켜 조회수에 반영

- 하루에 한 번 Spring Scheduler를 이용하여 Redis에 저장되어 있는 데이터를 DB에 반영하고 Redis의 데이터는 0으로 초기화
```

## 사용한 기술
| 범위 |                                                                                                                                                                                                                     스택                                                                                                                                                                                                                                                                                                                                                                                   |
| :-------: | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------: |
| Front End | <img src="https://noticon-static.tammolo.com/dgggcrkxq/image/upload/v1566995514/noticon/jufppyr8htislboas4ve.png" alt="" height="50"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <img src="https://noticon-static.tammolo.com/dgggcrkxq/image/upload/v1678672480/noticon/qblxu9uo0uuitucuzhjy.png" alt="" height="50"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <img src="https://noticon-static.tammolo.com/dgggcrkxq/image/upload/v1567008394/noticon/ohybolu4ensol1gzqas1.png" alt="" height="50"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <img src="https://noticon-static.tammolo.com/dgggcrkxq/image/upload/v1567128552/noticon/mksvojnxnqtvdwrhttce.png" alt="" height="50"/> |
| Back End  |                            <img src="https://noticon-static.tammolo.com/dgggcrkxq/image/upload/v1567008187/noticon/m4oad4rbf65fjszx0did.png" alt="" height="50"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <img src="https://noticon-static.tammolo.com/dgggcrkxq/image/upload/v1566913679/noticon/xlnsjihvjxllech0hawu.png" alt="" height="50"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <img src="https://noticon-static.tammolo.com/dgggcrkxq/image/upload/v1567064876/noticon/sb5llmvfubuceldbkmx8.png" alt="" height="50"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <img src="https://noticon-static.tammolo.com/dgggcrkxq/image/upload/v1603423163/noticon/az0cvs28lm7gxoowlsva.png" alt="" height="50"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <img src="https://noticon-static.tammolo.com/dgggcrkxq/image/upload/v1687307488/noticon/o9lxyva5z8zbwyeaxers.png" alt="" height="50"/>                            |
|  DevOps   |                                                                                                  <img src="https://noticon-static.tammolo.com/dgggcrkxq/image/upload/v1581824154/noticon/a5wsihnorfumuzu7ewdd.png" alt="" height="50"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <img src="https://noticon-static.tammolo.com/dgggcrkxq/image/upload/v1566777755/noticon/yfmwxv8nhnr5aqaxhxpg.png" alt="" height="50"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <img src="https://media.licdn.com/dms/image/D560BAQH0TLan23PvIQ/company-logo_200_200/0/1691549098754?e=2147483647&v=beta&t=OvBN0FX58BBE1MmuztyHW58HnFL6lfBWD9THjT-2_RQ" alt="" width="50"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <img src="https://noticon-static.tammolo.com/dgggcrkxq/image/upload/v1566798146/noticon/lku5cppzh8r7awwsmmko.png" alt="" width="50"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |


## 느낀 점

**박종혁**

- 자바스크립트로 Cookie를 처음 다뤄보는 프로젝트였으며, 한승규 팀원의 도움을 많이 받았다. 백엔드 담당인 한승규 팀원과 카카오 로그인 기능을 만들면서 토큰을 다루는 웹 표준에 대해서 많이 알게 되어서 너무 좋았고, 자바스크립트의 DOM에 대한 이해도 늘릴 수 있는 경험이었다.

- 글 작성하는 페이지를 만들면서 Selection, Range 등의 객체와 execCommand()메소드 등 잘 사용되지 않거나 브라우저마다 지원되지 않는 기능들을 몇 가지 사용하면서 어려움을 많이 겪었는데 다행히도 구글링으로 여러가지 자료들을 찾아 만들어 만족스럽게 만들 수 있어 뿌듯했다.

- 사전에 나름 어느정도 계획하고 시작했다고 생각했는데 막상 작업하면서 다른 팀원과 상의해야 할 부분이 많이 나와 계속 진행이 멈추는 부분들이 있어 난감했다. 그래서 이번 프로젝트를 계기로 다음에 기획할 때는 사전 기획 단계에서 최대한 기능이나 API 등을 명확하게 정해둔 뒤에 작업해야겠다고 느꼈다.

- 프로젝트를 거의 마무리 하고 작성한 코드를 보니 난잡하게 되어 코드 리뷰하기 어렵게 되어 있었다. 너무 기능에만 집중하고 빠르게 하려는 욕심 때문에 그랬던 것 같다. 앞으로는 다시 코드를 재활용하거나 리팩토링하기 용이하게 깔끔하게 정리하도록 노력해야겠다고 생각이 들었다.

**한승규**

- 이번 프로젝트에서는 코드의 가독성에 굉장히 신경을 썼었다. try-catch 구문이 굉장히 가독성이 안좋다고 생각해서 다른 방법을 찾아보다가 @RestControllerAdvice를 사용하게 되었다. Exception들을 한 곳에 모아 작성함으로 굉장히 가독성있게 코드를 작성 할 수 있었던 것 같다.

- BcryptPasswordEncoder를 사용하여 자체 로그인을 만드려고 했지만, 도중에 욕심이 생겨 kakao 로그인으로 변경해보았다. 덕분에 카카오 개발자 문서를 많이 읽어보게 되었고, 서버에서 서버간에 통신하는 코드도 작성해보게 되었다.

- 응답 속도를 최대한 줄이기 위해서 많은 방법들을 사용했었다. 모든 API들의 응답 속도가 너무 느려서, Nginx와 SpringBoot의 서버를 분리하고 SSL 인증과 프록시 패스로 이어주는 작업을 진행했었다. 이 방법으로 스프링 부트 자체의 속도를 높일 수 있었다. 또한 DB에 자주 접근해야 하는 값들은 Redis에 데이터를 모았다가 한 번에 DB에 반영하도록 했다. 덕분에 자주 사용하는 API들의 속도도 크게 높일 수 있었다. 서버의 성능이 좋지 않았기 때문에 속도를 최대한 올리기 위해 해본 방법들이 나중에는 큰 도움이 될 수 있을 것 같았다.

- 클라우드의 많은 기능들을 사용해 본 것 같다. AWS에서 S3와 ElastiCache를 사용했었는 데, 비록 프리티어로 사용했지만 해당 기능들을 사용한 것만으로도 굉장히 많은 성능 향상이 있었다. 또한 서버 증축과정에서 다른 클라우드 서비스로 이전했었는 데, 프로젝트의 배포 과정에서 기본 설정하는 과정을 경험하게 되었다.