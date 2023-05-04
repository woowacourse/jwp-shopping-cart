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
- [x] 요청에 대한 응답 코드와 자원 포함
- [x] 원시값 포장

## [2단계]

### API 명세서

| Method | URI              | Description |
|--------|------------------|-------------|
| GET    | /settings        | 사용자선택 페이지   |
| GET    | /settings/login  | 사용자 로그인 기능  |
| GET    | /cart            | 장바구니 목록 페이지 |
| GET    | /cart/items      | 장바구니 목록 반환  |
| POST   | /cart/items      | 장바구니에 상품 추가 |
| DELETE | /cart/items/{id} | 장바구니에 상품 삭제 |

### 기능 요구사항

- [x] 사용자 기능 구현
- [x] 사용자 설정 페이지 연동
- [x] 장바구니 기능 구현
    - [x] 장바구니에 상품 추가
    - [x] 장바구니에 담긴 상품 제거
    - [x] 장바구니 목록 조회
- [x] 장바구니 페이지 연동
