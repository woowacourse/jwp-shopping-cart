# jwp-shopping-cart

## 기능 목록

- 컨트롤러
  - 웹페이지
    - 상품 목록 `/`
    - 관리자 `/admin`
  - API
    - 추가 `POST /products`
    - 수정 `PUT /products/{id}`
    - 삭제 `DELETE /products/{id}`
    - 상품 요청 페이로드
      - 이름은 필수이다.
      - 이미지는 필수이다.
        - 2000글자 이내이다.
        - URL이어야 한다.
      - 가격은 필수이다.
- 상품
  - [ ] 이름은 20글자 이하이다.
  - [ ] 금액은 1,000,000원 이하이다.