## 파일 구성

- `mysql-connector-j-9.3.0.jar`  
  - MySQL JDBC 드라이버입니다.
- '-2026-1-DBProject_Team10-main'
  - java 코드 파일입니다

    - lib
      - JDBC 드라이버를 위치시켜야 합니다.
    - src
      - dao: dao 클래스 파일들이 있습니다.
      - dto: dto 클래스 파일들이 있습니다.
      - main: 전체 코드인 메인 코드가 있습니다.
      - util: 데이터베이스 접속을 위한 유틸리디 메서드가 있습니다.

## 준비 단계

1. `mysql-connector-j-9.3.0.zip` 압축 해제
2. `-2026-1-DBProject_Team10-main.zip` 압축 해제

3. 아래와 같이 **MySQL Connector/J 파일을 프로젝트 lib 폴더로 복사**합니다.

- 압축을 해제하면 `mysql-connector-j-9.3.0` 폴더가 보입니다

```text
-2026-1-DBProject_Team10/
  lib/
    mysql-connector-j-9.3.0.   ← 여기에 위치하도록 복사
