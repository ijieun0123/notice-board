# 게시판 프로젝트

이 프로젝트는 Spring Boot와 Thymeleaf를 사용하여 간단한 게시판을 구현하는 예제입니다.<br/>
사용자는 회원가입 및 로그인을 거쳐 게시글을 작성, 수정, 삭제할 수 있습니다.<br/>
데이터베이스는 H2를 사용합니다.<br/>
로그인 기능은 사용자 인증을 위한 커스텀 로그인 페이지와 Spring Security를 사용하여 구현되었습니다.

<br/>

## 기술 스택

* Spring Boot: 서버 사이드 프레임워크
* Thymeleaf: HTML 템플릿 엔진
* H2 Database: 임베디드 데이터베이스
* JPA (Java Persistence API): 데이터베이스와의 연동
* Lombok: 코드 간소화 (Getter, Setter 자동 생성)
* Spring Security: 커스텀 로그인 페이지
* SHA-256: 비밀번호 암호화
* Logger : 로그 생성

<br/>

## 기능

* 게시글 작성
* 게시글 목록 조회
* 게시글 상세 조회
* 게시글 수정
* 게시글 삭제
* 회원가입
* 로그인

<br/>

## Post( 게시글 ) 구성

1. Entity: 데이터베이스와 매핑되는 객체<br/><br/>
   데이터베이스와 연결된 객체, 게시글 데이터를 저장.<br/>
   Post 엔티티는 데이터베이스의 posts 테이블과 매핑되며, 게시글에 대한 정보를 저장합니다.


2. DTO (Data Transfer Object): 데이터 전송을 위한 객체<br/><br/>
   클라이언트와 서버 간 데이터 전송을 위한 객체, 필요한 정보만 포함.<br/>
   DTO는 클라이언트와 서버 간의 데이터 전송을 최적화하는 역할을 합니다.<br/> PostDTO는 클라이언트에게 필요한 게시글의 정보만 전달합니다.


3. Model: 비즈니스 로직을 처리하거나 데이터를 표현하는 객체<br/><br/>
   화면에 표시할 데이터나 비즈니스 로직을 처리<br/>
   PostModel은 게시글을 화면에 표시할 때 필요한 데이터를 준비하거나, 비즈니스 로직을 포함할 수 있습니다.<br/> 예를 들어, 게시글 요약을 제공하는 메소드를 추가할 수 있습니다.


4. PostService: 비즈니스 로직 처리<br/><br/>
   비즈니스 로직을 처리, DTO와 엔티티 간 변환.<br/>
   서비스 계층에서는 PostDTO를 사용하여 클라이언트와 데이터를 주고받고,<br/>PostModel을 통해 필요한 비즈니스 로직을 처리합니다.


5. PostController: 클라이언트 요청 처리<br/><br/>
   클라이언트 요청을 처리하고, DTO를 사용하여 데이터를 주고받음.<br/>
   컨트롤러에서는 PostDTO를 사용하여 클라이언트와 데이터를 주고받고,<br/> PostModel을 통해 화면에 표시할 데이터를 준비합니다.


6. PostRepository: 데이터베이스와 상호작용<br/><br/>
   PostRepository는 JPA를 사용하여 데이터베이스와 상호작용합니다.<br/> Post 엔티티와 매핑되어 CRUD 작업을 처리합니다.


7. HTML 뷰: Thymeleaf 템플릿<br/><br/>
   list.html (게시글 목록 페이지)<br/>
   form.html (게시글 작성 폼)<br/>
   detail.html (게시글 상세 페이지)

<br/>

## 실행 방법

1. 애플리케이션 실행:

* src/main/java/com/example/board/BoardApplication.java 파일을 실행하여 Spring Boot 애플리케이션을 시작합니다.

2. H2 콘솔 접속:

* http://localhost:8080/h2-console에 접속하여 H2 데이터베이스 콘솔을 사용할 수 있습니다.
* JDBC URL: jdbc:h2:mem:testdb
* 사용자명: sa
* 비밀번호: 비워두기

3. 게시판 사용:

* 게시글 목록: http://localhost:8080/posts
* 게시글 작성: http://localhost:8080/posts/new
* 게시글 상세 보기: http://localhost:8080/posts/{id} (예: http://localhost:8080/posts/1)

<br/>

## 문제 해결

* Whitelabel Error Page: 템플릿 파일이 존재하지 않거나 경로가 잘못 설정되었을 경우 발생합니다. 템플릿 파일이 src/main/resources/templates/posts/ 디렉토리에 있는지
  확인하세요.
* Cannot resolve 'content': PostDto나 PostEntity에 content 필드가 없을 경우 발생합니다. 필드가 정의되어 있는지 확인하세요

<br/>

## 블로그

[[프로젝트] [스프링부트] 게시판 - Create / Read](https://velog.io/@cock321/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EA%B2%8C%EC%8B%9C%ED%8C%90)
[[프로젝트] [스프링부트] 게시판 - 로그인 & 유저 권한 부여](https://velog.io/@cock321/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EA%B2%8C%EC%8B%9C%ED%8C%90-Create-Read)