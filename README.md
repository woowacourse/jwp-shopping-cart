# jwp-shopping-cart

## 기능 구현 목록

### API 명세

- [x] 상품 단일 조회
    - GET "/products/{id}"
    - Response: 200 OK
        - id, name, price, image_url

- [x] 상품 생성
  - POST "/products"
  - Request: name, price, image_url
  - Response: 201 Created
    - header
        - location: "/products/{id}"
- [x] 상품 수정
  - PUT "/products/{id}"
  - Request: name, price, image_url
  - Response: 204 No Content
- [x] 상품 삭제
  - DELETE "/products/{id}"
  - Response: 200 No Content

### View

URL : "/"
    - 존재하는 모든 상품의 리스트를 제공한다.

URL : "/admin"
    - 존재하는 모든 상품의 리스트를 제공한다.
    - 상품의 추가, 수정, 삭제가 가능하다.

### 도메인 

Product 
  - id
  - name
    - [ ] 이름은 1자 이상, 20자 이하이다.
  - price
    - [ ] 가격은 1000(원) 이상이여야 한다.
    - [ ] 가격의 단위는 100원 단위이다
  - image_url

### 데이터베이스 테이블 정보
  Product
  - id(pk)
  - name
  - price
  - image_url 

