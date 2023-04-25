# jwp-shopping-cart

## 장바구니

- [x] 상품 목록 페이지 연동

### 도메인

- [x] 상품
    - [x] id
    - [x] 이름
        - [x] 1 ~ 20 자로 제한한다.
    - [x] 이미지
    - [x] 가격
        - [x] 숫자로만 이루어진다.
        - [x] 0원 이상 1억원 이하이다.
        - [x] 10원 단위여야 한다.

### API 명세

- [ ] 상품 Create
    - POST "/product"
    - Request: name, price, image
    - Response: 201 Created
- [ ] 상품 Read
    - GET "/proudcts"
    - Response: products{ {id, name, image, price}, {..}}
- [ ] 상품 Update
    - PUT "/product"
    - Request: id, name, price, image
    - Response: 200 OK
- [ ] 상품 Delete
    - DELETE "/product/{id}"
    - Response: 200 OK

