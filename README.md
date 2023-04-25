# jwp-shopping-cart

## 기능 요구사항

- [ ] 상품 목록 페이지 연동
  - [ ] `/` url로 접속했을 때, 바로 상품 목록 페이지로 접속하도록 연동한다.
  - [ ] index.html에 조회된 정보 표시
- [ ] 상품 관리 CRUD API 구현
  - [ ] 상품 목록 조회 GET API 구현
    - [x] 상품 목록 전체 조회 기능 구현
    - [ ] GET `/product` url 맵핑
      - 상품 id 
      - 상품 이미지
      - 상품 이름
      - 상품 가격
  - [ ] 상품 등록 API 구현
    - [ ] 상품 저장 기능 구현
    - [ ] POST `/product` url 맵핑
      - 상품 이름
      - 상품 가격
      - 상품 이미지 URL
  - [ ] 상품 수정 API 구현
    - [ ] 상품 정보 업데이트 기능 구현 
    - [ ] PUT `/product/{id}` url 맵핑
      - 상품 이름
      - 상품 가격
      - 상품 이미지 URL
  - [ ] 상품 삭제 API 구현
    - [ ] DELETE `/product/{id}` url 맵핑
    - [ ] 상품 삭제 기능 구현
- [ ] 관리자 도구 페이지 연동
  - [ ] `/admin` url로 접속했을 때, 관리자 페이지로 접속하도록 연동한다.
  - [ ] admin.html에 조회된 정보 표시

