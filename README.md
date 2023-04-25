# jwp-shopping-cart

- 기능 목록

## API

### 관리자
- 어드민 페이지 반환 (상품 목록 반환)
  - `/admin (Get)`
  - Product 리스트 반환
- 상품 수정
  - `/admin/product/{id} (Put)`
  - 해당 id를 가진 Product 수정
- 상품 삭제
  - `/admin/product/{id} (Delete)`
  - 해당 id를 가진 Product 삭제
- 상품 추가
  - `/admin/product (Post)`
  - 입력한 정보를 바탕으로 Product 추가

### 상품
- 상품목록
  - `/ (Get)`
  - Product 리스트 반환

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
