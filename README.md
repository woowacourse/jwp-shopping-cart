# jwp-shopping-cart

## API

### 페이지
- 어드민 페이지 반환
  - `/admin (Get)`
  - Product 리스트 반환
- 상품 목록 페이지 반환
  - `/ (Get)` 
  - Product 리스트 반환
- 사용자 설정 페이지 반환
  - `/settings (Get)`
  - Member 리스트 반환
- 장바구니 페이지 반환
  - `/cart (Get)`
  - Cart 리스트 반환

### 상품
- 상품 수정
  - `/product/{id} (Put)`
  - 해당 id를 가진 Product 수정
- 상품 삭제
  - `/product/{id} (Delete)`
  - 해당 id를 가진 Product 삭제
- 상품 추가
  - `/product (Post)`
  - 입력한 정보를 바탕으로 Product 추가

### 장바구니
- 장바구니 조회
  - `/carts (Get)`
  - Auth 를 이용하여 해당 사용자의 장바구니 조회
- 장바구니 추가
  - `/carts/{id} (Post)`
  - {id} 는 선택한 Product 의 ID
  - Auth 를 이용하여 해당 사용자의 장바구니에 상품을 추가
- 장바구니 삭제
  - `/carts/{id} (Delete)`
  - {id} 는 선택한 Cart 의 ID
  - Auth 를 이용하여 해당 사용자가 맞는지 검증 후 삭제

## DDL

```sql
CREATE TABLE Product (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    price INT NOT NULL,
    image_url LONGTEXT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Member (
    email VARCHAR(30) UNIQUE NOT NULL,
    password VARCHAR(20) NOT NULL,
    PRIMARY KEY (email)
);

CREATE TABLE Cart (
    id BIGINT AUTO_INCREMENT,
    member_email VARCHAR(30) NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT `cart_ibfk_1` FOREIGN KEY (member_email) REFERENCES Member (email) ON DELETE CASCADE,
    CONSTRAINT `cart_ibfk_2` FOREIGN KEY (product_id) REFERENCES Product (id) ON DELETE CASCADE
);
```
