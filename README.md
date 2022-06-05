## 1단계 기능 목록
- 테이블과 데이터 추가
    - customer username, password, phoneNumber, address
- customer
    - username
        - 영어와 숫자가 아니면 예외 발생
        - 3자 이상 15자 이하가 아니면 예외 발생
    - password
        - 영어와 숫자가 둘 다 존재하지 않으면 예외 발생
        - 8자 이상 20자 이하가 아니면 예외 발생
    - phoneNumber
        - 열한자의 숫자가 아니면 예외 발생
    - address

## End Point

### 회원 가입

- `POST /api/customers/signup`
- not required authorization
- http request

```json
{
  "username" : "myUsername123",
  "password" : "myPassword1234",
  "phoneNumber" : "01012345678",
  "address" : "성담빌딩"
}
```

- http response

```json
201 created
empty body
```

### 로그인

- `POST /api/customers/login`
- required authorization
- http request

```json
{
  "username" : "myUsername123",
  "password" : "myPassword1234"
}
```

- http response

```json
200 ok
{
  "accessToken" : "jwt.token.here"
}
```

### 회원 정보 조회

- `GET /api/customers`
- required authorization
- http response

```json
200 ok
{
  "customer" : {
    "username" : "myUsername123",
    "phoneNumber" : "01012345678",
    "address" : "성담빌딩"
  }
}
```

### 회원 정보 수정

- `PUT /api/customers`
- required authorization
- http request

```json
{
  "phoneNumber" : "01087654321",
  "address" : "루터회관"
}
```

- http response

```json
204 no-content
empty body
```

### 패스워드 변경

- `PATCH /api/customers/password`
- required authorization
- http request

```json
{
  "password" : "changePassword123"
}
```

- http response

```json
204 no-content
empty body
```

### 회원 탈퇴

- `DELETE /api/customers`
- requried authorization
- http response

```json
204 no-content
empty body
```
## Cart

### 카트 전체 아이템조회

- `GET /api/cartItems`
- required authorization
- http response

```json
200 ok
{
  "cartItems" : [
    {
      "id" : 1,
      "productId" : 5,
      "name" : "음식1",
      "price" : 1000,
      "quantitiy" : 2,
      "imageURL" : "http:..."
    },
    {
      "id" : 2,
      "productId" : 7,
      "name" : "음식2",
      "price" : 1000,
      "quantitiy" : 3,
      "imageURL" : "http:..."
    }
  ]
}
```

### 카트에 아이템 추가

- `POST /api/cartItems`
- required authorization
- http request

```json
{
  "productId" : 1,
  "quantitiy" : 2
}
```

- http response

```json
201 created
empty body
```

### 카트에 아이템 수량 수정

- `PATCH /api/cartItems/{cartItemId}?quantity=2`
- required authorization
- http response

```json
200 ok
empty body
```

### 카트에 아이템 삭제

- `DELETE /api/cartItems/{cartItemId}`
- required authoziation
- http response

```json
204 noContent
empty body
```

## Order

### 주문 추가

- `POST /api/orders`
- required authoziation
- http request

```json
[
  {
    "cartItemId" : 1
  },
  {
    "cartItemId" : 2
  }
]

```

- http response

```json
201 created
Location : /api/orders/{orderId}
```

### 주문 전체 확인

- `GET /api/orders`
- required authoziation
- http response

```json
200 ok
{
  "orders" : [
    { 
      "id" : 1,
      "orderDetails" : [
        {
          "id" : 1,
          "productId" : 6,
          "name" : "음식1",
          "price" : 1000,
          "quantitiy" : 2,
          "imageURL" : "http:..."
        },
        {
          "id" : 2,
          "productId" : 7,
          "name" : "음식2",
          "price" : 1000,
          "quantitiy" : 3,
          "imageURL" : "http:..."
        }
      ]
    },
    {
      "id" : 2,
      "orderDetails" : [
        {
          "id" : 1,
          "productId" : 6,
          "name" : "음식1",
          "price" : 1000,
          "quantitiy" : 2,
          "imageURL" : "http:..."
        },
        {
          "id" : 2,
          "productId" : 7,
          "name" : "음식2",
          "price" : 1000,
          "quantitiy" : 3,
          "imageURL" : "http:..."
        }
      ]
    }
  ]
}
```

### 주문 상세 확인

- `GET /api/orders/{orderId}`
- required authoziation
- http response

```json
200 ok
{
  "order" : { 
    "id" : 1,
    "orderDetails" : [
      {
        "id" : 1,
        "productId" : 6,
        "name" : "음식1",
        "price" : 1000,
        "quantitiy" : 2,
        "imageURL" : "http:..."
      },
      {
        "id" : 2,
        "productId" : 7,
        "name" : "음식2",
        "price" : 1000,
        "quantitiy" : 3,
        "imageURL" : "http:..."
      }
    ]
  }
}
```

## Product - 단순

### 상품 전체 조회

- `GET /api/products`
- http response

```json
200 ok
{
  "products" : [
    {
      "id" : 1,
      "name" : "과자",
      "price" : 1000,
      "stock" : 20,
      "imageURL" : "http...."
    },
    {
      "id" : 2,
      "name" : "소주",
      "price" : 2000,
      "stock" : 15,
      "imageURL" : "http...."
    }
  ]
}
```

### 상품 단일 조회

- `GET /api/products/{productId}`
- http response

```json
200 ok
{
  "product" : {
    "id" : 1,
    "name" : "과자",
    "price" : 1000,
    "stock" : 20,
    "imageURL" : "http...."
  }
}
```

### 상품 추가

- `POST /api/products/{productId}`
- http request

```json
{
  "name" : "과자",
  "price" : 1000,
  "stock" : 20,
  "imageURL" : "http..."
}
```

- http response

```json
201 created
Location : "/api/products/{productId}"
```

### 상품 삭제

- `DELETE /api/products/{productId}`
- http response

```json
204 non content
empty body
```

## Error Response

```json
{
  "error" : {
    "messages" : [
      "error1", "error2"
    ]
}
```

1. 아이디 패스워드 불일치 : 401 unauthorized
2. 회원가입 시 유저 패턴 : 400 bad request
    1. 영어, 숫자만 가능
    2. 3자 이상 15자 이하
3. 회원가입 시 패스워드 패턴 : 400 bad request
    1. 영어, 숫자 항상 포함
    2. 8자 이상 20자 이하
4. 만료 토큰, 잘못된 코튼 : 401 unauthorized
