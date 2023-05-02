# jwp-shopping-cart

## step 1 - 상품 관리 기능
### 미션 요구사항

- [x] 상품 목록 페이지 연동 ([GET] /products)
- [x] 관리자 도구 페이지 연동 ([GET] /admin)
- [x] 상품 관리 CRUD API 작성
    - [x] 상품 생성
        - [POST] /admin/product
    - [x] 상품 목록 조회
        - [GET] /admin/products
    - [x] 상품 수정
        - [PUT] /admin/products/{id}
    - [x] 상품 삭제
        - [DELETE] /admin/products/{id}

## step 2 - 장바구니 기능

### 미션 요구사항
- 사용자
  - [ ] 사용자 기능 구현
    - [ ] 전체 조회
    - [ ] 생성
  - [ ] 설정 페이지 연동
  - [ ] 인증
    - 인증 방식은 Basic 인증 사용
      - type: Basic
      - credentials : email:password를 base64로 인코딩한 문자열
      - ex) email@email.com:password -> ZW1haWxAZW1haWwuY29tOnBhc3N3b3Jk
- 장바구니
  - 기능 구현
    - [ ] 상품 추가
    - [ ] 담긴 상품 제거
    - [ ] 목록 조회
  - 페이지 연동
    - [ ] 상품 추가
    - [ ] 목록 조회 및 제거