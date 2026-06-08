## 파일 구성

- `mysql-connector-j-9.3.0.jar`  
  - MySQL JDBC 드라이버입니다.

- `-2026-1-DBProject_Team10-main/`  
  - Java 프로젝트 폴더입니다.
  - 구성:
    - `lib/`
      - JDBC 드라이버(`mysql-connector-j-9.3.0`)를 위치시켜야 합니다.
    - `src/`
      - `dao/` : DAO 클래스 파일들이 있습니다.
      - `dto/` : DTO 클래스 파일들이 있습니다.
      - `main/` : 전체 프로그램의 진입점인 `Main` 코드가 있습니다.
      - `util/` : 데이터베이스 접속을 위한 유틸리티 메서드가 있습니다.

## 준비 단계

1. `mysql-connector-j-9.3.0.zip` 압축 해제
2. `-2026-1-DBProject_Team10-main.zip` 압축 해제

3. 아래와 같이 **mysql-connector-j-9.3.0 폴더를 프로젝트 lib 폴더로 복사**합니다.

- 압축을 해제하면 `mysql-connector-j-9.3.0` 폴더가 보입니다.
- 이 폴더 전체를 다음 경로로 복사합니다.

```text
-2026-1-DBProject_Team10-main/
  lib/
    mysql-connector-j-9.3.0   ← 여기에 위치하도록 복사
