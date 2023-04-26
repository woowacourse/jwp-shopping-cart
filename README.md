# jwp-shopping-cart

## 기능 구현 목록

### API 명세

- [x] 상품 생성
  - POST "/admin"
  - Request: name, price, image_url
  - Response: 201 OK
- [x] 상품 조회
  - GET "/admin"
  - Response: products{ {id, name, image_url, price}, {..}}
- [x] 상품 수정
  - PUT "/admin/{id}"
  - Request: name, price, image_url
  - Response: 200 OK
- [x] 상품 삭제
  - DELETE "/admin/{id}"
  - Response: 200 OK


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

  