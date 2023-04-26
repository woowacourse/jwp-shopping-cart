# jwp-shopping-cart

- 10분씩 돌아가면서 진행한다.
    - 하던 코드까지는 마무리 한다.

## 요구 사항 목록
- [x] 상품 목록 페이지 연동
- [x] 상품 관리 CRUD API 작성
  - [x] 상품 생성 API 작성
  - [x] 상품 목록 조회 API 작성
  - [x] 상품 수정 API 작성
  - [x] 상품 삭제 API 작성
- [x] 관리자 도구 페이지 연동

## 도메인 요구사항
- 상품은 ID값을 가진다
- 상품은 이름을 가진다.
- 상품은 이미지를 가진다.
- 상품은 가격을 가진다.

## admin api

POST admin/items/add
POST admin/items/edit/{id}
POST admin/items/delete/{id}
GET  admin/

## 질문
- 값을 변경시에 수정해야하는 칼럼만 수정하는 방식이랑 전부 수정하는 거랑 어떤차이가 있는지
