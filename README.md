# jwp-shopping-cart

## 기능 목록

- [x] 상품 목록 페이지 연동
    - [x] `/` url로 접근할 경우 상품 목록 페이지를 들어가야 한다.
    - [x] 모든 상품의 정보를 가져와 보여가준다.
- [x] 상품 관리 CRUD API 작성
    - [x] 상품 생성 POST `/admin/products`
    - [x] 상품 목록 조회 GET
        - [x] home `/`
        - [x] admin `/admin`
    - [x] 상품 수정 PUT `/admin/products/{id}`
    - [x] 상품 삭제 DELETE `/admin/products/{id}`
- [x] 관리자 도구 페이지 연동
    - [x] `/admin` url로 접근할 경우 관리자 도구 페이지에 들어가야 한다.
    - [x] 모든 상품의 정보를 가져와 보여준다.
    - [x] 상품을 추가할 수 있다.
    - [x] 상품을 수정할 수 있다.
        - [x] 존재하지 않는 상품을 수정할 때 예외처리한다.
    - [x] 상품을 삭제할 수 있다.
        - [x] 존재하지 않는 상품을 삭제할 때 예외처리한다.
- [x] 데이터베이스
    - [x] 모든 상품을 조회한다
    - [x] 상품을 생성한다
    - [x] 상품을 수정한다
    - [x] 상품을 삭제한다

## 테이블 구조

```sql
CREATE TABLE product
(
    id    bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name  varchar(255)       NOT NULL,
    image text               NOT NULL,
    price int                NOT NULL
);

CREATE TABLE user_account
(
    id bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL
);

```


