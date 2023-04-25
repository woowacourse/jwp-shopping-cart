# jwp-shopping-cart

## 장바구니

- [x] 상품 목록 페이지 연동

### 도메인
- [x] 상품
  - [x] id
  - [x] 이름
    - [ ] 1 ~ 20 자로 제한한다.
  - [x] 이미지
  - [x] 가격
    - [ ] 숫자로만 이루어진다.
    - [ ] 0원 이상 1억원 이하이다.
    - [ ] 10원 단위여야 한다.

### API 명세
- [ ] 상품 Create
  - POST "/product"
  - Request: name, price, image
  - Response: 201 Created
- [ ] Read
  - GET "/proudcts"
  - Response: products{ {id, name, image, price}, {..}}
- [ ] Update
  - PUT "/product"
  - Request: id, name, price, image
  - Response: 200 OK
- [ ] Delete
  - DELETE "/product/{id}" 
  - Response: 200 OK

