# 기능 구현 목록

### 상품
  - [x] 상품 생성
  - [x] 상품 리스트 조회
  - [x] 상품 정보 업데이트
  - [x] 상품 삭제

### 사용자
  - [x] 사용자 생성
  - [x] 사용자 리스트 조회
  - [x] 이메일을 통해 사용자 조회

### 장바구니
  - [x] 장바구니에 상품 추가
  - [x] 장바구니에 담긴 상품 리스트 조회
  - [x] 장바구니에 담긴 상품 제거


# 예외 처리

### 상품
- [x] 이름
  - [x] Null or Empty인 경우
  - [x] 길이가 255를 초과인 경우
- [x] 가격
  - [x] Null or Empty인 경우
  - [x] 숫자가 아닌 경우
  - [x] 천만 초과인 경우
  - [x] 0 이하인 경우
- [x] 이미지 URL
  - [x] Null or Empty인 경우
  - [x] 길이가 255를 초과인 경우

### 사용자
- [x] 이메일
  - [x] Null or Empty인 경우
  - [x] 길이가 30자를 초과인 경우
  - [x] 이메일 형식이 아닌 경우
- [x] 비밀번호
  - [x] Null or Empty인 경우
  - [x] 길이가 5자미만, 30자 초과인 경우

# DDL
```sql
CREATE TABLE PRODUCT
(
    ID          INT     UNSIGNED    NOT NULL AUTO_INCREMENT,
    NAME        VARCHAR(255)        NOT NULL,
    IMAGE_URL   VARCHAR(255)        NOT NULL,
    PRICE       INT                 NOT NULL,
    PRIMARY KEY (ID)
);
```

```sql
CREATE TABLE IF NOT EXISTS MEMBER
(
    ID          INT        UNSIGNED NOT NULL AUTO_INCREMENT,
    EMAIL       VARCHAR(30)        NOT NULL UNIQUE,,
    PASSWORD    VARCHAR(30)        NOT NULL,
    PRIMARY KEY (ID)
);
```

```sql
CREATE TABLE IF NOT EXISTS CART_PRODUCT
(
  ID          INT        UNSIGNED NOT NULL AUTO_INCREMENT,
  PRODUCT_ID  INT        UNSIGNED NOT NULL,
  MEMBER_ID   INT        UNSIGNED NOT NULL,
  PRIMARY KEY (ID),
  FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (ID) ON DELETE CASCADE,
  FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER (ID) ON DELETE CASCADE
  );
```

# API 설계



| url                 | method | desc                                  |
|---------------------|--------|---------------------------------------|
| /                   | GET    | 메인 페이지를 가져온다(상품 리스트)                  |
| /admin              | GET    | 관리자 페이지를 가져온다        (상품 리스트)         |
| /cart               | GET    | 장바구니 페이지를 가져온다(인증된 멤버의 장바구니 안 상품 리스트) |
| /settings           | GET    | 세팅 페이지를 가져온다(멤버 리스트)                  |
| /products           | GET    | 상품 리스트를 가져온다                          |
| /products           | POST   | 상품을 추가한다                              |
| /products/{id}      | PATCH  | 상품 정보를 업데이트한다                         |
| /products/{id}      | DELETE | 상품을 삭제한다                              |
| /members            | GET    | 멤버 리스트를 가져온다                          |
| /members            | POST   | 멤버를 추가한다                              |
| /cart-products      | GET    | 인증된 멤버의 장바구니 안 상품 리스트를 가져온다           |
| /cart-products      | POST   | 인증된 멤버의 장바구니 안 상품을 추가한다               |
| /cart-products/{id} | DELETE | 인증된 멤버의 장바구니 안 상품을 삭제한다               |



# 페이지 정보

### 메인 페이지
- [x] 상품 리스트 출력
- [x] 상품을 장바구니에 추가

### 관리자 페이지
- [x] 상품 리스트 출력
- [x] 상품 생성
- [x] 상품 정보 업데이트
- [x] 상품 삭제

### 세팅 페이지
- [x] 사용자 리스트 출력
- [x] 사용자 선택

### 장바구니 페이지
- [x] 장바구니에 담긴 상품 리스트 출력
- [x] 장바구니에 담긴 상품 삭제