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
- [x] 관리자 도구 페이지 연동
  - [x] `/admin` url로 접속했을 때, 관리자 페이지로 접속하도록 연동한다.
  - [x] admin.html에 조회된 정보 표시
- [X] 예외
  - [X] 상품 이름은 1글자 이상 50글자 이하여야 한다.
  - [X] 상품 가격은 양수여야 한다.

