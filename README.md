# jwp-shopping-cart

## 장바구니

- [ ] 사용자 기능 구현
- [ ] 사용자 설정 페이지 연동
- [ ] 장바구니 기능 구현
- [ ] 장바구니 페이지 연동

### 도메인 엔티티

- [x] 상품
    - [x] id
    - [x] 이름
        - [x] 1 ~ 20 자로 제한한다.
    - [x] 이미지
    - [x] 가격
        - [x] 숫자로만 이루어진다.
        - [x] 0원 이상 1억원 이하이다.
        - [x] 10원 단위여야 한다.
- [ ] 사용자
    - [ ] id
    - [ ] email
        - [ ] '@'를 1개 포함해야 한다.
        - [ ] 길이는 5 ~ 30 글자이다.
    - [ ] password
        - [ ] 길이는 6 ~ 30 글자이다.

### DAO

- [x] Product CRUD

### Service

- [x] 장바구니 서비스
    - [x] 상품 추가
    - [x] 상품 전체 조회
    - [x] 상품 수정
    - [x] 상품 삭제

### API 명세

- [x] 상품 Create
    - POST "/product"
    - Request: name, price, image
    - Response: 200 OK
- [x] 상품 Read
    - GET "/products"
    - Response: products{ {id, name, image, price}, {..}}
- [x] 상품 Update
    - PUT "/product"
    - Request: id, name, price, image
    - Response: 200 OK
- [x] 상품 Delete
    - DELETE "/product/{id}"
    - Response: 200 OK

