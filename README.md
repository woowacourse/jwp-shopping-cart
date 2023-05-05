# 기능 구현 목록

### 상품
  - [x] 상품 생성
  - [x] 상품 리스트 조회
  - [x] 상품 정보 업데이트
  - [x] 상품 삭제

### 사용자
  - [x] 사용자 생성
  - [x] 사용자 리스트 조회

### 장바구니
  - [ ] 장바구니에 상품 추가
  - [ ] 장바구니에 담긴 상품 조회
  - [ ] 장바구니에 담긴 상품 제거


# 예외 처리
- [x] 상품명
  - [x] Null or Empty인 경우
  - [x] 길이가 255를 초과하는 벗어난 경우
- [x] 가격
  - [x] Null or Empty인 경우
  - [x] 숫자가 아닌 경우
  - [x] 천만 초과인 경우
  - [x] 0 이하인 경우
- [x] URL
  - [x] Null or Empty인 경우
  - [x] 길이가 255를 초과하는 벗어난 경우



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
    EMAIL       VARCHAR(255)        NOT NULL,
    PASSWORD    VARCHAR(255)        NOT NULL,
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
- [ ] 상품을 장바구니에 추가

### 관리자 페이지
- [x] 상품 리스트 출력
- [x] 상품 생성
- [x] 상품 정보 업데이트
- [x] 상품 삭제

### 세팅 페이지
- [x] 사용자 리스트 출력
- [x] 사용자 선택

### 장바구니 페이지
- [ ] 장바구니에 담긴 상품 리스트 출력
- [ ] 장바구니에 담긴 상품 삭제