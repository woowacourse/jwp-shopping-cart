# jwp-shopping-cart

---

## 기능 구현 목록

- [x] 상품 목록 페이지 연동
    - [x] `/` URL로 접근할 경우 상품 목록 페이지를 조회할 수 있어야 한다.
    - [x] Controller 이용해서 페이지 연동하기
- [x] 상품 관리 CRUD API 작성
    - [x] 상품 기본 정보 DTO 만들기
    - [x] 상품 생성 API
    - [x] 상품 목록 조회 API
    - [x] 상품 수정 API
    - [x] 상품 삭제 API
- [x] 관리자 도구 페이지 연동
- [x] 프론트 뷰와 연결

- [x] 사용자 기능 구현
- [x] 사용자 설정 페이지 연동
- [x] 장바구니 기능 구현
- [x] 장바구니 페이지 연동

## 도메인

- [x] 상품 도메인 모델 정의하기
    - [x] 상품의 가격은 음수가 될 수 없다.
    - [x] 이름은 공백이 될 수 없다.

- [x] 사용자 도메인 모델 정의하기
    - [x] 사용자의 이메일은 공백이 될 수 없다.
    - [x] 사용자의 이메일은 이메일 형식을 가져야한다. (example@example.com)
    - [x] 사용자의 패스워드는 최소 8자이며, 특수문자와 영문 그리고 숫자를 모두 포함하여야 한다.

## 상품 기본 정보

- 상품 ID
- 상품 이름
- 상품 이미지
- 상품 가격

---

# API 스펙

## 상품 조회 GET`/products`

### 요청

### 응답

200

```json
{
  "products": [
    {
      "id": 1,
      "name": "피자",
      "imgUrl": "img",
      "price": 10000
    },
    {
      "id": 2,
      "name": "치킨",
      "imgUrl": "img",
      "price": 10000
    }
  ]
}
```

---

## 상품 추가  POST`/products`

### 요청

```json
{
  "name": "이름",
  "price": 10000,
  "imgUrl": "dfowf"
}
```

### 응답

201

---

## 상품 수정 PUT `/products/{id}`

### 요청

```json
{
  "name": "이름",
  "price": 10000,
  "imgUrl": "dfowf"
}
```

### 응답

200

---

## 상품 삭제 DELETE `/products/{id}`

### 요청

### 응답

200

---

## 카트 추가 POST `/carts/{productId}`

### 요청

param, Basic header

### 응답

201

---

## 카트 삭제 DELETE `/carts/{productId}`

### 요청

param, Basic header

### 응답

200

---
