# jwp-shopping-cart

## 기능 구현 목록

- [x] 상품 목록 페이지 연동
  - [x] `/`로 이동하면 상품 목록 페이지를 조회할 수 있어야 한다.
  - [x] 상품의 목록은 `ID`, `이름`, `이미지`, `가격`을 가지고 있어야 한다.
- [x] 상품 관리 CRUD API 작성
  - [x] 생성, 조회, 수정, 삭제 API를 작성해야 한다.
  - [x] API 스펙은 정해진게 없으므로, API 설계를 직접 진행 한 후 기능을 구현한다.
- [x] 관리자 도구 페이지 연동
  - [x] `/admin`으로 이동하면 관리자 도구 페이지를 조회할 수 있어야 한다.
  - [x] 상품 추가, 수정, 삭제 기능이 동작하게 만들어야 한다.

## 상품 관리 API 명세
- [x] 생성
  - [x] `/products` URL에 `POST` 요청을 보내면 상품이 생성되어야 한다.
  - [x] 생성 시 `body`에는 `이름`, `이미지`, `가격`을 보낸다.
  - [x] 이름은 `name`, 이미지는 `image`, 가격은 `price`로 한다.
- [x] 수정
  - [x] `/products/{id}` URL에 `PATCH` 요청을 보내면 상품이 수정되어야 한다.
  - [x] 수정 시 `body`에는 `이름`, `이미지`, `가격`을 보낸다.
  - [x] 이름은 `name`, 이미지는 `image`, 가격은 `price`로 한다.
- [x] 삭제
  - [x] `/products/{id}` URL에 `DELETE` 요청을 보내면 상품이 삭제되어야 한다.
