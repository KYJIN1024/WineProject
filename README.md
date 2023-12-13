# KOWASA.NET  코와사넷
 > 국산와인을 주제로 정보제공 정보교류를 목적으로 하는 웹프로젝트입니다.
![home-2](https://github.com/KYJIN1024/WineProject/assets/111983474/10279b2e-8396-4750-84ad-f2fa84655cd5)

## 목차
- 들어가며
  - 프로젝트 소개
  - 프로젝트 기능
  - 사용 기술
  - 실행화면

- 구조 및 설계
  - 패키지 구조
  - DB 설계
  - API 설계
- 개발내용
- 마치며
  - 프로젝트 보완사항
  - 후기
 
## 들어가며

**1. 프로젝트 소개**
2021년 기준 국내 와인 판매량은 1,538만 4천 리터로 전년 대비 41.4% 증가하고 해마다 꾸준히 증가하고 있습니다. 이 중 국산 와인의 판매량은 140만 3천 리터로 전년 대비 60.2% 증가했습니다. 수입산와인에 비해 가격 경쟁력이 좋은 국내생산 과일로 만든 와인들이 인지도부족등의 이유로 소비자들에게 알려지지 않았습니다. 또한 기존의 와인관련 커뮤니티 웹사이트는 대부분 수입와인의 정보를 제공하고 있으며 국산와인을 주제로 정보를 제공하는 웹사이트는 희박합니다. 따라서 국산와인의 정보를 제공하고 커뮤니티 활성화를 통해 국산 와인의 인지도를 높여 궁긍적으로 국내와인산업의 발전과 와인농가의 수입증대를 목표로 웹사이트를 개발하였습니다.

**2. 프로젝트 기능**
  - **게시판**- CRUD기능, 조회수, 좋아요, 페이징, 검색
  - **사용자**- Security 회원가입 및 로그인, OAuth 2.0 구글 로그인, 회원정보(비밀번호 수정), 회원가입시 이메일인증 및 중복검사
  - **댓글**- CRUD기능

**3. 사용 기술**

**3-1 백엔드**

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

**3-2 프론트엔드**
 - Html/CSS
 - JavaScript
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
        - ![write1](https://github.com/KYJIN1024/WineProject/assets/111983474/40dd8991-3165-43ca-9db1-184b785a0fad)

        - 게시글 상세보기
          ![view1](https://github.com/KYJIN1024/WineProject/assets/111983474/ff8759f6-c277-4ced-b195-7c3ee6f1cc29)

        - 게시글 수정
         ![modify1](https://github.com/KYJIN1024/WineProject/assets/111983474/89016d35-48b7-43bd-bc1f-0614b88d2100)

        - 게시글 삭제
     </details>


      


    
      

    

</details>





