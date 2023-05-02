# jwp-shopping-cart

---

## API 명세서

[바로가기](docs/API.md)

## 기능 구현 목록

- [x] 상품 목록 페이지 연동
    - [x] `/` URL로 접근할 경우 상품 목록 페이지를 조회할 수 있어야 한다.
    - [x] Controller 이용해서 페이지 연동하기
- [x] 상품 관리 CRUD API 작성
    - [x] 상품 기본 정보 DTO 만들기
    - [x] 상품 생성 API
    - [x] 상품 목록 조회 API
    - [x] 상품 수정 API
    - [x] 상품 삭제 API
- [ ] 사용자 관리 페이지
  - [ ] 모든 사용자 정보 조회 기능
  - [ ] 사용자 설정 페이지 연동
- [ ] 장바구니 기능 API
  - [ ] 사용자 정보를 Header의 `Authorization` 필드에서 얻는다.
    - 인증 방식은 Basic 인증이다.
    - credentials: `email:password` 형식이다. base64로 디코딩하여 사용한다.
  - [ ] 장바구니 상품 추가 API
    - 상품 아이디로 장바구니 아이템을 추가할 수 있다.
  - [ ] 장바구니 상품 제거 API
    - 추가된 장바구니 아이템 id로 삭제할 수 있다. 
    - [ ] 장바구니에 없는 아이템일 경우 예외가 발생한다.
  - [ ] 장바구니 목록 조회 API
- [x] 관리자 도구 페이지 연동
- [x] 프론트 뷰와 연결


## 도메인

- [x] 상품 도메인 모델 정의하기
  - [x] 상품의 가격은 음수가 될 수 없다.
  - [x] 이름은 공백이 될 수 없다.
- [x] 사용자 모델 정의하기
  - [x] email, password 를 갖는다.
  - [x] **장바구니를 갖는다.**
- [x] 장바구니 모델 정의하기
  - [x] 장바구니 아이템을 가질 수 있다.
  - [x] 같은 상품을 여러 개 가질 수 있다.
- [x] 장바구니 아이템 모델 정의하기
  - [x] 식별자를 가지고 있어야한다.
  - [x] 상품을 가지고 있어야한다.

## 데이터베이스

### DDL

#### product: 상품 정보 저장 테이블
```sql
CREATE TABLE IF NOT EXISTS products
(
  id      BIGINT AUTO_INCREMENT PRIMARY KEY,
  name    VARCHAR(45) NOT NULL,
  price   INT         NOT NULL,
  img_url CLOB(10K) NOT NULL
);
```

#### user: 회원 정보 저장 테이블

```sql
CREATE TABLE IF NOT EXISTS users(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
  );
```

#### cart_product: 장바구니 정보 저장 테이블
```sql
CREATE TABLE IF NOT EXISTS cart_products
(
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  product_id BIGINT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (product_id) REFERENCES products(id)
);
```
