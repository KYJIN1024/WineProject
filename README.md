# KOWASA.NET  코와사넷
 > 국산와인을 주제로 정보제공 정보교류를 목적으로 하는 웹프로젝트입니다.
![home-2](https://github.com/KYJIN1024/WineProject/assets/111983474/10279b2e-8396-4750-84ad-f2fa84655cd5)

## 목차
- [들어가며](#들어가며)
  - [프로젝트 소개](#1.프로젝트-소개)
  - [프로젝트 기능](#2.-프로젝트-기능)
  - [사용 기술](#3.-사용기술)
  - [실행화면](#4.-실행화면)

- [구조 및 설계](#구조-및-설계)
  - [패키지 구조](#1.-패키지구조)
  - [DB 설계](#2.-DB설계)
  - [API 설계](#3.-API설계)
- [개발내용](#개발내용)
- [마치며](#마치며)
  - [향후 보완해야할 사항](#1.-향후-보완해야할-사항)
  - [후기](#2.-후기)
 
## 들어가며

###1.프로젝트 소개

2021년 기준 국내 와인 판매량은 1,538만 4천 리터로 전년 대비 41.4% 증가하고 해마다 꾸준히 증가하고 있습니다. 이 중 국산 와인의 판매량은 140만 3천 리터로 전년 대비 60.2% 증가했습니다. 수입산와인에 비해 가격 경쟁력이 좋은 국내생산 과일로 만든 와인들이 인지도부족등의 이유로 소비자들에게 알려지지 않았습니다. 또한 기존의 와인관련 커뮤니티 웹사이트는 대부분 수입와인의 정보를 제공하고 있으며 국산와인을 주제로 정보를 제공하는 웹사이트는 희박합니다. 따라서 국산와인의 정보를 제공하고 커뮤니티 활성화를 통해 국산 와인의 인지도를 높여 궁긍적으로 국내와인산업의 발전과 와인농가의 수입증대를 목표로 웹사이트를 개발하였습니다.

참고한 벤치마킹 웹사이트  [와인21](https://www.wine21.com/) , [와인인](https://winein.co.kr/), [더술닷컴](https://thesool.com/)

이용한 공공데이터  [한국농수산식품유통공사_전통주정보(공공데이터포털)](https://www.data.go.kr/data/15048755/fileData.do)


**2. 프로젝트 기능**
  - **게시판**- CRUD기능, 조회수, 좋아요, 페이징, 검색
  - **사용자**- Security 회원가입 및 로그인, OAuth 2.0 구글 로그인, 회원정보(비밀번호 수정), 회원가입시 이메일인증 및 중복검사
  - **댓글**- CRUD기능

**3. 사용 기술**

**3-1. 백엔드**

**주요 프레임워크/라이브러리**
  - Java 11
  - Spring boot 2.7.1
  - JPA(Spring Data JPA)
  - Spring Security
  - OAuth2.0
  - Spring Boot Starter Mail

**Build Tool**
  - Maven 3.9

**DataBase**
  - MySQL 5.1.49

**Infra**
 - Nginx
 - AWS

**3-2. 프론트엔드**
 - Html/CSS
 - JavaScript
 - Thymeleaf
 - BootStrap 
    
**4. 실행화면**
<details>
    <summary>게시글관련</summary>
 
   1. 메인페이지
   ![home-1](https://github.com/KYJIN1024/WineProject/assets/111983474/3e1aa9ae-a2b4-403a-acdd-e4210f834324)
   로그인 및 커뮤니티 및 와인정보 페이지에 대한 소개 및 버튼을 통해 해당페이지로 redirect 할 수 있습니다.
    
   2. 와인검색페이지
   ![search](https://github.com/KYJIN1024/WineProject/assets/111983474/10e46d0f-270a-4218-8918-2ec87c0ad76c)
   통합검색 및 상세검색(원료별, 지역별, 용량별, 도수별)로 와인을 검색할수 있습니다. 

   3. 커뮤니티
       <details> 
          <summary> 커뮤니티- 자유게시판 </summary>
 
         - 게시글 목록 
           ![freeboard1](https://github.com/KYJIN1024/WineProject/assets/111983474/476f4489-7002-416d-bc28-17c8bd9d0c33)
 
         - 게시글 등록
           ![write](https://github.com/KYJIN1024/WineProject/assets/111983474/a1dea6b7-2aab-41e2-9b3d-8e3e4b43eb9d)
 
         - 게시글 상세보기
           ![view](https://github.com/KYJIN1024/WineProject/assets/111983474/bf1c9bdb-0770-4915-9814-4fee35afb0d2)
 
         - 게시글 수정
           ![modify](https://github.com/KYJIN1024/WineProject/assets/111983474/92562b17-cfaf-4a54-9a2b-6656f533feeb)
 
         - 게시글 삭제
      </details>
       <details> 
          <summary> 커뮤니티- 행사게시판 </summary>
 
         - 게시글 목록 
          ![list1](https://github.com/KYJIN1024/WineProject/assets/111983474/2e053fdf-ba4c-4b01-b6cf-b3bdd57ad471)
 
         - 게시글 등록
          ![write1](https://github.com/KYJIN1024/WineProject/assets/111983474/40dd8991-3165-43ca-9db1-184b785a0fad)
 
         - 게시글 상세보기
           ![view1](https://github.com/KYJIN1024/WineProject/assets/111983474/ff8759f6-c277-4ced-b195-7c3ee6f1cc29)
 
         - 게시글 수정
          ![modify1](https://github.com/KYJIN1024/WineProject/assets/111983474/89016d35-48b7-43bd-bc1f-0614b88d2100)
 
         - 게시글 삭제
      </details>
  4. 와인 파트너스
       <details> 
          <summary> 생산자,와인샵&레스토랑,구인 게시판 </summary>
        
      - 게시글 목록 
        ![list2](https://github.com/KYJIN1024/WineProject/assets/111983474/564f43c3-3b1f-4725-b953-488a9a83fceb)
      - 게시글 등록
        ![write2](https://github.com/KYJIN1024/WineProject/assets/111983474/8bc99d36-008d-43eb-8cf2-2d86d805aaa0)
      - 게시글 상세보기
        ![view2](https://github.com/KYJIN1024/WineProject/assets/111983474/b25f87c1-220e-4817-ab15-89801bdb3030)
      - 게시글 수정
        ![modify2](https://github.com/KYJIN1024/WineProject/assets/111983474/efa62444-c349-41c6-9d25-820b0df5bb06)
      - 게시글 삭제
      </details>  

   </details>
   
   <details>
    <summary>회원관련</summary>
    
   1.회원가입 화면
   ![register](https://github.com/KYJIN1024/WineProject/assets/111983474/1505f9b1-b860-4f6d-b55b-207e709d5723)

   2.로그인 화면
   ![login1](https://github.com/KYJIN1024/WineProject/assets/111983474/d88b428b-97bd-4b6f-a4f9-ebf5c346bc5e)
   
   2-1. OAuth 2.0 소셜 로그인 화면
   ![google login](https://github.com/KYJIN1024/WineProject/assets/111983474/694642b8-7525-4296-9293-88d3a51e75b2)


   3.마이페이지 화면
   ![mypage1](https://github.com/KYJIN1024/WineProject/assets/111983474/ba3e8469-db4d-4f69-bf41-d313b3a268a6)

   ![password change](https://github.com/KYJIN1024/WineProject/assets/111983474/867cdd6c-5749-475e-b0b2-3702568c83dc)
   
   ![writedpost](https://github.com/KYJIN1024/WineProject/assets/111983474/c4e9a5aa-5b4b-4848-b535-d2449e55ad76)
   
   ![liked](https://github.com/KYJIN1024/WineProject/assets/111983474/4fb82ca2-54a7-425f-8b40-0568e6ff1f43)

   </details>

   <details> 
     <summary>댓글관련</summary>

   1.댓글작성
   ![reply](https://github.com/KYJIN1024/WineProject/assets/111983474/fb52f55b-cae0-4e44-8536-c13ad32c0c2e)

   ![reply2](https://github.com/KYJIN1024/WineProject/assets/111983474/2432e957-3e79-4a4d-b756-15f57876c70c)

   2.댓글수정
   ![reply3](https://github.com/KYJIN1024/WineProject/assets/111983474/ec42f2d3-2c80-4847-bd8b-1727b653a098)

   3.댓글삭제
   ![reply4](https://github.com/KYJIN1024/WineProject/assets/111983474/a9e4bde6-b41c-4f94-b6e9-cc21a1f38045)
   </details> 

## 구조 및 설계

**1.패키지구조**
   <details> 
      <summary>패키지 구조보기</summary>
 
   ![project structure2](https://github.com/KYJIN1024/WineProject/assets/111983474/94ab966e-4f8f-4d7a-b443-3f6e1929434a)

   
   </details>
 
 **2. DB설계**
     <details> 
          <summary>ERD다이어그램</summary>

   ![WineProject2](https://github.com/KYJIN1024/WineProject/assets/111983474/5835efb5-4e11-4b64-999a-fe6bc96cb52c)
     </details>
    
**3. API설계**

[POSTMAN API문서](https://documenter.getpostman.com/view/31219336/2s9YXpUxpo)

**4. INFRA**
<details> 
      <summary>구조보기</summary>   
      
 ![2023-12-15 14 13 23](https://github.com/KYJIN1024/WineProject/assets/111983474/70b1d7d8-5892-48b0-b617-065948e789b8)
</details>

##개발내용

##마치며

**1. 향후 보완해야할 사항**

마이페이지 - SNS 로그인연동 기능추가 / 회원탈퇴 기능추가

회원가입 - 유효성검사 추가, 네이버 소셜로그인 추가

검색 - DB데이터추가

게시판 - 파일업로드 기능 추가

**2. 후기**

설계단계에서 중단되었던 팀프로젝트를 개인프로젝트로 이어서 처음으로 만들어본 프로젝트이기 때문에
배포를 완성하였을때 뿌듯함도 있었지만 아직 부족한부분도 많이 보여 아쉬움도 많이 남았습니다.
프로젝트를 진행하던도중 책, 블로그, 구글링 및 생성형프롬프트에 정보를 찾고 공부한 예제를 바탕으로 실제
코드를 입력하며 적용해보면서 다양한 에러를 만났습니다. 특히 마이페이지와 게시글댓글부분을 만들때는 정말 많이 헤맸습니다. 
다행히 에러를 해결하여 구동은 되었지만 구동측면이나 서비스측면에서 어떻게하면 좀더 효율적으로 할수있을까를 많이 고민하게되었습니다.

또한 강의중 배웠던 이론과 간단한예제로 익혔던 개념들을 실제 프로젝트를 통해 구현해보면서 java와 web에 좀더 이해할수 있게 되었습니다.
우선 기존에 작성했던 코드를 다시한번 정리하고 refatoring 해보면서 다른사람에게 내가 만든 프로젝트에 대해 문의를 하면 자신있게 설명할수 있도록 열심히 복습해야될것같습니다.
그리고 앞으로 다음 프로젝트를 할때는 기존과 다른 환경이나 도구를 사용하여 다른 주제로 프로젝트를 진행볼 계획입니다.
감사합니다.
