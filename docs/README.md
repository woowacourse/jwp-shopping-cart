## 회원 가입

### Request

```
POST /customers
Content-Type: application/json
```

```json
{
  "email": "123@gmail.com",
  "password": "1234",
  "nickname": "Movie"
}
```

### Response (정상)

```
HTTP/1.1 201 Created
Content-Type: application/json
Content-length:
Location: /customers/100
```

```json
{
  "email": "123@gmail.com",
  "nickname": "Movie"
}
```

### Response (예외, 중복 이메일?)

- 유효성 검사(**서버**에서 처리)
    - **중복된 이메일**
    - 이메일 형식이 지켜지지 않았을 경우
        - @ 유무
        - 공백 유무
        - @ 이후 . 유무
    - 비밀번호 형식
        - 최소 10자리 이상 : 영문자, 숫자, 특수문자 중 3종류 조합
    - 닉네임은 최소 2자리 이상 최대 10자리 이하

```
400 Bad Request
```

---

## 회원 탈퇴

### Request

```
DELETE /customers
Authorization : ????
```

```json
{
  "password": "1234"
}
```

### Response

```
204 no-content
```

### Response (예외)

```
401 Unauthorized
```

---

## 회원 정보 수정 (닉네임)

### Request

```
PUT /customers/profile
Content-Type: application/json
Authorization : ????
```

```json
{
  "nickname": "Marco"
}
```

### Response(정상)

```
200 OK`
```

```json
{
  "nickname": "Marco"
}
```

## 회원 정보 수정 (비밀번호)

### Request

```
PUT /customers/password
Content-Type: application/json
Authorization : ????
```

```json
{
  "password": "password123!@#",
  "newPassword": "newPassword123!@#"
}
```

### Response(정상)

```
204 no-content
```


### Response (예외)

- 유효성 검사(클라이언트)
    - 비밀번호 형식
        - 최소 10자리 이상 : 영문자, 숫자, 특수문자 중 3종류 조합
    - 닉네임은 최소 2자리 이상 최대 10자리 이하
- 유효성 검사(서버)
    - 유효하지 않은 토큰이 전달됐을 때 (→ 401)
    - 이전 비밀번호가 현재 비밀번호와 일치하지 않을 경우 (→ 401)
    - 비밀번호 형식 (→400)
        - 최소 10자리 이상 : 영문자, 숫자, 특수문자 중 3종류 조합
    - 닉네임은 최소 2자리 이상 최대 10자리 이하 (→400)

```
401 Unauthorized
```

---

## 로그인

### Request

```
POST /auth/login
Content-Type: application/json
```

```json
{
  "email": "123@gmail.com",
  "password": "1234"
}
```

### Response (정상)

```
200 Ok
Content-Type: application/json
```

```json
{
  "nickname": "Movie",
  "accessToken": "?????"
}
```

### Response (예외)

- 유효성 검사(서버)
    - 존재하지 않는 이메일의 경우
    - 해당 이메일에 비밀번호가 일치하지 않는 경우

```
401 Unauthorized
```

---

## 로그아웃

### Request

- 논의 중
- 토큰을 서버에서 만료시킬 방법이 없음
- 리프레시 토큰? 블랙리스트?

```
POST /auth/logout
Authorization : ????
```

### Response

```
204 no-content
```

## 회원 조회

```
GET /customers
Authorization : ????
```

### Response

```
200 Ok
Content-Type: application/json
```

```json
{
  "nickname": "Movie",
  "email": "adsf@adsf.com"
}
```

## 상품 목록 가져오기

### Request

```
GET/products
```

### Response

```
200 ok
Content-Type: application/json
```

```json
[
  {
    "id": 1,
    "name": "우유",
    "price": 3000,
    "image": "https://img1.com/img.jpg"
  },
  {
    "id": 2,
    "name": "식빵",
    "price": 4000,
    "image": "https://img2.com/img.jpg"
  }
]
```

## 특정 상품 가져오기

### Request

```
GET /products/{id}
```

### Response

```
200 ok
Content-Type: application/json
```

```json
{
  "id": 1,
  "name": "우유",
  "price": 3000,
  "image": "https://img1.com/img.jpg"
}
```

## 장바구니 목록 가져오기

### Request

```
GET /cartItem
Authorization: `Bearer ${accessToken}`
```

### Response

```json
[
  {
    "id": 1,
    "name": "우유",
    "price": 3000,
    "image": "https://img1.com/img.jpg",
    "quantity": 2
  },
  {
    "id": 2,
    "name": "식빵",
    "price": 4000,
    "image": "https://img2.com/img.jpg",
    "quantity": 3
  }
]
```

## 장바구니에 상품 추가 및 수량 수정

### Request

```
PUT /cartItem/products/{id}
Authorization: `Bearer ${accessToken}`
```

```json
{
  "quantity": 2
}
```

### Response

장바구니에 해당 상품이 없던 경우 새로 생성

```
201 Created
Content-Type: application/json
Location: /cartItem/1
```

```json
 {
  "productId": 1,
  "name": "우유",
  "price": 3000,
  "image": "https://img1.com/img.jpg",
  "quantity": 2
}
```

이미 장바구니에 해당 상품이 있으면 수정

```
200 Ok
Content-Type: application/json
```

```json
 {
  "productId": 1,
  "name": "우유",
  "price": 3000,
  "image": "https://img1.com/img.jpg",
  "quantity": 2
}
```

## 장바구니 내 상품 삭제

### Request

```
DELETE /cartItem
Authorization: `Bearer ${accessToken}`
```

```json
{
  "productIds": [
    3,
    5
  ]
}
```

### Response

```
204 no-content
```

## 주문하기

### Request

```
POST /orders
Authorization: `Bearer ${accessToken}
```

### Response

```
201 Created
Content-Type: application/json
Content-length: 
Location: /orders/5
```

```json
{
  "id": 5,
  "orderDetails": [
    {
      "productId": 4,
      "name": "우유",
      "quantity": 3,
      "price": 1000,
      "imgUrl": "http://img."
    },
    {
      "productId": 4,
      "name": "우유",
      "quantity": 3,
      "price": 1000,
      "imgUrl": "http://img."
    }
  ],
  "totalPrice": 53200,
  "orderDate": "2022-03-20 12:23:33"
}
```

## 주문 단건 조회

### Request

```
GET /orders/5
Authorization: `Bearer ${accessToken}
```

### Response

```
200 Ok
Content-Type: application/json
```

```json
{
  "id": 5,
  "orderDetails": [
    {
      "productId": 4,
      "name": "우유",
      "quantity": 3,
      "price": 1000,
      "imgUrl": "http://img."
    },
    {
      "productId": 4,
      "name": "우유",
      "quantity": 3,
      "price": 1000,
      "imgUrl": "http://img."
    }
  ],
  "totalPrice": 53200,
  "orderDate": "2022-03-20 12:23:33"
}
```

## 공통 에러

```
// 수량 형식이 잘못됨(양수여야 한다)
400 Bad Request

// 토큰 유효하지 않음
401 Unauthorized

// 상품id 존재하지 않음
404 Not Found
```
