# 🧺 장바구니 1단계 - 회원 기능

## 이메일 중복 체크

### HTTP Request

```jsx
GET /api/members/email-check?email=email@email.com HTTP/1.1
```

### HTTP Response - 이메일 중복 체크 성공 시

```jsx
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json

{
  unique: "true"
}
```

### HTTP Response - 이메일 중복 체크 실패 시

```jsx
HTTP/1.1 400 Bad Request

{
  message: ~~
}
```

- 이메일 형식이 잘못되었을 경우

## 회원 가입

### HTTP Request

```jsx
POST /api/members HTTP/1.1
Content-Type: application/json

{
  "email" : "email@email.com",
  "nickname" : "닉네임",
  "password" : "password123!"
}
```

### HTTP Response - 회원 가입 성공 시

```jsx
HTTP/1.1 201 Created
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
```

### HTTP Response - 회원 가입 실패 시

```jsx
HTTP/1.1 400 Bad Request
```

- 누락된 항목이 존재할 경우
- 중복되는 이메일이 존재할 경우
- 이메일 형식이 잘못되었을 경우

    ```java
    "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
    ```

- 닉네임 형식이 잘못되었을 경우

    ```java
    한글로만 최소 1자 최대 5자
    "^[ㄱ-ㅎ가-힣]{1,5}$"
    ```

- 비밀번호 형식이 잘못되었을 경우

    ```java
    최소 8 자, 최대 20 자 최소 하나의 문자, 하나의 숫자 및 하나의 특수 문자
    "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$"
    ```
  
## 로그인

### HTTP Request

```jsx
POST /api/login HTTP/1.1
Content-Type: application/json

{
  "email" : "email@email.com",
  "password" : "password123!"
}
```

### HTTP Response - 성공 시

```jsx
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json

{
	"nickname" : "닉네임",
  "token" : "accessToken"
}
```

### HTTP Response - 실패 시

```jsx
HTTP/1.1 400 Bad Request
```

- 잘못된 이메일 혹은 비밀번호를 입력 하였을 경우
- 이메일 형식이 잘못되었을 경우

    ```java
    "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
    ```

- 닉네임 형식이 잘못되었을 경우

    ```java
    한글로만 최소 1자 최대 5자
    "^[ㄱ-ㅎ가-힣]{1,5}$"
    ```

- 비밀번호 형식이 잘못되었을 경우

    ```java
    최소 8 자, 최대 20 자 최소 하나의 문자, 하나의 숫자 및 하나의 특수 문자
    "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$"
    ```
  
## 비밀번호 확인

### HTTP Request

```jsx
POST /api/members/password-check HTTP/1.1
Content-Type: application/json
Authorization: Bearer accessToken

{
  "password" : "password123!"
}
```

### HTTP Response - 성공 시

```jsx
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json

{
  "success": "true"
}
```

## 회원 정보 조회

### HTTP Request

```jsx
GET /api/members/me HTTP/1.1
Authorization: Bearer accessToken
```

### HTTP Response - 성공 시

```jsx
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json

{
  "email" : "email@email.com",
  "nickname" : "닉네임"
}
```

## 회원 정보 수정

### HTTP Request

```jsx
PATCH /api/members/me HTTP/1.1
Content-Type: application/json
Authorization: Bearer accessToken

{
  "nickname" : "다른닉네임"
}
```

### HTTP Response - 성공 시

```jsx
HTTP/1.1 204 No Content
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
```

## 비밀번호 수정

### HTTP Request

```jsx
PATCH /api/members/password HTTP/1.1
Content-Type: application/json
Authorization: Bearer accessToken

{
  "password" : "otherpassword123!"
}
```

### HTTP Response - 성공 시

```jsx
HTTP/1.1 204 No Content
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
```

## 회원 탈퇴

### HTTP Request

```jsx
DELETE /api/members/me HTTP/1.1
Content-Type: application/json
Authorization: Bearer accessToken
```

### HTTP Response - 성공 시

```jsx
HTTP/1.1 204 No Content
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
```

## 기타

### 인증 에러

```jsx
HTTP/1.1 401 Unauthorized
```

- 로그인이 안되어 있을 경우
- 토큰 시간이 만료 되었을 경우
- 삭제된 회원의 토큰으로 접근을 시도할 경우

### 기타 에러

```jsx
HTTP/1.1 500 Internal Server Error
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers

{
  "message" : "...",
}
```

# 🧺 장바구니 2단계 - 장바구니/주문 API 변경하기

## 상품 리스트 조회

### HTTP Request

```jsx
GET /api/products?page={page}&limit={limit} HTTP/1.1
```

- page = 현재 페이지 번호
- limit = 한 페이지에 표시할 상품의 개수

### HTTP Response - 성공 시

```jsx
HTTP/1.1 200 Ok
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
x-total-count: {상품 개수}

[	{
	"id" : 1,
	"name" : "그릴",
	"price" : 100,
	"imageUrl" : "https~~",
	"stock" : 1,
}, {
	"id" : 2,
	"name" : "손전등",
	"price" : 200,
	"imageUrl" : "https~~",
	"stock" : 5,
} ]
```

## 장바구니 상품 추가

### HTTP Request

```jsx
POST /api/carts/products HTTP/1.1
Content-Type: application/json
Authorization: Bearer accessToken

{
  "id" : 1,
  "quantity" : 1
}
```

### HTTP Response - 장바구니 상품 추가 성공 시

```jsx
HTTP/1.1 200 Ok
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json

[	{
	"product" : {
		"id" : 1,
		"name" : "그릴",
		"price" : 100,
		"imageUrl" : "https~~",
		"stock" : 1,
	},
	"quantity" : 1
}, {
	"product" : {
		"id" : 2,
		"name" : "손전등",
		"price" : 200,
		"imageUrl" : "https~~",
		"stock" : 5,
	},
	"quantity" : 1
} ]
```

### HTTP Response - 장바구니 상품 추가 실패 시

```jsx
HTTP/1.1 400 Bad Request
```

- 상품이 품절 되었을 경우
- 상품이 존재하지 않을 경우(상품 id가 존재하지 않는 경우)

## 장바구니 조회

### HTTP Request

```jsx
GET /api/carts HTTP/1.1
Authorization: Bearer accessToken
```

### HTTP Response - 성공 시

```jsx
HTTP/1.1 200 Ok
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json

[	{
	"product" : {
		"id" : 1,
		"name" : "그릴",
		"price" : 100,
		"imageUrl" : "https~~",
		"stock" : 1,
	},
	"quantity" : 1
}, {
	"product" : {
		"id" : 2,
		"name" : "손전등",
		"price" : 200,
		"imageUrl" : "https~~",
		"stock" : 5,
	},
	"quantity" : 1
} ]
```

## 장바구니 수량 변경

### HTTP Request

```jsx
PATCH /api/carts/products HTTP/1.1
Content-Type: application/json
Authorization: Bearer accessToken

{
  "id" : 1,
	"quantity" : 1,
}
```

### HTTP Response - 성공 시

```jsx
HTTP/1.1 200 Ok
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json

[	{
	"product" : {
		"id" : 1,
		"name" : "그릴",
		"price" : 100,
		"imageUrl" : "https~~",
		"stock" : 1,
	},
	"quantity" : 1
}, {
	"product" : {
		"id" : 2,
		"name" : "손전등",
		"price" : 200,
		"imageUrl" : "https~~",
		"stock" : 5,
	},
	"quantity" : 1
} ]
```

## 장바구니 상품 제거

### HTTP Request

```jsx
DELETE /api/carts/products?id=1 HTTP/1.1
Content-Type: application/json
Authorization: Bearer accessToken
```

### HTTP Response - 성공 시

```jsx
HTTP/1.1 200 Ok
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json

[	{
	"product" : {
		"id" : 1,
		"name" : "그릴",
		"price" : 100,
		"imageUrl" : "https~~",
		"stock" : 1,
	},
	"quantity" : 1
}, {
	"product" : {
		"id" : 2,
		"name" : "손전등",
		"price" : 200,
		"imageUrl" : "https~~",
		"stock" : 5,
	},
	"quantity" : 1
} ]
```
