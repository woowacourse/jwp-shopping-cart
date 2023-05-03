# jwp-shopping-cart

### 1단계 기능 목록

- [x] 상품 목록 페이지 연동
    - [x] 상품 객체 생성 (ID, 이름, 이미지, 가격)
    - [x] `/`로 접근할 경우 상품 목록 페이지 조회 => `GET /`

- [x] 상품 관리 CRUD API 작성
    - [x] 상품 생성
    - [x] 상품 목록 조회
    - [x] 상품 수정
    - [x] 상품 삭제

- [x] 관리자 도구 페이지 연동 `/admin`
    - [x] `/admin`로 접근할 경우 전체 상품 조회 => `GET /admin/products`
    - [x] `상품 추가` 클릭 시 상품 생성 API 호출 => `POST /admin/products`
    - [x] `수정` 클릭 시 상품 수정 API 호출 => `PUT /admin/products/{productId}`
    - [x] `삭제` 클릭 시 상품 삭제 API 호출 => `DELETE /admin/products/{productId}`

### 리팩터링 목록

- [x] 예외 처리
- [x] `Product` 원시값 포장
- [x] 전체 test 코드 작성

### 2단계 기능 목록

- [x] `/settings`로 접근할 경우 모든 사용자의 정보를 확인 => `GET /admin`
- [x] `SELECT` 클릭 시 사용자 선택
- [x] `SELECT` 클릭 시 이후 요청에 선택한 사용자의 인증 정보 포함
- [x] 사용자 정보는 요청 Header의 `Authorization` 필드를 사용해 인증 처리

- [x] `/cart`로 접근할 경우 장바구니 페이지 조회 => `GET /cart`

- [ ] 장바구니 기능 구현
    - [x] 상품 추가 => `POST /cart`
    - [ ] 장바구니에 담긴 상품 제거 => `DELETE /cart`
    - [x] 목록 조회 => `GET /cart`
    - [x] 목록 불러오기 => `GET /cart/all`
