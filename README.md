# jwp-shopping-cart

## 상품 목록 페이지 연동
- [x] spec
    - [x] HttpMethod: GET
    - [x] path: "/"

## 상품 관리 CRUD API 작성
- [x] Create
    - [x] HttpMethod: POST
    - [x] Header
      - [x] Authorization: Basic Base64
    - [x] path: "/admin/products"
- [x] Read
    - [x] HttpMethod: GET
    - [x] path: "/admin"
- [x] Update
    - [x] HttpMethod: PATCH
    - [x] Header
      - [x] Authorization: Basic Base64
    - [x] path: "/admin/products/{product_id}"
- [x] Delete
    - [x] HttpMethod: DELETE
    - [x] Header
      - [x] Authorization: Basic Base64
    - [x] path: "/admin/products/{product_id}"

## 회원가입 API 작성
- [x] Read
  - [x] HttpMethod: GET
  - [x] path: "/register"
- [x] Create
  - [x] HttpMethod: POST
  - [x] path: "/register"
  - 
## 로그인 API 작성
- [x] Read
  - [x] HttpMethod: GET
  - [x] path: "/settings"
- [x] Create
  - [x] HttpMethod: POST
  - [x] Header
    - [x] Authorization: Basic Base64
  - [x] path: "/settings"

## 인증 인터셉터 
- [x] Base64AdminAccessInterceptor: 관리자 페이지 상품 등록,수정,삭제
- [x] Base64AuthInterceptor: 장바구니 페이지 접근

## @Auth ArgumentResolver
- [x] @Auth 어노테이션을 확인 후 Header에 Authorization 값을 디코딩하여 넘겨준다.

## Utils
- [x] 비밀번호를 데이터베이스에 저장할 때 간단한 암호화 알고리즘 적용
  - [x] Caesar Cipher
