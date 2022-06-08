# 장바구니
장바구니 미션 저장소

## ✏️ Code Review Process
[텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 기능 요구 사항
JWT 라이브러리를 활용하여 액세스 토큰 발급 기능을 구현하기
발급한 토큰을 이용하여 로그인이 필요한 기능(회원 정보 조회/수정, 회원탈퇴) 요청 시 포함하여 보내고 이를 이용하여 기능이 동작하도록 구현하기

## API 명세
### 회원가입

- 요청

```json
POST /users

{
	email: "email",
	password: "password",
	nickname: "nickname"
}
```

- 응답
    - 정상 케이스

        ```json
        200 OK
        ```

    - 회원정보 양식이 잘못됐을 때

        ```json
        400 BAD REQUEST
        
        {
        	errorCode: "1000",
        	message: "error message"
        }
        ```

    - 이메일이 중복일 때

        ```json
        400 BAD REQUEST 
        
        {
        	errorCode: "1001",
        	message: "error message"
        }
        ```


### 로그인

- 요청

```json
POST /login

{
	email: "email",
	password: "password"
}
```

- 응답
    - 정상 케이스

        ```json
        200 OK
        
        {
        	accessToken: "accessToken"
        }
        ```

    - 회원정보 양식이 잘못됐을 때

        ```json
        400 BAD REQUEST
        
        {
        	errorCode: "1000",
        	message: "어쩌고 저쩌고"
        }
        ```

    - 존재하지 않는 아이디 비밀번호 사용

        ```json
        400 BAD REQUEST
        
        {
        	errorCode: "1002",
        	message: "어쩌고 저쩌고"
        }
        ```


### 회원 정보 수정

- 요청

```json
PUT /users
Authorization : accessToken

{
	nickname: "nickname",
	password: "password"
}
```

- 응답
    - 정상 케이스

    ```java
    200 OK
    ```

    - 회원정보 양식 문제

    ```java
    400 BAD REQUEST
    
    {
    	errorCode: "1000",
    	message: "어쩌고 저쩌고"
    }
    ```

    - 잘못된 토큰일 때

    ```java
    401 UNAUTHORIZED
    ```


### 회원 정보 요청

- 요청

```java
GET /users/me
Authorization : accessToken
```

- 응답
    - 정상 케이스

    ```java
    200 OK
    
    {
    	email: "email",
    	nickname: "nickname"
    }
    ```

    - 잘못된 토큰일 때

    ```java
    401 UNAUTHORIZED
    ```


### 회원 정보 삭제

- 요청

```json
DELETE /users
Authorization : accessToken
```

- 응답
    - 정상 케이스

    ```json
    200 OK
    ```

    - 잘못된 토큰의 경우

    ```json
    401 UNAUTHORIZED
    ```

## 2단계 장바구니 API 명세서
## 장바구니

### 장바구니 상품 목록 조회

- 요청

```json
GET /users/me/carts

Authorization: accessToken
```

- 응답
  - 정상 케이스

    ```json
    200 OK
    
    {
       "cartList": [{
          "id": "1L",
          "name": "치약",
          "price": "1200",
          "imageUrl": "image url",
          "quantity": "3"
       }]
    }
    ```

  - 인가가 잘못 됐을 때

    ```json
    401 UNAUTHORIZED
    ```


### 장바구니 상품 추가

- 요청

```json
POST /users/me/carts
Authorization: accessToken

{
   "productId": "1L"
}
```

- 응답
  - 정상 케이스

    ```json
    204 NO CONTENT
    ```

  - 인가가 잘못 됐을 때

    ```json
    401 UNAUTHORIZED
    ```

  - 중복된 상품을 장바구니에 담을 경우

    ```json
    400 BAD REQUEST
    
    {
    	"errorCode" : "1101",
    	"message" : "중복된 물품입니다."
    }
    ```

  - 존재하지 않는 상품을 장바구니에 담을 경우

    ```json
    404 NOT FOUND
    ```


### 장바구니 상품 수정

- 요청

```json
PUT /users/me/carts/{productId}
Authorization: accessToken

{
    "quantity": "3"
}
```

- 응답
  - 정상 케이스

    ```json
    200 OK
    
    {
    	"id": "1L",
      "name": "치약",
      "price": "1200",
      "imageUrl": "image url",
    	"quantity": "3"
    }
    ```

  - 인가가 잘못됐을 때

    ```json
    401 UNAUTHORIZATION
    ```

  - 잘못된 수량의 양식인 경우 (수량은 양수만 허용)

    ```json
    400 BAD REQUEST
    
    {
    	"errorCode" : "1100",
    	"message" : "잘못된 형식입니다."
    ```

  - 장바구니에 없는 상품을 수정할 경우

    ```json
    400 BAD REQUEST
    
    {
    	"errorCode" : "1102",
    	"message" : "장바구니에 상품이 존재하지 않습니다."
    }
    ```

  - 없는 상품인 경우

    ```jsx
    404 NOT FOUND
    ```


### 장바구니 상품 삭제

- 요청

```java
DELETE /users/me/carts/{productId}
Authorization: accessToken
```

- 응답
  - 정상 케이스

    ```json
    204 NO CONTENT
    ```

  - 인가가 잘못됐을 때

    ```json
    401 UNAUTHORIZATION
    ```

  - 장바구니에 없는 상품을 삭제할 경우

    ```json
    400 BAD REQUEST
    
    {
    	"errorCode" : "1102",
    	"message" : "장바구니에 상품이 존재하지 않습니다."
    }
    ```

  - 없는 상품인 경우

## 상품

### 상품 목록 조회

- 요청

```json
GET /products
```

- 응답

```json
200 OK

{
   productList: [{
      "id": "1L",
      "name": "치약",
      "price": "1200",
      "imageUrl": "image url"
   }]
}
```

### 상품 상세 조회

- 요청

```json
GET /products/{productId}
```

- 응답
  - 정상 케이스

    ```json
    200 OK
    
    {
       "id": "1L",
       "name": "치약",
       "price": "1200",
       "imageUrl": "image url"
    }
    ```

  - 상품이 없는 경우

    - 상품이 없는 경우
    ```json
      404 NOT FOUND
    ```

### 에러 코드

> 1000번대 - BAD_REQUEST(400)
>

> 1000번대 - 유저 관련
>
- 1000 : 회원정보 양식이 잘못됐을 때
- 1001 : 이메일이 중복일 때
- 1002 : 존재하지 않는 아이디 또는 잘못된 비밀번호로 로그인 시도

> 1100번대 - 장바구니 관련
>
- 1100 : 장바구니 수량 수정 양식이 잘못됐을 때
- 1101 : 장바구니 중복 등록
- 1102 : 장바구니에 상품이 없는 경우
