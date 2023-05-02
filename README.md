# jwp-shopping-cart

## 기능 구현 목록

### Database

- H2 Database 사용
- DDL은 이하 기술

```sql
CREATE TABLE product
(
    id        BIGINT      NOT NULL AUTO_INCREMENT,
    name      VARCHAR(50) NOT NULL,
    price     BIGINT      NOT NULL,
    image_url TEXT        NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE member
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    email    VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE cart
(
    id         BIGINT NOT NULL AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    user_id    BIGINT NOT NULL,
    PRIMARY KEY (id)
)

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
    - [x] GET /
    - [x] index.html 반환

- 상품 추가
    - [x] POST /product
        - [x] body : name, price, imageUrl
    - [x] admin 페이지에서 상품을 추가한다.
        - [x] 추가 성공 시 status created
    - [x] 상품 추가 요청을 유효성 검증한다.
        - [x] 이름이 공백일 수 없다.
        - [x] 가격이 0원 이상 이어야 한다.
        - [x] URL이 공백일 수 없다.

- 상품 조회
    - [x] GET /admin
    - [x] admin 페이지에서 상품을 보여준다.
    - [x] admin.html 반환

- 상품 수정
    - [x] PATCH /product/id
        - [x] body : name, price, imageUrl
    - [x] admin 페이지에서 상품을 수정한다.
        - [x] 해당 id에 상품이 없을 시 status not found
        - [x] update 성공 시 status ok
    - [x] 상품 수정 요청을 유효성 검증한다.
        - [x] 이름이 공백일 수 없다.
        - [x] 가격이 0원 이상 이어야 한다.
        - [x] URL이 공백일 수 없다.

- 상품 삭제
    - [x] DELETE /product/id
    - [x] admin 페이지에서 상품을 삭제한다.
        - [x] 해당 id에 상품이 없을 시 status not found
        - [x] delete 성공 시 status ok

### 사용자

- [x] GET /settings
    - [x] 유저 전체 조회
- [ ] Basic Authorization을 통해 인증

### 장바구니

- [ ] GET /cart
    - [ ] 장바구니의 상품 조회

- [ ] POST /cart
    - [ ] 장바구니에 상품 추가
    - [ ] body : id, name, price, imageUrl

- [ ] DELETE /cart/id
    - [ ] 장바구니의 상품 삭제
        - [ ] 성공 시 status ok
        - [ ] 상품 없을 시 status not found
