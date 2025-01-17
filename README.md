# Notice Board

**Notice Board**는 사용자들이 게시글을 작성, 수정, 삭제할 수 있는 웹 애플리케이션입니다. 이 프로젝트는 Spring Boot와 Thymeleaf를 사용하여 개발되었으며, 사용자 인증 및 권한 관리를
위해 Spring Security를 통합하였습니다.

## 주요 기능

- **게시글 목록 조회**: 모든 사용자가 게시글 목록을 볼 수 있습니다.
- **게시글 작성**: 인증된 사용자는 새로운 게시글을 작성할 수 있습니다.
- **게시글 수정**: 게시글 작성자는 자신의 게시글을 수정할 수 있습니다.
- **게시글 삭제**: 게시글 작성자는 자신의 게시글을 삭제할 수 있습니다.
- **사용자 인증 및 권한 관리**: Spring Security를 사용하여 사용자 인증 및 권한을 관리합니다.

## 기술 스택

- **백엔드**: Java, Spring Boot
- **프론트엔드**: Thymeleaf, HTML, CSS
- **데이터베이스**: H2 Database (개발 및 테스트용)
- **빌드 도구**: Maven

## 설치 및 실행

1. **프로젝트 클론**

   ```bash
   git clone https://github.com/ijieun0123/notice-board.git
   cd notice-board
   ```

2. **필요한 의존성 설치**

   Maven을 사용하여 필요한 의존성을 설치합니다.

   ```bash
   mvn clean install
   ```

3. **애플리케이션 실행**

   Spring Boot 애플리케이션을 실행합니다.

   ```bash
   mvn spring-boot:run
   ```

4. **웹 브라우저에서 접속**

   브라우저를 열고 [http://localhost:8080](http://localhost:8080)으로 접속합니다.

## 사용 방법

1. **회원 가입 및 로그인**

    - 회원 가입을 통해 새로운 계정을 생성합니다.
    - 생성한 계정으로 로그인합니다.

2. **게시글 작성**

    - 로그인 후, '새 글 작성' 버튼을 클릭하여 새로운 게시글을 작성할 수 있습니다.

3. **게시글 수정 및 삭제**

    - 자신의 게시글에는 '수정' 및 '삭제' 버튼이 표시됩니다.
    - '수정' 버튼을 클릭하여 게시글을 수정할 수 있으며, '삭제' 버튼을 클릭하여 게시글을 삭제할 수 있습니다.

## 테스트

JUnit과 Spring Boot Test를 사용하여 단위 테스트 및 통합 테스트를 작성하였습니다. 테스트를 실행하려면 다음 명령어를 사용하세요.

```bash
mvn test
```

## 블로그

[[프로젝트] [스프링부트] 게시판 - Create / Read](https://velog.io/@cock321/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EA%B2%8C%EC%8B%9C%ED%8C%90)<br/>
[[프로젝트] [스프링부트] 게시판 - 로그인 & 유저 권한 부여](https://velog.io/@cock321/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EA%B2%8C%EC%8B%9C%ED%8C%90-Create-Read)