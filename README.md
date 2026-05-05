# Expo Backend Assignment

JWT를 이용한 회원가입, 로그인, Refresh Token 재발급 기능 구현 과제입니다.


## Tech Stack

- Java 17
- Spring Boot 3.5.0
- Spring Web
- Spring Data JPA
- Spring Security
- Spring Validation
- MySQL
- Lombok
- Gradle

## Project Structure

```text
src/main/java/com/expo/backendassignment
├─ domain
│  └─ user
│     ├─ repository
│     ├─ service
│     ├─ controller
│     └─ dto
└─ global
   ├─ exception
   ├─ response
   └─ config
```

## Database

- Database Name: `expo_auth`
- Table Name: `users`
- DB init script: [db/init.sql](./db/init.sql)
- JPA ddl-auto: `validate`


## How To Run

1. MySQL 서버를 실행합니다.
2. [db/init.sql](./db/init.sql)을 실행해 `expo_auth` 데이터베이스와 `users` 테이블을 생성합니다.
3. [.env.example](./.env.example)를 참고해서 `MYSQL_URL`, `MYSQL_USERNAME`, `MYSQL_PASSWORD`를 입력합니다.
4. 아래 명령어로 애플리케이션을 실행합니다.

```bash
./gradlew bootRun
```

Windows에서는 아래 명령어를 사용합니다.

```powershell
.\gradlew.bat bootRun
```

## Shared Local Setup

- DB 접속 정보는 `.env`로 분리
- 실제 비밀번호는 저장소에 포함하지 않음
- DB 생성 스크립트는 `db/init.sql`로 공유
- JPA는 기존 스키마를 검증만 하도록 `ddl-auto=validate` 사용

## Planned Features

- 회원가입 API
- 로그인 API
- JWT Access Token 발급
- Refresh Token 발급 및 재발급
- 공통 응답 형식
- Global Exception Handler
- 에러 코드 및 메시지 정의
