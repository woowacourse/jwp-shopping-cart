# jwp-shopping-cart

### 기능 목록

- [x] 상품 목록 페이지 연동
    - [x] 상품 객체 생성 (ID, 이름, 이미지, 가격)
    - [x] `/`로 접근할 경우 상품 목록 페이지 조회

- [x] 상품 관리 CRUD API 작성
    - [x] 상품 생성
    - [x] 상품 목록 조회
    - [x] 상품 수정
    - [x] 상품 삭제

- [x] 관리자 도구 페이지 연동
    - [x] `/admin`로 접근할 경우 전체 상품 조회
    - [x] `상품 추가` 클릭 시 상품 생성 API 호출
    - [x] `수정` 클릭 시 상품 수정 API 호출
    - [x] `삭제` 클릭 시 상품 삭제 API 호출

- [ ] 사용자 기능 구현
    - [x] 사용자 저장
    - [x] `/settings`로 접근할 경우 전체 사용자 조회
    - [ ] `select` 클릭 시 이후 요청에 사용자 인증 정보 포함

- [ ] 장바구니 기능 구현
    - [ ] 사용자 인증 정보는 요청 header에 포함
        - [ ] basic 인증 사용
    - [ ] `담기` 클릭 시 장바구니에 추가
    - [ ] `/cart`로 접근할 경우 전체 장바구니 목록 조회
    - [ ] `delete`클릭 시 장바구니에서 제거

### 리팩터링 목록

- [x] 예외 처리
- [x] 전체 test 코드 작성
