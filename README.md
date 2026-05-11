


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




## How To Run

1. MySQL 서버를 실행합니다.
2. [db/init.sql](./db/init.sql)을 실행해 `expo_auth` 데이터베이스와 `users` 테이블을 생성합니다.
3. 프로젝트 루트에 `.env` 파일을 만든다.
4. `.\gradlew.bat bootRun` 또는 `./gradlew bootRun`으로 애플리케이션을 실행합니다.


## Shared Local Setup

- DB 접속 정보는 `.env`로 분리
- 실제 비밀번호는 저장소에 포함하지 않음
- DB 생성 스크립트는 `db/init.sql`로 공유

## 실행 방법

1. 저장소를 clone 받습니다.
2. `java -version`으로 Java 17이 설치되어 있는지 확인합니다.
3. MySQL Server를 실행합니다.
4. MySQL Workbench에서 새 연결을 생성합니다.
5. [db/init.sql](./db/init.sql)을 실행해 `expo_auth` 데이터베이스와 `users` 테이블을 생성합니다.
6. 프로젝트 루트에 `.env` 파일을 생성하고 위 예시 값을 입력합니다.
7. `.\gradlew.bat bootRun`으로 애플리케이션을 실행합니다.


성공 기준은 아래와 같습니다.

- `.env`를 읽지 못했다는 에러가 없음
- MySQL 연결 에러가 없음
- `Unknown database 'expo_auth'` 에러가 없음
- `JWT_SECRET`, `JWT_ACCESS_TOKEN_EXPIRATION`, `JWT_REFRESH_TOKEN_EXPIRATION` 누락 에러가 없음
- Spring Boot가 정상 기동됨

자주 실패하는 원인은 아래와 같습니다.

- Java 버전이 17이 아님
- MySQL 서버가 실행 중이 아님
- `db/init.sql`을 실행하지 않음
- `.env` 파일이 없거나 값이 비어 있음
- `MYSQL_URL`의 포트가 실제 MySQL 포트와 다름


