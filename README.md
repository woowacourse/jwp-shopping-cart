# jwp-shopping-cart

## 기능 구현 목록

### Domain

- [ ] 상품
    - [ ] 상품 id
    - [ ] 이름
    - [ ] 이미지
    - [ ] 가격

### Web

- 상품 조회
    - [ ] GET /
    - [ ] index.html 반환

- 상품 추가
    - [ ] POST /admin/product
    - [ ] admin 페이지에서 상품을 추가한다.
    - [ ] 상품 추가 요청을 유효성 검증한다.
        - [ ] 이름이 공백일 수 없다.
        - [ ] 가격이 0원 이상 이어야 한다.

- 상품 조회
    - [ ] GET /admin/products
    - [ ] admin 페이지에서 상품을 보여준다.

- 상품 수정
    - [ ] PATCH /admin/product/id
    - [ ] admin 페이지에서 상품을 수정한다.
  
- 상품 삭제
    - [ ] DELETE /admin/product/id
    - [ ] admin 페이지에서 상품을 삭제한다.
