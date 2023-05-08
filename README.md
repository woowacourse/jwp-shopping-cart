# jwp-shopping-cart

## 장바구니

- [x] 상품 목록 페이지 연동
- [x] 사용자 설정 페이지 연동
- [x] 장바구니 페이지 연동

### 공통

- [x] 어노테이션
- [x] ArgumentResolver

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

- [x] 사용자
    - [x] email, password

### DAO

- [x] Product
    - [x] create
    - [x] read
    - [x] update
    - [x] delete

- [x] Member
    - [x] create
    - [x] read

- [x] Cart
    - [x] create
    - [x] read
    - [x] delete

### Service

- [x] 상품 서비스
    - [x] 상품 추가
    - [x] 상품 전체 조회
    - [x] 상품 수정
    - [x] 상품 삭제

- [x] 사용자 서비스
    - [x] 사용자 추가
    - [x] 사용자 조회

- [x] 장바구니 서비스
    - [x] 장바구니 상품 추가
    - [x] 장바구니 상품 조회
    - [x] 장바구니 상품 삭제

### API 명세

상품

- [x] 상품 Create
    - POST "/product"
    - Request: name, price, image
    - Response: 204 NoContent
- [x] 상품 Read
    - GET "/products"
    - Response: products{ {id, name, image, price}, {..}}
- [x] 상품 Update
    - PUT "/product"
    - Request: id, name, price, image
    - Response: 204 NoContent
- [x] 상품 Delete
    - DELETE "/product/{id}"
    - Response: 204 NoContent

사용자

- [x] 사용자 Create
    - POST "/member"
    - Request: email, password
    - Response: 200 OK

장바구니

- [x] 장바구니 Create
    - POST "/carts/{id}"
    - Response: 204 NoContent
- [x] 장바구니 Read
    - GET "/carts"
    - Response: 200 OK
- [x] 장바구니 Delete
    - DELETE "/carts/{id}"
    - Response: 204 NoContent
