## 기능 목록 명세

### 도메인

- Product
  - [x] 상품 이름 : 50자 이상인 경우 예외를 던진다.
  - [x] 가격 : 0 미만 10억 초과인 경우 예외를 던진다.
  - [x] 이미지 : 2000자 초과일 경우 예외를 던진다.
- User
  - [x] 이메일 : 이메일 형식이 아닐 경우 예외를 던진다.
  - [x] 비밀번호: 영어와 숫자를 포함해야 한다.

- 엔티티
  - 상품
    - id
    - 이름
    - 이미지
    - 가격
  - 사용자
    - id
    - 이메일
    - 비밀번호

--- 

### 페이지 

- [x] 상품 목록 페이지 연동
  - [x] 요청 : GET `/`
  - [x] 응답 : index.html
    - [x] 모델 추가 : List\<ProductResponse>

- [x] 관리자 도구 페이지 연동
  - [x] 요청 : GET `/admin`
  - [x] 응답 : admin.html
    - [x] 모델 추가 : List\<ProductResponse>

- [x] 설정 페이지 연동
  - [x] 요청 : GET `/settings`
  - [x] 응답 : settings.html

- [ ] 장바구니 목록 페이지 연동
  - [ ] 요청 : GET `/cart`
    - [ ] header : Basic 형식의 토큰
  - [ ] 응답 : cart.html

----
### API
  - 상품 생성
  - URI : `/products`
    - [x] 요청 : post
      - [x] 상품 이름, 가격, 이미지
    - [x] 응답 : 201
    - [x] 예외 :
      - [x] 입력값이 비어있는 경우
      - [x] 입력값이 도메인의 조건을 만족하지 않은 경우
  - 상품 업데이트
  - URI : `/products/{id}`
    - [x] 요청 : put
      - id 전송
      -  [x] 상품 이름, 가격, 이미지
    - [x] 응답 : 200
    - [x] 예외 :
      - [x] 입력값이 비어있는 경우
      - [x] 입력값이 도메인의 조건을 만족하지 않은 경우
  - 상품 삭제
  - URI : `/products/{id}`
    - [x] 요청 : delete
      - id 전송
    - [x] 응답 : 202
    - [x] 예외 :
      - [x] DB 관련 예외가 생긴 경우
  - 장바구니 추가
    - URI : `/carts/{productId}`
      - [ ] 요청 : post
        - header : Basic 형식의 토큰 
        - path variable : 상품 id 전송
    - [ ] 응답 : 201
  - 장바구니 삭제
    - URI : `/carts/{cartId}`
      - [ ] 요청 : delete
        - header : Basic 형식의 토큰
        - path variable : 카트 id 전송
    - [ ] 응답 : 202

----
#### DB 테이블

```sql
CREATE TABLE PRODUCT (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    price INT NOT NULL,
    image VARCHAR(2000) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE USER (
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