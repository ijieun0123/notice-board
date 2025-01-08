# 게시판 프로젝트
이 프로젝트는 Spring Boot와 Thymeleaf를 사용하여 간단한 게시판을 구현하는 예제입니다.<br/> 
사용자는 게시글을 작성하고, 목록을 조회하며, 상세 페이지를 확인할 수 있습니다.<br/>
데이터베이스는 H2를 사용하고, 게시글은 Post라는 엔티티로 관리됩니다.

## 프로젝트 구조
    src
    └── main
        └── java
            └── com
                └── example
                    └── board
                        ├── controller
                        │   └── PostController.java
                        ├── dto
                        │   └── PostDto.java
                        ├── entity
                        │   └── PostEntity.java
                        ├── repository
                        │   └── PostRepository.java
                        └── service
                            └── PostService.java
    └── resources
        └── templates
            └── posts
                ├── form.html
                ├── list.html
                └── view.html
                └── application.properties

## 기술 스택
* Spring Boot: 서버 사이드 프레임워크
* Thymeleaf: HTML 템플릿 엔진
* H2 Database: 임베디드 데이터베이스
* JPA (Java Persistence API): 데이터베이스와의 연동
* Lombok: 코드 간소화 (Getter, Setter 자동 생성)

## 기능
* 게시글 작성
* 게시글 목록 조회
* 게시글 상세 조회

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

## 문제 해결
* Whitelabel Error Page: 템플릿 파일이 존재하지 않거나 경로가 잘못 설정되었을 경우 발생합니다. 템플릿 파일이 src/main/resources/templates/posts/ 디렉토리에 있는지 확인하세요.
* Cannot resolve 'content': PostDto나 PostEntity에 content 필드가 없을 경우 발생합니다. 필드가 정의되어 있는지 확인하세요

