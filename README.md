# jwp-shopping-cart

## API

### 페이지
- 메인 페이지 (상품목록)
  - GET
  - `/`
- 관리자 페이지
  - GET
  - `/admin`

### 상품 API
- 상품 추가
  - POST
  - `/products`
  - 입력한 정보를 바탕으로 Product 추가
- 상품 수정
  - PUT
  - `/products/{id}`
  - 해당 id를 가진 Product 수정
- 상품 삭제
  - DELETE
  - `/products/{id}`
  - 해당 id를 가진 Product 삭제

### 장바구니 API
- 장바구니에 상품 추가
  - POST
  - `/cart/{id}`
  - 해당 id를 가진 Product를 장바구니에 추가
- 장바구니에 담긴 상품 제거
  - DELETE
  - `/cart/{id}`
  - 해당 id를 가진 Product를 장바구니에서 제거
- 장바구니 목록 조회
  - GET
  - `/cart`
  - 해당 사용자의 장바구니 목록을 조회

## DDL

### 상품

```sql
CREATE TABLE Product (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    price INT NOT NULL,
    image_url LONGTEXT NOT NULL,
    PRIMARY KEY(id)                  
);
```

### 사용자

```sql
CREATE TABLE Member (
    id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    PRIMARY KEY(id)                  
);
```

### 장바구니

```sql
CREATE TABLE Cart (
    member_id INT NOT NULL,
    product_id INT NOT NULL,
    FOREIGN KEY(member_id) REFERENCES Member(id),                 
    FOREIGN KEY(product_id) REFERENCES Product(id)                 
);
```
