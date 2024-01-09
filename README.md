# KOWASA.NET   [코와사넷](https://www.kowasa.net)  v1.0
 > 국산와인을 주제로 정보제공 정보교류를 목적으로 하는 웹프로젝트입니다.
![home-2](https://github.com/KYJIN1024/WineProject/assets/111983474/10279b2e-8396-4750-84ad-f2fa84655cd5)

## 목차
- [들어가며](#들어가며)
  * [프로젝트 소개](#1-프로젝트-소개)
  - [프로젝트 기능](#2-프로젝트-기능)
  - [사용 기술](#3-사용-기술)
  - [실행화면](#4-실행화면)

- [구조 및 설계](#구조-및-설계)
  - [패키지 구조](#1-패키지-구조)
  - [DB 설계](#2-db-설계)
  - [API 설계](#3-api-설계)
  - [INFRA 구동환경](#4-infra-구동환경)
- [개발내용](#개발내용)
- [마치며](#마치며)
  - [향후 보완해야할 사항](#1-향후-보완해야할-사항)
  - [후기](#2-후기)
 
# 들어가며

## 1. 프로젝트 소개

2021년 기준 국내 와인 판매량은 1,538만 4천 리터로 전년 대비 41.4% 증가하고 해마다 꾸준히 증가하고 있습니다. 이 중 국산 와인의 판매량은 140만 3천 리터로 전년 대비 60.2% 증가했습니다. 수입산와인에 비해 가격 경쟁력이 좋은 국내생산 과일로 만든 와인들이 인지도부족등의 이유로 소비자들에게 알려지지 않았습니다. 또한 기존의 와인관련 커뮤니티 웹사이트는 대부분 수입와인의 정보를 제공하고 있으며 국산와인을 주제로 정보를 제공하는 웹사이트는 희박합니다. 따라서 국산와인의 정보를 제공하고 커뮤니티 활성화를 통해 국산 와인의 인지도를 높여 궁긍적으로 국내와인산업의 발전과 와인농가의 수입증대를 목표로 웹사이트를 개발하였습니다.

참고한 벤치마킹 웹사이트  [와인21](https://www.wine21.com/) , [와인인](https://winein.co.kr/), [더술닷컴](https://thesool.com/)

이용한 공공데이터  [한국농수산식품유통공사_전통주정보(공공데이터포털)](https://www.data.go.kr/data/15048755/fileData.do)


## 2. 프로젝트 기능
  - **게시판**- CRUD기능, 조회수, 좋아요, 페이징, 검색
  - **사용자**- Security 회원가입 및 로그인, OAuth 2.0 구글 로그인, 회원정보(비밀번호 수정), 회원가입시 이메일인증 및 중복검사
  - **댓글**- CRUD기능

## 3. 사용 기술

### 3-1. 백엔드

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

### 3-2. 프론트엔드
 - Html/CSS
 - JavaScript
 - Thymeleaf
 - BootStrap 
    
## 4. 실행화면
<details>
    <summary>게시글관련</summary>
 
   1. 메인페이지
   ![home-1](https://github.com/KYJIN1024/WineProject/assets/111983474/3e1aa9ae-a2b4-403a-acdd-e4210f834324)
   로그인 및 커뮤니티 및 와인정보 페이지에 대한 소개 및 버튼을 통해 해당페이지로 redirect 할 수 있습니다.
    
   2. 와인검색페이지
   ![search](https://github.com/KYJIN1024/WineProject/assets/111983474/10e46d0f-270a-4218-8918-2ec87c0ad76c)
   와인명으로 검색 및 조건별 검색(원료별, 지역별, 용량별, 도수별)으로 와인을 검색할수 있습니다. 

   3. 커뮤니티
       <details> 
          <summary> 커뮤니티- 자유, 행사게시판 </summary>
 
         - 게시글 목록 
           ![freeboard1](https://github.com/KYJIN1024/WineProject/assets/111983474/476f4489-7002-416d-bc28-17c8bd9d0c33)
           와인과 관련된 질문답변, 정보공유등을 게시물 작성 및 댓글을 작성 할수 있습니다.  목록을 pageing하고 게시글 및 인기게시글을 조회할수 있습니다.

           ![list1](https://github.com/KYJIN1024/WineProject/assets/111983474/b79e2caf-9b24-423a-ba03-edb68cc7cdbe)
           와인관련 전시회, 시음회, 기업행사 등 행사정보 게시물을 조회할수 있습니다
           
         - 게시글 등록
           ![write](https://github.com/KYJIN1024/WineProject/assets/111983474/a1dea6b7-2aab-41e2-9b3d-8e3e4b43eb9d)

           로그인 한 사용자만 새로운 글을 작성할 수 있고, 작성 후 목록 화면으로 redirect합니다.
           
         - 게시글 상세보기
           ![view](https://github.com/KYJIN1024/WineProject/assets/111983474/bf1c9bdb-0770-4915-9814-4fee35afb0d2)
           ![eventboard2](https://github.com/KYJIN1024/WineProject/assets/111983474/77f4e6f6-c189-4a9c-a9e9-d1a9e4067560)

           본인이 작성한글만 수정 및 삭제가 가능합니다.
           
         - 게시글 수정
           ![modify](https://github.com/KYJIN1024/WineProject/assets/111983474/92562b17-cfaf-4a54-9a2b-6656f533feeb)
   
         - 게시글 삭제
           ![2023-12-18 12 21 05](https://github.com/KYJIN1024/WineProject/assets/111983474/54c5ecba-bddb-4a36-86cd-08dcb34dbabc)

           Confirm으로 삭제할지 확인하고, 삭제 후 전체 목록 리스트 화면으로 redirect 합니다.

   
       
  4. 와인 파트너스
       <details> 
          <summary> 생산자,와인샵&레스토랑,구인 게시판 </summary>
        
      - 게시글 목록 
        ![list2](https://github.com/KYJIN1024/WineProject/assets/111983474/564f43c3-3b1f-4725-b953-488a9a83fceb)

        생산자게시판- 와인생산자(와이너리)에 관한 정보를 조회할수 있습니다.
        
        와인샵&레스토랑게시판- 와인샵및 와인레스토랑에 대해 검색할수 있습니다.
        
        와인구인게시판- 와인과 관련된 구인정보를 조회할수 있습니다.
        
        
        목록을 pageing하고 로그인한 상태에서 게시글에 좋아요 버튼을 누를수 있습니다. 게시글 및 인기게시글을 조회할수 있습니다.

      - 게시글 등록
        ![write2](https://github.com/KYJIN1024/WineProject/assets/111983474/8bc99d36-008d-43eb-8cf2-2d86d805aaa0)

       로그인 한 사용자만 새로운 글을 작성할 수 있고, 작성 후 목록 화면으로 redirect합니다.
     
      - 게시글 상세보기
        ![view2](https://github.com/KYJIN1024/WineProject/assets/111983474/b25f87c1-220e-4817-ab15-89801bdb3030)

     글을 등록한 게시자만 수정과 삭제가 가능하며 사용자는 수정과 삭제가 불가능합니다.
        
      - 게시글 수정
        ![modify2](https://github.com/KYJIN1024/WineProject/assets/111983474/efa62444-c349-41c6-9d25-820b0df5bb06)

      - 게시글 삭제
        ![2023-12-18 16 29 07](https://github.com/KYJIN1024/WineProject/assets/111983474/a1c8c5ed-60fe-4e59-94f0-285fa7d1dfcc)

      Confirm으로 삭제할지 확인하고, 삭제 후 전체 목록 리스트 화면으로 redirect 합니다.


      </details>  
   
   </details>
   
   <details>
    <summary>회원관련</summary>
    
   1.회원가입 화면
   ![register](https://github.com/KYJIN1024/WineProject/assets/111983474/1505f9b1-b860-4f6d-b55b-207e709d5723)
   
   회원가입시 아이디중복확인 및 이메일 인증을 진행하며 완료시 회원정보를 저장하고 로그인 화면으로 이동합니다.
   
   
   2.로그인 화면
   ![login1](https://github.com/KYJIN1024/WineProject/assets/111983474/d88b428b-97bd-4b6f-a4f9-ebf5c346bc5e)

   2-1. OAuth 2.0 소셜 로그인 화면
   ![google login](https://github.com/KYJIN1024/WineProject/assets/111983474/694642b8-7525-4296-9293-88d3a51e75b2)
  구글로그인이 가능합니다.

   3.마이페이지 화면
   ![mypage1](https://github.com/KYJIN1024/WineProject/assets/111983474/ba3e8469-db4d-4f69-bf41-d313b3a268a6)

 비밀번호를  변경할수 있고, 자유게시판에서 작성한 게시글및 댓글을 출력하고 링크클릭시 해당게시물로 이동합니다.  좋아요표시를 누른 와인파트너스 게시물을 출력하고 클릭시 해당게시물로 이동합니다.

   ![password change](https://github.com/KYJIN1024/WineProject/assets/111983474/867cdd6c-5749-475e-b0b2-3702568c83dc)
   
   ![writedpost](https://github.com/KYJIN1024/WineProject/assets/111983474/c4e9a5aa-5b4b-4848-b535-d2449e55ad76)
   
   ![liked](https://github.com/KYJIN1024/WineProject/assets/111983474/4fb82ca2-54a7-425f-8b40-0568e6ff1f43)

   </details>

   <details> 
     <summary>댓글관련</summary>

   1.댓글작성
   ![reply](https://github.com/KYJIN1024/WineProject/assets/111983474/fb52f55b-cae0-4e44-8536-c13ad32c0c2e)

   ![reply2](https://github.com/KYJIN1024/WineProject/assets/111983474/2432e957-3e79-4a4d-b756-15f57876c70c)

댓글은 로그인한 사용자만 작성할수 있으며, 댓글작성시 현재페이지를 reload합니다.

   2.댓글수정
   ![reply3](https://github.com/KYJIN1024/WineProject/assets/111983474/ec42f2d3-2c80-4847-bd8b-1727b653a098)

글작성자이외에는 댓글을 수정하거나 삭제할수 없습니다.

   3.댓글삭제
   ![reply4](https://github.com/KYJIN1024/WineProject/assets/111983474/a9e4bde6-b41c-4f94-b6e9-cc21a1f38045)
   </details> 

# 구조 및 설계

## 1. 패키지 구조
   <details> 
      <summary>패키지 구조보기</summary>


   
  ![2023-12-18 11 52 04](https://github.com/KYJIN1024/WineProject/assets/111983474/c231f796-53a6-4f0f-8030-c6930357270c)


   
   </details>
 
## 2. DB 설계
   <details> 
        <summary>ERD다이어그램</summary>

   [ERD CLOUD](https://www.erdcloud.com/d/EAFeEnL43SMDDqydY)
    
   ![WIneProject](https://github.com/KYJIN1024/WineProject/assets/111983474/ad758f76-5ede-4e0b-82be-68b341afa816)

   </details>
    
## 3. API 설계

[POSTMAN API문서](https://documenter.getpostman.com/view/31219336/2s9YXpUxpo)

## 4. INFRA 구동환경
<details> 
      <summary>구조보기</summary>   
      
 ![2023-12-15 14 13 23](https://github.com/KYJIN1024/WineProject/assets/111983474/70b1d7d8-5892-48b0-b617-065948e789b8)

 로컬에서 개발한 Java Spring Boot 웹 프로젝트를 GitHub에 업로드하고, Putty로 EC2 인스턴스에 접속하여 설정 및 Amazon RDS의 DB를 연결하여 구동, Route53, Nginx를 사용하여 도메인주소로 연결하는 구조입니다.
</details>

## 개발내용
[JAVA Spring Boot 프로젝트- 1. 스프링 시큐리티를 이용한 회원가입 및 로그인 구현하기(1)](https://every-coding.tistory.com/16)

[JAVA Spring Boot 프로젝트- 1. 스프링 시큐리티를 이용한 회원가입 및 로그인 구현하기(2)](https://every-coding.tistory.com/17)

[JAVA Spring Boot 프로젝트-2. Oauth 2.0 구글로그인 구현하기](https://every-coding.tistory.com/18)

[JAVA Spring Boot 프로젝트-3. 아이디/비밀번호 찾기 기능 구현하기](https://every-coding.tistory.com/19)

[JAVA Spring Boot 프로젝트- 4. 와인 검색 페이지 구현](https://every-coding.tistory.com/20)

[JAVA Spring Boot 프로젝트- 5. 자유게시판 구현(페이징, 조회수)](https://every-coding.tistory.com/21)

[JAVA Spring Boot 프로젝트- 6.댓글 CRUD 구현](https://every-coding.tistory.com/21)

개발내용은 현재 [블로그](https://every-coding.tistory.com/) 에 작성중입니다.



# 마치며

## 1. 향후 보완해야할 사항

마이페이지 - SNS 로그인연동 기능추가

회원가입 - 유효성검사 추가, 네이버 소셜로그인 추가

검색 - DB데이터추가

게시판 - 파일업로드 기능 추가

메인페이지 - AI CHATING기능

## 2. 후기

설계단계에서 중단되었던 팀프로젝트를 개인프로젝트로 이어서 처음으로 만들어본 프로젝트이기 때문에
배포를 완성하였을때 뿌듯함도 있었지만 아직 부족한부분도 많이 보여 아쉬움도 많이 남았습니다.
프로젝트를 진행하던도중 책, 블로그, 구글링 및 생성형프롬프트에 정보를 찾고 공부한 예제를 바탕으로 실제
코드를 입력하며 적용해보면서 다양한 에러를 만났습니다. 특히 마이페이지와 게시글댓글부분을 만들때는 정말 많이 헤맸습니다. 
다행히 에러를 해결하여 구동은 되었지만 구동측면이나 서비스측면에서 어떻게하면 좀더 효율적으로 할수있을까를 많이 고민하게되었습니다.

또한 강의중 배웠던 이론과 간단한예제로 익혔던 개념들을 실제 프로젝트를 통해 구현해보면서 java와 web에 좀더 이해할수 있게 되었습니다.
우선 기존에 작성했던 코드를 다시한번 정리하고 refatoring 해보면서 다른사람에게 내가 만든 프로젝트에 대해 문의를 하면 자신있게 설명할수 있도록 열심히 복습해야될것같습니다.
그리고 앞으로 다음 프로젝트를 할때는 기존과 다른 환경이나 도구를 사용하여 다른 주제로 프로젝트를 진행볼 계획입니다.
감사합니다.
