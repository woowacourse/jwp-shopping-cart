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
  - `/product`
  - 입력한 정보를 바탕으로 Product 추가
- 상품 수정
  - PUT
  - `/product/{id}`
  - 해당 id를 가진 Product 수정
- 상품 삭제
  - DELETE
  - `/product/{id}`
  - 해당 id를 가진 Product 삭제


## DDL

```sql
CREATE TABLE Product (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    price INT NOT NULL,
    image_url LONGTEXT NOT NULL,
    PRIMARY KEY(id)                  
)
```
