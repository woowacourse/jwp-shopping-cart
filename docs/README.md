# 미션: 장바구니 서비스의 회원 기능 구현하기

## 1단계 - 인증

- [ ] Token 기반의 로그인

  <details>
  <summary>HTTP request</summary>
  
  ```
  POST /auth/login HTTP/1.1
  Content-Type: application/json
  
  {
    "email" : "123@gmail.com",
    "password" : "1234"
  }
  ```

  </details>

  <details>
  <summary>HTTP response - 정상</summary>

  ```
  HTTP/1.1 200 Ok
  Content-Type: application/json

  {
    "accessToken" : "?????"
  }
  ```

  </details>

  <details>
  <summary>HTTP response - 예외</summary>

  ```
  HTTP/1.1 401 Unauthorized
  ```

  </details>


- [ ] 회원가입

  <details>
  <summary>HTTP request</summary>

  ```
  POST /members HTTP/1.1
  Content-Type: application/json
  
  {
      "email" : "123@gmail.com"
      "password" : "1234"
  }
  ```

  </details>

  <details>
  <summary>HTTP response - 정상</summary>

  ```
  HTTP/1.1 201 Created
  ```

  </details>

  <details>
  <summary>HTTP response - 예외</summary>

  ```
  HTTP/1.1 400 Bad Request
  ```

  </details>


- [ ] 회원 정보 수정
  
  <details>
  <summary>HTTP request</summary>

  ```
  PUT /members HTTP/1.1
  Content-Type: application/json
  Authorization : ????

  {
    "password" : "1234",
    "new-password" : "2345"
    .....
  }
  ```
  </details>

  <details>
  <summary>HTTP response - 정상</summary>

  ```
  HTTP/1.1 200 OK
  ```

  </details>

  <details>
  <summary>HTTP response - 예외</summary>

  ```
  HTTP/1.1 401 Unauthorized
  ```

  </details>

- [ ] 회원탈퇴

  <details>
  <summary>HTTP request</summary>

  ```
  POST /auth/logout HTTP/1.1
  Authorization : ????
  ```

  </details>

  <details>
  <summary>HTTP response - 정상</summary>

  ```
  HTTP/1.1 204 no-content
  ```

  </details>
