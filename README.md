# jwp-shopping-cart

## 요구 사항 목록
- [x] 데이터 베이스 설계
- [x] 상품 목록 페이지 연동
- [x] 상품 관리 CRUD API 작성
  - [x] 상품 생성 API 작성
  - [x] 상품 목록 조회 API 작성
  - [x] 상품 수정 API 작성
  - [x] 상품 삭제 API 작성
- [x] 관리자 도구 페이지 연동
- [ ] 사용자 기능 구현
- [ ] 사용자 설정 페이지 연동
- [ ] 장바구니 기능 구현
- [ ] 장바구니 페이지 연동


## 도메인 요구사항
- [x] 상품
  - [x] 상품은 ID값을 가진다
  - [x] 상품은 이름을 가진다.
  - [x] 상품은 이미지를 가진다.
  - [x] 상품은 가격을 가진다.
- [x] 사용자
  - [x] 사용자는 이메일을 가진다.
  - [x] 사용자는 비밀번호를 가진다.
- [ ] 장바구니
  - [ ] 장바구니는 상품 목록을 가진다.

## admin api

POST admin/items/new
POST admin/items/edit/{id}
POST admin/items/delete/{id}
GET  admin/
