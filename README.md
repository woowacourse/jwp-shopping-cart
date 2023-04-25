# 요구사항

- [x] 상품 목록 페이지 연동
    - [x] index.html 페이지 구현
- [ ] 상품 관리 CRUD API 작성
    - [ ] 조회
    - [ ] 작성
    - [ ] 수정
    - [ ] 삭제
- [ ] 관리자 도구 페이지 연동
- [ ] 상품 테이블을 구성한다.

# API 명세서

## 상품 API

| Method | URL             | Description |
|--------|-----------------|-------------|
| POST   | `/product`      | 상품 작성       |
| GET    | `/product/list` | 상품 조회       |
| PUT    | `/product/{id}` | 상품 수정       |
| DELETE | `/product/{id}` | 상품 삭제       |
