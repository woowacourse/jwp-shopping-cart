# 장바구니

장바구니 미션 저장소

# 구현 기능

## 회원 기능

- [X] 회원 가입
    - [X] [userName, password]를 받아서 회원을 가입한다.
    - [X] 유저 이름은 빈칸일 수 없다.
    - [X] 유저 이름은 "영어 소문자, 숫자, _" 만 허용하며, 5자 이상 20자 이하만 가능하다.
    - [X] 비밀번호는 "소문자, 대문자, 특수문자(!, @, #, $, %, ^, &, *, -, _)" 8자 이상 16자 이하만 가능하다.
- [X] 회원 정보 조회
    - [X] 로그인 된 사용자만 자신의 정보를 조회할 수 있다.
    - [X] [password] 값을 제외하고 반환 한다.
- [X] 회원 정보 수정
    - [X] 로그인 된 사용자만 자신의 정보를 수정할 수 있다.
    - [X] [userName] 값을 제외하고 회원 정보를 수정할 수 있다.
- [X] 회원 탈퇴
    - [X] 로그인 된 사용자만 회원 탈퇴를 할 수 있다.
    - [X] 회원 탈퇴를 하면 탈퇴한 회원과 관련된 데이터도 모두 삭제된다.

## 인증 기능

- [X] 로그인 기능
    - [X] JWT Access Token을 발급한다.
    - [X] JWT Access Token을 이용하여 유효성을 검증한다.

## 회원 기능 API

| Method | Url                   | Description |
|--------|-----------------------|-------------|
| POST   | /api/customers        | 회원 가입       |
| GET    | /api/customers/me     | 내 정보 조회     |
| PUT    | /api/customers/me     | 내 정보 수정     |
| DELETE | /api/customers/me     | 회원 탈퇴       |
| GET    | /api/customers/exists | 회원 중복 확인    |
| POST   | /api/login            | 로그인         |

## 장바구니 기능

- [X] JWT 토큰 이용하여 사용자 인증한다.
- [X] 장바구니에 담은 상품의 수량은 0~99 까지 가능하다.
- [X] 장바구니에 담은 상품의 수량을 변경할 수 있다.

## 장바구니 기능 API

| Method | Url                              | Description     |
|--------|----------------------------------|-----------------|
| GET    | /api/customers/me/carts          | 내 장바구니 목록 조회    |
| POST   | /api/customers/me/carts          | 내 장바구니 추가       |
| PATCH  | /api/customers/me/carts/{cartId} | 내 장바구니 물품 수량 변경 |
| DELETE | /api/customers/me/carts/{cartId} | 내 장바구니 삭제       |

## 주문 기능

- [X] JWT 토큰 이용하여 사용자 인증한다.

## 주문 기능 API

| Method | Url                                | Description   |
|--------|------------------------------------|---------------|
| POST   | /api/customers/me/orders           | 주문 추가         |
| GET    | /api/customers/me/orders/{orderId} | 특정 주문 목록 조회   |
| GET    | /api/customers/me/orders/          | 내 모든 주문 목록 조회 |

## 상품 기능

- [X] JWT 토큰 이용하여 사용자 인증한다.
- [X] 상품 목록 조회
    - [X] 로그인이 되지 않은 상황이라면 장바구니에 담은 물품을 고려하지 않는다.
    - [X] 로그인이 된 상황이라면 장바구니에 담겨져 있는 상품인지 확인 할 수 있도록 한다.

## 상품 기능 API

| Method | Url                       | Description        |
|--------|---------------------------|--------------------|
| GET    | /api/products             | 상품 목록 조회           |
| GET    | /api/products/me          | 로그인 된 상품 목록 조회     |
| GET    | /api/products/pageable    | 상품 목록 페이징 조회       |
| GET    | /api/products/pageable/me | 로그인 된 상품 목록 페이징 조회 |
| POST   | /api/products             | 상품 추가              |
| GET    | /api/products/{productId} | 특정 상품 조회           |
| DELETE | /api/products/{productId} | 상품 삭제              |

## ✏️ Code Review Process

[텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)
