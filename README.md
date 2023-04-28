# jwp-shopping-cart

## 기능 목록

### API

### admin

- [x] 관리자 페이지 (상품 반환)
  - `GET /admin`
  - Product 리스트 반환
- [x] 상품 수정
  - `PUT /product/{id}`
  - 입력한 id와 일치하는 Product 수정
- [x] 상품 삭제
  - `DELETE  product/{id}`
  - 입력한 id를 가진 Product 삭제
- [x] 상품 추가
  - `POST /product`
  - 입력한 상품을 추가

- [x] 상품 목록 페이지
  -  상품 리스트 조회

### DB

````
CREATE TABLE IF NOT EXISTS PRODUCT
(
id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(200) NOT NULL,
image VARCHAR(2047) NOT NULL,
price BIGINT NOT NULL,
PRIMARY KEY (id)
);
````
