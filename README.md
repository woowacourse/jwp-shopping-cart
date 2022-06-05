# 장바구니 1단계 - 회원 기능

장바구니 미션 저장소

## 미션 내용

- JWT를 이용한 액세스 토큰 발급 기능 구현
- 발급한 토큰을 이용하여 로그인이 필요한 기능 요청 시 포함하여 보냄
    - 회원 가입
    - 로그인 (토큰 발급)
    - 회원 정보 조회 (토큰 활용)
    - 회원 정보 수정 (토큰 활용)
    - 회원 탈퇴 (토큰 활용)
- 위 기능 동작하도록 구현

### 회원 정보

- Email
    - 이메일 형식 검사
- Password
    - 최소 8자 이상 16자 이하
    - 대소문자 구분 각각 1개 이상, 숫자 1개 이상, 특수문자 1개 이상
- 이름
    - 최소 1자 이상 30자 이하
- 전화번호
    - 010-xxxx-xxxx 형식
- 주소
  - 최소 1자 이상

## API

- [x] 회원 가입
    - `요청`

        ```java
        POST /customers HTTP/1.1
        Content-Type: application/json
        
        {
          "email" : "이메일"
          "password" : "비밀번호"
          "name" : "이름"
          "phone" : "전화번호"
          "address" : "주소"
        }
        ```

    - `응답`

        ```java
        HTTP/1.1 201 Created
        Content-Type: application/json
        
        {
          "id" : 1,
          "email" : "이메일"
          "name" : "이름"
          "phone" : "전화번호"
          "address" : "주소"
        }
        ```

    - `예외`

        ```java
        HTTP/1.1 400 Bad Request
        Content-Type: application/json
        
        {
            "message: "예외 메세지"
        }
        ```
        - `예외`
            - 비밀번호 형식 예외
            - 이메일 형식 예외
            - 이름 형식 예외
            - 전화번호 형식 예외
            - 주소 형식 예외
- [x] 로그인
    - `요청`

      ```java
      POST /customers/login HTTP/1.1
      Content-Type: application/json
      
      {
        "email" : "이메일"
        "password" : "비밀번호"
      }
      ```

    - `응답`

        ```java
        HTTP/1.1 200 Ok
        Content-Type: application/json
    
        // TokenResponse
        {
          "accessToken" : "Token 값"
        (논의 필요)  "email" : "이메일"
        (논의 필요)  "name" : "이름"
        (논의 필요)  "phone" : "전화번호"
        (논의 필요)  "address" : "주소"
        }
        ```

    - `예외`
        - 아이디나 비밀번호가 틀렸을 경우 : `아이디 또는 비밀번호가 틀렸습니다.`
- [x] 회원 정보 조회
    - `요청`

          ```java
          GET /customers HTTP/1.1
          Content-Type: application/json
          Authorization : accessToken
          ```

    - `응답`

        ```java
        HTTP/1.1 200 Ok
        Content-Type: application/json
        
        {
          "email" : "이메일"
          "name" : "이름"
          "phone" : "전화번호"
          "address" : "주소"
        }
        ```
    - `예외`
        - Token 값이 잘못되었을 경우
- [x] 회원 정보 수정
    - `요청`

      ```java
      PUT /customers HTTP/1.1
      Content-Type: application/json
      Authorization : accessToken
      {
          "email" : "이메일"
        "name" : "이름"
        "phone" : "전화번호"
        "address" : "주소"
          "password" : "비밀번호"
      }
      ```

    - `응답`

      ```java
      HTTP/1.1 200 Ok
      Content-Type: application/json
      ```
    - `예외`
        - Token 값이 잘못되었을 경우
- [x] 회원 탈퇴
    - `요청`

        ```java
        DELETE /customers HTTP/1.1
        Content-Type: application/json
        Authorization : accessToken
        ```

    - `응답`

        ```java
        HTTP/1.1 204 No Content
        Content-Type: application/json
        ```
