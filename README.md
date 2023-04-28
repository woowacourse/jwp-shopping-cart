# jwp-shopping-cart

## 기능 요구 사항

- [x] 상품 기본 정보는 상품 ID, 상품 이름, 상품 이미지 url, 상품 가격이다.
  - 상품 이름은 1자 이상 20자 이하이다.
  - 상품 가격은 10원 단위의 양수여야 한다.
  - 상품 이미지 url은 512자 이하의 문자열이여야 한다.
- - - -

- [x] 상품 목록 페이지 연동
  - [x] 전체 상품 조회(/)


- [x] 관리자 도구 페이지 상품 관리 CRUD API
  - [x] 생성 (/admin/product POST)
    - 상품의 형식이 올바르지 않을 때: `BAD_REQUEST`
  - [x] 전체 상품 조회 (/admin)
  - [x] 수정 (/admin/product/{id} PUT)
    - 상품의 형식이 올바르지 않을 때: `BAD_REQUEST`
    - 요청된 id에 대한 데이터가 없을 시: `NOT_FOUND`
  - [x] 삭제 (/admin/product/{id} DELETE)
    - 요청된 id에 대한 데이터가 없을 시: `NOT_FOUND`

  - 이외의 예외가 발생했을 때: `INTERNAL_SERVER_ERROR`
