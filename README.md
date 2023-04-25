# jwp-shopping-cart

## 기능 구현 목록

### Database

- H2 Database 사용
- DDL은 이하 기술

```sql
CREATE TABLE product
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    name      VARCHAR(50)  NOT NULL,
    price     BIGINT       NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    PRIMARY KEY (id)
);

```

### Domain

- [x] 상품
    - [x] 상품 id
    - [x] 이름
        - [x] 이름은 비어있을 수 없다.
    - [x] 이미지
        - [x] url은 비어있을 수 없다.
    - [x] 가격
        - [x] 가격은 0 미만일 수 없다.

### Web

- 상품 조회
    - [ ] GET /
    - [ ] index.html 반환

- 상품 추가
    - [ ] POST /admin/product
    - [ ] admin 페이지에서 상품을 추가한다.
    - [ ] 상품 추가 요청을 유효성 검증한다.
        - [ ] 이름이 공백일 수 없다.
        - [ ] 가격이 0원 이상 이어야 한다.

- 상품 조회
    - [ ] GET /admin/products
    - [ ] admin 페이지에서 상품을 보여준다.

- 상품 수정
    - [ ] PATCH /admin/product/id
    - [ ] admin 페이지에서 상품을 수정한다.

- 상품 삭제
    - [ ] DELETE /admin/product/id
    - [ ] admin 페이지에서 상품을 삭제한다.
