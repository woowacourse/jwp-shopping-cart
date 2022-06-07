# 장바구니
장바구니 미션 저장소


## 🧺 기능 요구사항
- [x] 회원가입 기능
  - [x] username
    - [x] 영어와 숫자로 이루어져야 한다.
    - [x] 길이는 3자 이상 15자 이하여야 한다.
  - [x] password
    - [x] 영어와 숫자로 이루어져야 한다.
    - [x] 길이는 8자 이상 20자 이하여야 한다.
  - [x] phoneNumber
    - [x] ('-'가 없이) 11자리 숫자이어야 한다.
  - [x] address
- [x] 로그인
  - [x] JWT 기반 토큰인증
- [x] 회원 정보 조회
- [x] 회원 정보 수정
  - [x] password 
  - [x] phoneNumber
  - [x] address
- [x] 회원 탈퇴
- API 스펙은 [API 문서](https://www.notion.so/a00bc92443f04c52a852ce16501e981a) 참고

## 🧺 기능 요구사항 2
- 추가 api
  - [x] `POST /api/customers/password` 패스워드 확인 api
- Cart
  - [x] `GET /api/cartItems` 카트 전체 아이템 조회 api
  - [x] `POST /api/cartItems` 카트에 아이템 추가 api
  - [x] `PATCH /api/cartItems{cartItemId}?quantity=2` 카트에 아이템 수량 수정 api
  - [x] `DELETE /api/carItems{cartItemId}` 카트에 아이템 삭제 api
- Order
  - [ ] `POST /api/orders` 주문추가 api
  - [ ] `GET /api/orders` 주문 전체 확인 api
  - [ ] `GET /api/orders/{orderId}` 주문 상세 확인 api
- Product
  - [x] `GET /api/products` 상품 전체 조회 api
  - [x] `GET /api/products/{productId}` 상품 단일 조회 api
  - [x] `POST /api/products` 상품 추가 api
  - [x] `DELETE /api/products/{productId}` 상품 삭제 api

    

## ✏️ Code Review Process
[텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)