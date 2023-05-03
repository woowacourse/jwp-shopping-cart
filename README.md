# jwp-shopping-cart

## 기능 목록

### 1단계
- [x] 상품 목록 페이지 연동
    - [x] `/` url로 접근할 경우 상품 목록 페이지를 들어가야 한다.
    - [x] 모든 상품의 정보를 가져와 보여가준다.
- [x] 상품 관리 CRUD API 작성
    - [x] 전체 상품 조회 `GET /product` → `200 OK`
    - [x] 상품 생성 `POST /products` → `201 CREATED`
    - [x] 상품 수정 `PATCH /products/{productId}` → `204 NO CONTENT`
    - [x] 상품 삭제 `DELETE /products/{productId}` → `204 NO CONTENT` 
- [x] 관리자 도구 페이지 연동
    - [x] `/admin` url로 접근할 경우 관리자 도구 페이지에 들어가야 한다.
    - [x] 모든 상품의 정보를 가져와 보여준다.
    - [x] 상품을 추가할 수 있다.
    - [x] 상품을 수정할 수 있다.
    - [x] 상품을 삭제할 수 있다.
- [x] 데이터베이스
    - [x] 모든 상품을 조회한다.
    - [x] 상품을 생성한다.
    - [x] 상품을 수정한다.
    - [x] 상품을 삭제한다.

### 2단계
- [x] 사용자 기능 구현
  - [x] 사용자는 이메일과 비밀번호를 가진다.
- [x] 사용자 설정 페이지 연동
  - [x] 모든 사용자의 정보를 확인하고, 사용자를 선택할 수 있는 페이지를 만든다.
- [ ] 장바구니 기능 구현
  - [ ] API 추가
    - [x] 장바구니 상품 추가`POST /cart/products/{productId}` → `201 CREATED`
    - [ ] 장바구니에 담긴 상품 제거 `DELETE /cart/products/{productId}` → `204 NO CONTENT`
    - [x] 장바구니 목록 조회 `GET /cart/products` → `200 OK`
  - [x] 사용자 정보는 Authorization 필드를 사용해 인증 처리를 한다.
  - [x] 인증 방식은 Basic 인증을 사용한다.
- [ ] 장바구니 페이지 연동
  - [ ] 상품 목록 페이지에서 장바구니를 추가할 수 있다.
  - [ ] 장바구니 페이지를 통해 각 유저에 맞는 장바구니 목록을 확인하고, 상품을 제거하는 기능을 동작하게 한다.

## 테이블 구조

```sql
CREATE TABLE member
(
  id       BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
  email    VARCHAR(50) NOT NULL,
  password VARCHAR(30) NOT NULL
);

CREATE TABLE product
(
  id        BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name`    VARCHAR(255) NOT NULL,
  image_url TEXT         NOT NULL,
  price     INT          NOT NULL
);

CREATE TABLE cart_product
(
  id         BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  member_id  BIGINT NOT NULL,
  product_id BIGINT NOT NULL,
  FOREIGN KEY (member_id) REFERENCES member (id),
  FOREIGN KEY (product_id) REFERENCES product (id),
  UNIQUE (member_id, product_id);
);
```

## API 문서

[API 문서 링크](https://70825.notion.site/API-f36d0bced7af47ef9c318ddadc35f96f)
