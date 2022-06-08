# 장바구니 API

## 회원 API

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
- [x] 회원 이름 조회
    - `요청`
        ```java
        GET /customers/name HTTP/1.1
        Content-Type: application/json
        Authorization : accessToken
        ```

    - `응답`

        ```java
        HTTP/1.1 200 Ok
        Content-Type: application/json
        
        {
          "name" : "이름"
        }
        ```
    - `예외`
        - Token 값이 잘못되었을 경우

## 장바구니 API

### 상품

- [x] 상품 목록 조회
    - `요청`

        ```java
        GET /products HTTP/1.1
        Authorization: "Bearer 유저 토큰"
        ```

    - `응답`
        ```java
        HTTP/1.1 200 OK
        Content-Type: application/json
        {products : [
            {
                id: '상품 ID',
                thumbnail: '상품 이미지 url',
                name: '상품 이름',
                price: 상품 가격,
                quantity: 장바구니 수량
            },{
                id: '상품 ID',
                thumbnail: '상품 이미지 url',
                name: '상품 이름',
                price: 상품 가격,
                quantity: 장바구니 수량
            }
            ...
        ]}
      
        ```

### 장바구니

- [x] 장바구니 추가
    - `요청`
      ```
      POST [논의 필요] /customers/carts HTTP/1.1
      Content-Type: application/json
      Authorization: "Bearer 유저 토큰"
      [
          {
            "productId": "상품 ID",
            "quantity": "상품 수량"
          }
      ]
      ```
    - `응답`
      ```java
      HTTP/1.1 201 Created
      Content-Type: application/json
      ```
- [x] 장바구니 삭제
    - `요청`
      ```java
      DELETE /customers/carts HTTP/1.1
      Content-Type: application/json
      Authorization: "Bearer 유저 토큰"
      {
          "productIds": [1, 2, 3]
      }
      ```

    - `응답`
        ```java
        HTTP/1.1 204 No Content
        Content-Type: application/json
        ```
- [x] 장바구니 조회
    - `요청`
      ```java
      GET /customers/carts HTTP/1.1
      Authorization: "Bearer 유저 토큰"
      ```
    - `응답`
      ```java
      HTTP/1.1 200 OK
      Content-Type: application/json
      [
          {
              "productId" : '상품 ID',
              "thumbnail": '상품 이미지 url',
              "name": '상품 이름',
              "price": 상품 가격,
              "quanttiy": 장바구니 담긴 수량
          }
      ]
      ```
- [x] 장바구니 수량 수정
    - `요청`
      ```java
      PATCH /customers/carts HTTP/1.1
      Content-Type: application/json
      Authorization: "Bearer 유저 토큰"
      {
          "productId": "상품 ID",
          "quantity": 상품 수량
      }
      ```
    - `응답`
      ```
      HTTP/1.1 200 OK
      Content-Type: application/json
      ```
