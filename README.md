# jwp-shopping-cart

## [1단계]

### API 명세서

| Method | URI                  | Description |
|--------|----------------------|-------------|
| GET    | /                    | 상품 목록       |
| GET    | /admin               | 어드민 페이지 목록  |
| POST   | /admin/products/     | 상품 추가       |
| PUT    | /admin/products/{id} | 상품 수정       |
| DELETE | /admin/products/{id} | 상품 삭제       |

### 기능 요구사항

- [x] 상품 목록 페이지 연동
    - [x] index.html 파일을 이용하여 상품 목록이 노출되는 페이지를 완성한다.
- [x] 상품 관리 CRUD API 작성
- [x] 관리자 도구 페이지 연동

### 피드백 요구사항

- [x] view 담당 Controller와 resource(json) 담당 Controller 분리
- [x] 반환하는 자원의 확장자명까지 명확히 표기
- [x] 요청에 대한 응답 코드와 자원 포함
- [x] 원시값 포장
