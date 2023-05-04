## 기능 목록 명세

### 도메인

- Product
  - [x] 상품 이름 : 50자 이상인 경우 예외를 던진다.
  - [x] 가격 : 0 미만 10억 초과인 경우 예외를 던진다.
  - [x] 이미지 : 2000자 초과일 경우 예외를 던진다.
- User
  - [x] 이메일 : 이메일 형식이 아닐 경우 예외를 던진다.
  - [x] 비밀번호: 4자 미만일 경우 예외를 던진다.
--- 

### 페이지 

- [x] 상품 목록 페이지 연동
  - [x] 요청 : GET `/products`
  - [x] 응답 : index.html
    - [x] 모델 추가 : List\<ProductResponse>

- [x] 관리자 도구 페이지 연동
  - [x] 요청 : GET `/admin`
  - [x] 응답 : admin.html
    - [x] 모델 추가 : List\<ProductResponse>

- [x] 설정 페이지 연동
  - [x] 요청 : GET `/settings`
  - [x] 응답 : settings.html
    - [x] 모델 추가 : List\<MemberResponse>

- [x] 장바구니 목록 페이지 연동
  - [x] 요청 : GET `/cart`
  - [x] 응답 : cart.html

----
### API
  - 상품 생성
    - URI : `/products`
      - [x] 요청 : post
        - body : 상품 이름, 가격, 이미지
      - [x] 응답 : 201
      - [x] 예외
        - 400 : 입력값이 비어있거나, 도메인 조건을 만족하지 않은 경우
  - 상품 업데이트
    - URI : `/products/{id}`
      - [x] 요청 : put
        - path variable : id 전송
        - body : 상품 이름, 가격, 이미지
      - [x] 응답 : 200
      - [x] 예외
        - 400 : 입력값이 비어있거나, 도메인 조건을 만족하지 않은 경우
        - 404 : 업데이트하려는 상품이 없는 경우
  - 상품 삭제
    - URI : `/products/{id}`
      - [x] 요청 : delete
        - path variable : id 전송
      - [x] 응답 : 202
      - [x] 예외
        - 404 : 삭제하려는 상품이 없는 경우
      
  - 장바구니 조회
    - URI : `/carts`
      - [x] 요청 : get
        - header : Basic 형식의 토큰
      - [x] 응답 : 200 List\<CartResponse>
      - [x] 예외 
        - 404 : 회원 정보가 없는 경우
  - 장바구니 추가
    - URI : `/carts/{productId}`
      - [x] 요청 : post
        - header : Basic 형식의 토큰 
        - path variable : 상품 id 전송
      - [x] 응답 : 201
      - [x] 예외
        - 404 : 회원 정보가 없는 경우
        - 404 : 이미 장바구니에 담은 상품일 경우
  - 장바구니 삭제
    - URI : `/carts/{cartId}`
      - [x] 요청 : delete
        - header : Basic 형식의 토큰
        - path variable : 카트 id 전송
      - [x] 응답 : 202
      - [x] 예외
        - 404 : 회원 정보가 없는 경우
        - 404 : 존재하지 않는 상품을 삭제하려는 경우

----
#### DB Schema

```sql
CREATE TABLE PRODUCT (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    price INT NOT NULL,
    image VARCHAR(2000) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE MEMBER (
    id BIGINT NOT NULL AUTO_INCREMENT,
    email VARCHAR(20) NOT NULL,
    password VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE CART (
    id         BIGINT NOT NULL AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    member_id  BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES PRODUCT (id),
    FOREIGN KEY (member_id) REFERENCES MEMBER (id)
);
```