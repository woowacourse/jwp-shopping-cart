# jwp-shopping-cart

| <img src="https://avatars.githubusercontent.com/u/101039161?v=4" alt="" width=200> | <img src="https://avatars.githubusercontent.com/u/82203978?v=4" alt="" width=200/> |
|:----------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------:|
|                         [키아라](https://github.com/kiarakim)                         |                         [헤나](https://github.com/hyena0608)                         

- [x] 도메인
    - [x] 상품
        - [x] 상품명 : 1 이상 20 이하의 문자열
        - [x] 가격  : 0 또는 양수
        - [x] 이미지 : null일 수 없다
- [x] 테이블

```h2
CREATE TABLE IF NOT EXISTS products
(
    id    BIGINT      NOT NULL AUTO_INCREMENT,
    name  VARCHAR(10) NOT NULL,
    price DOUBLE      NOT NULL,
    image TEXT        NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS members
(
    id       BIGINT      NOT NULL AUTO_INCREMENT,
    name     VARCHAR(30) NOT NULL,
    email    VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS carts
(
    id         BIGINT NOT NULL AUTO_INCREMENT,
    member_id  BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES members (id),
    FOREIGN KEY (product_id) REFERENCES products (id),
    UNIQUE (member_id, product_id)
);

```

- [x] 상품 목록 페이지 연동
- [x] 상품 관리 CRUD API 작성
    - [x] 상품 저장 API
    - [x] 상품 전체 조회 API
    - [x] 상품 삭제 API
    - [x] 상품 수정 API
- [x] 관리자 도구 페이지 연동

---

## 장바구니 2단계 요구사항

- 사용자 설정 페이지 연동
    - 설정 페이지 (`/settings`)
        - [x] 사용자 전체 정보 확인 기능
        - [x] 사용자 인증 정보 기능


- 장바구니 기능 구현
    - 상품 목록 페이지 (`/`)
        - [x] 장바구니 상품 추가 기능 - `Authorization`
    - 장바구니 목록 페이지 (`/carts`)
        - [x] 장바구니 목록 조회 기능 - `Authorization`
        - [x] 장바구니 상품 삭제 기능 - `Authorization`


- 장바구니 페이지 연동
    - 장바구니 상품 추가
        - [x] 상품 목록 페이지 : 상품 장바구니 추가
    - 장바구니 목록 조회
        - [x] 장바구니 상품 조회 기능
        - [x] 장바구니 상품 제거 기능
