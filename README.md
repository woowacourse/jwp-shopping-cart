# jwp-shopping-cart

- 10분씩 돌아가면서 진행한다.
    - 하던 코드까지는 마무리 한다.

## 요구 사항 목록

- **step1**
- [x] 상품 목록 페이지 연동
- [x] 상품 관리 CRUD API 작성
    - [x] 상품 생성 API 작성
    - [x] 상품 목록 조회 API 작성
    - [x] 상품 수정 API 작성
    - [x] 상품 삭제 API 작성
- [x] 관리자 도구 페이지 연동
- **step2**
- [x] 사용자 기능 구현
  - [x] 사용자 생성 API 작성
  - [x] 사용자 조회 API 작성
  - [x] 사용자 수정 API 작성
  - [x] 사용자 삭제 API 작성
- [x] 사용자 설정 페이지 연동
- [x] 장바구니 기능 구현
  - [x] 장바구니 생성 API 작성
  - [x] 장바구니 전체 조회 API 작성
  - [x] 장바구니 삭제 API 작성
- [x] 장바구니 페이지 연동

## 도메인 요구사항

- 상품은 ID값을 가진다
- 상품은 이름을 가진다.가
- 상품은 이미지를 가진다.
- 상품은 가격을 가진다.

- 사용자는 이메일,이름,휴대폰번호,패스워드를 가진다.
- 장바구니는 로그인된 사용자만 조회,추가,삭제 가능하다

---

# 아이템

### 추가

**Request**

`POST /admin/item`

```
curl -X POST -H "Content-Type: application/json" -d '{"name":"power", "image-url":"https://image.aladin.co.kr/product/30309/85/cover500/f252839034_1.jpg", "price":10000}' http://localhost:8080/admin/item
```

---

### 수정

**Request**

`PUT /admin/item`

```
curl -X PUT -H "Content-Type: application/json" -d '{"id":1,"name":"power", "image-url":"https://image.aladin.co.kr/product/30309/85/cover500/f252839034_1.jpg", "price":10000}' http://localhost:8080/admin/item
```

---

### 삭제

**Request**

`PUT /admin/item/{itemId}`

```
curl -X DELETE -H "Content-Type: application/json" http://localhost:8080/admin/item/1
```

---

# 유저 

### 추가

**Request**

`POST /member`

```
curl -X POST -H "Content-Type: application/json" -d '{"email":"admin@naver.com","name":"power", "phone-number":"01011112222", "password":"qwer1234!"}' http://localhost:8080/member
```

---

### 수정

**Request**

`PUT /member`

```
curl -X PUT -H "Content-Type: application/json" -d '{"email":"admin@naver.com","name":"power", "phone-number":"01011112222", "password":"qwer1234!"}' http://localhost:8080/member
```

---

### 삭제

**Request**

`PUT /member/{userEmail}`

```
curl -X DELETE -H "Content-Type: application/json" http://localhost:8080/member/admin@naver.com
```

---

# 장바구니

### 전체 조회

**Request**

`GET /carts`

```
curl -X GET -H "Content-Type: application/json" -H "Authorization: Basic YWRtaW5AbmF2ZXIuY29tOnF3ZXIxMjM0" http://localhost:8080/carts
```

### 추가

**Request**

`POST /cart`

```
curl -X POST -H "Content-Type: application/json" -H "Authorization: Basic YWRtaW5AbmF2ZXIuY29tOnF3ZXIxMjM0" -d '{"productId":2}' http://localhost:8080/cart
```

---

### 삭제

**Request**

`PUT /cart/{itemId}`

```
curl -X DELETE -H "Content-Type: application/json" -H "Authorization: Basic YWRtaW5AbmF2ZXIuY29tOnF3ZXIxMjM0" http://localhost:8080/cart/1
```
