# jwp-shopping-cart

## 페어

| <img src="https://avatars.githubusercontent.com/u/51906604?v=4" alt="" width=200> | <img src="https://avatars.githubusercontent.com/u/64852591?v=4" alt="" width=200/> |
|:---------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------:|
|                         [저문](https://github.com/jeomxon)                          |                          [라온](https://github.com/mcodnjs)                          | |

# 상품 관리 API 명세

## 상품 생성

1. 요청

- URL : localhost:8080/products
- method : POST
- Body :

```json
{
  "name": "치킨",
  "price": 10000,
  "imageUrl": "https://grepp-programmers.s3.ap-northeast-2.amazonaws.com/files/production/d440b8f4-91c3-4272-8a81-876e9aaffb9c/RisingStarGraphBox.jpg"
}
```

## 상품 목록 조회

1. 요청

- URL : localhost:8080/admin
- method : GET

## 상품 수정

1. 요청

- URL : localhost:8080/products/:productId
- method : PUT

```json
{
  "name": "치킨",
  "price": 10000,
  "imageUrl": "https://grepp-programmers.s3.ap-northeast-2.amazonaws.com/files/production/d440b8f4-91c3-4272-8a81-876e9aaffb9c/RisingStarGraphBox.jpg"
}
```

## 상품 삭제

1. 요청

- URL : localhost:8080/products/:productId
- method : DELETE