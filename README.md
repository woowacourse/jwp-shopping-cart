# jwp-shopping-cart

---

## 기능 구현 목록

- [ ] 상품 목록 페이지 연동
    - [x] `/` URL로 접근할 경우 상품 목록 페이지를 조회할 수 있어야 한다.
    - [ ] Controller 이용해서 페이지 연동하기
- [ ] 상품 관리 CRUD API 작성
    - [ ] 상품 기본 정보 DTO 만들기
    - [ ] 상품 생성 API
    - [ ] 상품 목록 조회 API
    - [ ] 상품 수정 API
    - [ ] 상품 삭제 API
- [ ] 관리자 도구 페이지 연동

## 도메인

- [x] 상품 도메인 모델 정의하기
    - [x] 상품의 가격은 음수가 될 수 없다.
    - [x] 이름은 공백이 될 수 없다.

## 상품 기본 정보

- 상품 ID
- 상품 이름
- 상품 이미지
- 상품 가격

---

# API 스펙

# 상품 조회 GET`/products`

## 요청

## 응답
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

# 상품 추가  POST`/products`

## 요청

```json
{
  "name": "이름",
  "price": 10000,
  "imgUrl": "dfowf"
}
```

## 응답

201

# 상품 수정 PUT `/products`

## 요청

```json
{
  "id": 1,
  "name": "이름",
  "price": 10000,
  "imgUrl": "dfowf"
}
```

## 응답

200

# 상품 삭제 DELETE `/products`

## 요청

```json
{
  "id": 1
}
```

## 응답

200
