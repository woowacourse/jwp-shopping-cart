# jwp-shopping-cart

# 상품 관리 API 명세

## 상품 생성

1. 요청

- URL : localhost:8080/admin/products
- method : POST
- Body :

```json
{
  "name": "치킨",
  "price": 10000,
  "image_url": "https://grepp-programmers.s3.ap-northeast-2.amazonaws.com/files/production/d440b8f4-91c3-4272-8a81-876e9aaffb9c/RisingStarGraphBox.jpg"
}
```

## 상품 목록 조회

1. 요청

- URL : localhost:8080/admin
- method : GET

## 상품 수정

1. 요청

- URL : localhost:8080/admin/products/:productId
- method : PUT

```json
{
  "name": "치킨",
  "price": 10000,
  "image_url": "https://grepp-programmers.s3.ap-northeast-2.amazonaws.com/files/production/d440b8f4-91c3-4272-8a81-876e9aaffb9c/RisingStarGraphBox.jpg"
}
```

## 상품 삭제

1. 요청

- URL : localhost:8080/admin/products/:productId
- method : DELETE