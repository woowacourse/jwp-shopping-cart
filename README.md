# 요구사항

- [x] 상품 목록 페이지 연동
    - [x] index.html 페이지 구현
- [x] 상품 관리 CRUD API 작성
    - [x] 조회
    - [x] 작성
    - [x] 수정
    - [x] 삭제
- [x] 관리자 도구 페이지 연동
- [x] 상품 테이블을 구성한다.
- [ ] 사용자 기능 구현
    - [ ] email, password를 기반으로 사용자를 저장한다.
    - [ ] email은 중복되지 않는다.
- [ ] 사용자 설정 페이지 연동
    -  [ ] "/settings"로 접근 시 저장된 회원 목록을 보여준다.
    -  [ ] 회원 중 한 명을 선택할 수 있다.

# API 명세서

## 상품 API

| Method | URL              | Description |
|--------|------------------|-------------|
| POST   | `/products`      | 상품 작성       |
| PUT    | `/products/{id}` | 상품 수정       |
| DELETE | `/products/{id}` | 상품 삭제       |

# 테이블 명세

```mermaid
erDiagram
    PRODUCT {
        BIGINT id
        VARCHAR name
        INT price
        TEXT image
    }
```
