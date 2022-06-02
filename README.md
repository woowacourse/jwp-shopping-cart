# 🧺 장바구니

장바구니 미션 저장소

## 1단계 - 회원 기능

### 회원 가입

- 회원 가입 `URL: “/api/members”`
    - Method: POST
    - 성공 시 201 Created
    - 실패 시 400 Bad Request
        - 이메일 중복 검증 실패
        - 이메일 규칙 검증 실패
        ```java
        "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
        ```
        - 닉네임 규칙 검증 실패
        ```java
        한글 최대 5자
        ```
        - 비밀번호 규칙 검증 실패
        ```java
        최소 8자, 최대 20자 최소 하나의 문자, 하나의 숫자 및 하나의 특수 문자 :
        "^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,20}$"
        ```
        - 항목 누락
        - 서버가 뻗은 경우 (500 Internal Server Error)
    - 필요한 정보
        - email
        - nickname
        - password

- email 중복 체크 / 규칙 검증 `URL: “/api/members/check-email?email={email}”`
    - Method: GET
    - 성공 시 200 No Content
  ```javascript
    {
        "success": "true"
    }
  ```
    - 실패 시 400 Bad Request

### 로그인

- 로그인 `URL: “/api/login”`
    - MethodL POST
    - 성공 시 200 OK
      ```javascript
      {
        nickname: "...",
        token: "...",
      }
      ```
    - 실패 시 400 Bad Request
    - 필요한 정보
        - email
        - password

### 로그아웃

- 프론트엔드에서 JWT 토큰을 삭제한다.

### 회원 정보

- 회원 권한 인가
    - request: `Authorization: Bearer {token}`
    - 로그인이 안 되어있는 경우 401 Unauthorized
    - 토큰의 시간이 만료된 경우 401 Unauthorized
    - 삭제된 회원의 토큰으로 접근하는 경우 401 Unauthorized

    - 회원 정보 조회 `URL: “/api/auth/members/me” + 토큰 정보로 식별`
        - Method GET
            - 성공 시 200 OK
            ```javascript
            {
              email: ,
              nickname: ,
            }
            ```
    - 회원 정보 수정
        - 성공 시 204 No Content
        - 닉네임 수정 `URL: “/api/auth/members/me” + 토큰 정보로 식별`
        ```javascript
        {
          nickname: ,
        }
        ```
        - 비밀번호 수정 `URL: “/api/auth/members/password” + 토큰 정보로 식별`
        ```javascript
        {
          password: ,
        }
        ```
    - 회원 탈퇴 `URL: “/api/auth/members/me” + 토큰 정보로 식별`
        - Method DELETE
        - 성공 시 204 No Content
