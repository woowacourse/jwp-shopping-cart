# jwp-shopping-cart

## 페어

| <img src="https://avatars.githubusercontent.com/u/51906604?v=4" alt="" width=200> | <img src="https://avatars.githubusercontent.com/u/64852591?v=4" alt="" width=200/> |
|:---------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------:|
|                         [저문](https://github.com/jeomxon)                          |                          [라온](https://github.com/mcodnjs)                          | |

## 상품 관리 기능 (product)

- [x] 상품 목록 페이지 연동
    - index: `GET /`
- [x] 상품 관리 CRUD API 작성
    - 상품 조회: `GET /products`
    - 상품 추가: `POST /products`
    - 상품 수정: `PUT /products/:productId`
    - 상품 삭제: `DELETE /products/:productId`
- [x] 관리자 도구 페이지 연동
    - admin: `GET /admin`

## 사용자 기능 (member)

- [x] 사용자 기능 구현
    - email, password
    - Basic Auth
- [x] 사용자 설정 페이지 연동
    - settings: `GET /settings`

## 장바구니 기능 (cart)

- [x] 장바구니 기능 구현
    - 장바구니 상품 조회: `GET /cart/products`
    - 장바구니 상품 추가: `POST /cart/products`
    - 장바구니 상품 삭제: `DELETE /cart/products/:productId`
- [x] 장바구니 페이지 연동
    - cart: `GET /cart`