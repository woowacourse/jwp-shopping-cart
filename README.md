# jwp-shopping-cart

## 기능 목록

### 기능

- [x] 상품 service
    - [x] 상품 생성
    - [x] 상품 목록 조회
    - [x] 상품 수정
    - [x] 상품 삭제

- [x] 장바구니 service
    - [x] 장바구니에 상품 추가
    - [x] 장바구니에 담긴 상품 제거
    - [x] 장바구니 목록 조회

- [x] 사용자 service
    - [x] 저장된 사용자 전체를 조회

- [x] ArgumentResolver
    - [x] Authorization 헤더가 있는지 검증
    - [x] Basic 타입인지 검증
    - [x] memberId 변환

### API

- [x] 관리자 페이지 (상품 반환)
    - `GET /admin` Product 리스트 반환
        - [x] 상품 수정
            - `PUT /product`입력한 id와 일치하는 Product 수정
        - [x] 상품 삭제
            - `DELETE  product/{id}` 입력한 id를 가진 Product 삭제
        - [x] 상품 추가
            - `POST /product` 입력한 상품을 추가

- [x] 상품 목록 페이지
    - `/` 상품 리스트 조회
    - `POST /cart/items`장비구니 추가

- [x] 장바구니 페이지
    - `DELETE /cart/item/{id}` 장바구니에 상품 삭제
    - `POST /cart/item` 사용자의 따른 상품 조회

### DB

````
CREATE TABLE IF NOT EXISTS PRODUCT
(
    id    INT           NOT NULL AUTO_INCREMENT,
    name  VARCHAR(200)  NOT NULL,
    image VARCHAR(2047) NOT NULL,
    price BIGINT        NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS MEMBER
(
    id       INT          NOT NULL AUTO_INCREMENT,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS CART_PRODUCT
(
    id        INT NOT NULL AUTO_INCREMENT,
    memberId  INT NOT NULL,
    productId INT NOT NULL,
    FOREIGN KEY (memberId) REFERENCES MEMBER (id) ON DELETE CASCADE,
    FOREIGN KEY (productId) REFERENCES PRODUCT (id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);
````
