# jwp-shopping-cart

## 기능 요구사항

- [x] 상품 목록 페이지 연동
  - [x] `/` url로 접속했을 때, 바로 상품 목록 페이지로 접속하도록 연동한다.
  - [x] index.html에 조회된 정보 표시
- [x] 상품 관리 CRUD API 구현
  - [x] 상품 목록 조회 GET API 구현
    - [x] 상품 목록 전체 조회 기능 구현
    - [x] GET `/admin` url 맵핑
      - 상품 id 
      - 상품 이미지
      - 상품 이름
      - 상품 가격
  - [x] 상품 등록 API 구현
    - [x] 상품 저장 기능 구현
    - [x] POST `/admin/product` url 맵핑
      - 상품 이름
      - 상품 가격
      - 상품 이미지 URL
  - [x] 상품 수정 API 구현
    - [x] 상품 정보 업데이트 기능 구현
    - [x] PUT `admin/product/{id}` url 맵핑
      - 상품 이름
      - 상품 가격
      - 상품 이미지 URL
  - [x] 상품 삭제 API 구현
    - [x] DELETE `admin/product/{id}` url 맵핑
    - [x] 상품 삭제 기능 구현
  - [ ] 예외
    - [ ] 관리자 계정이 아니라면 `/admin` 접근 불가
- [x] 관리자 도구 페이지 연동
  - [x] `/admin` url로 접속했을 때, 관리자 페이지로 접속하도록 연동한다.
  - [x] admin.html에 조회된 정보 표시
- [x] 사용자 기능 구현
  - [x] Email, Password 가지는 사용자 DB 설계
  - [x] 사용자 등록 API 구현
    - [x] 사용자 등록 기능 구현
      - email
      - password
      - 이메일 중복 불가
    - [x] POST `/settings/users` url 맵핑
  - [x] 사용자 전체 조회 API 구현
    - [x] 전체 사용자 조회 기능 구현
      - id 
      - email
      - password
    - [x] GET  `/settings` url 맵핑
  - [x] 요청 헤더의 `Authorization` 필드에서 사용자 정보 조회 기능 구현
    - [ ] 로그인이 안되었다면 `/` 만 접근 가능, 다른 url은 모두 401 예외 반환
- [x] 사용자 설정 페이지 연동
  - [x] `/settings` 에 접근하면 전체 사용자 목록 페이지로 접속하도록 연동한다.
  - [x] settings.html에 사용자 정보 표시되도록 변경
  - [x] settings.js 에서 사용자 정보에 맞게 credential 생성되도록 변경
  - **사용자가 선택된 후, 이루어지는 모든 요청 헤더에 인증 정보 필요**
    - `Authorization: Basic {credential}` 로 헤더에 인증 정보 추가
    - Credential: `email:password` 를 base64 인코딩
- [ ] 장바구니 기능 구현
  - [x] 장바구니 DB 설계 
  - [x] 장바구니 상품 담기 API 구현 
    - [x] 상품 담기 기능 구현
      - productId
      - 동일한 상품을 중복해서 담을 수 없다.
      - 로그인 된 사용자의 장바구니에 추가해야 한다.
    - [x] POST `/cart` url 맵핑
  - [ ] 장바구니 상품에서 상품 빼기 API 구현
    - [x] 장바구니 상품 제거 기능 구현
      - 담겨있지 않은 상품 제거 요청 오면 예외
      - 로그인 된 사용자의 장바구니에서 제거해야 한다.
    - [ ] DELETE `/cart/{cartId}` url 맵핑
  - [x] 장바구니 목록 조회 API 구현
    - [x] 사용자 장바구니 상품 조회 기능 구현
      - cartId
      - 상품명
      - 상품 가격
      - 상품 이미지 URL
      - **인증 정보에 따라 로그인 된 사용자의 상품만 조회해야 한다.**
    - [x] GET `/cart` url 맵핑
- [ ] 장바구니 페이지 연동
  - [x] `/cart` 에 접속했을 때, 장바구니 상품 조회 페이지로 연동되도록 한다. 
  - [x] cart.html에 전체 장바구니 정보 표시되도록 연동
  - [ ] cart.js에서 장바구니 상품 추가, 삭제할 수 있게 연동
- [X] 도메인 예외
  - [X] 상품 이름은 1글자 이상 50글자 이하여야 한다.
  - [X] 상품 가격은 양수여야 한다.

