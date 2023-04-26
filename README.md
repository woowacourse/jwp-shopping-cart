# jwp-shopping-cart

## [1단계]

### API 명세서

| Method | URI         | Description |
|--------|-------------|-------------|
| GET    | /           | 상품 목록       |
| GET    | /admin      | 어드민 페이지 목록  |
| POST   | /admin      | 상품 추가       |
| GET    | /admin/{id} | 상품 수정 페이지   |
| PUT    | /admin/{id} | 상품 수정       |
| DELETE | /admin/{id} | 상품 삭제       |

### 기능 요구사항

- [x] 상품 목록 페이지 연동
    - [x] index.html 파일을 이용하여 상품 목록이 노출되는 페이지를 완성한다.
- [x] 상품 관리 CRUD API 작성
- [x] 관리자 도구 페이지 연동
