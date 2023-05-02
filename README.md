# jwp-shopping-cart

## 기능 목록

- [x] 상품 목록 페이지 연동
    - [x] `/` url로 접근할 경우 상품 목록 페이지를 들어가야 한다
    - [x] 모든 상품의 정보를 가져와 보여준다
- [x] 상품 관리 CRUD API 작성
    - [x] 상품 생성 POST `/admin/products`
    - [x] 상품 목록 조회 GET
        - [x] home `/`
        - [x] admin `/admin`
    - [x] 상품 수정 PUT `/admin/products/{id}`
    - [x] 상품 삭제 DELETE `/admin/products/{id}`
- [x] 관리자 도구 페이지 연동
    - [x] `/admin` url로 접근할 경우 관리자 도구 페이지에 들어가야 한다
    - [x] 모든 상품의 정보를 가져와 보여준다
    - [x] 상품을 추가할 수 있다
    - [x] 상품을 수정할 수 있다
        - [x] 존재하지 않는 상품을 수정할 때 예외처리한다
    - [x] 상품을 삭제할 수 있다
        - [x] 존재하지 않는 상품을 삭제할 때 예외처리한다
- [ ] 사용자 기능 구현
    - [ ] 사용자는 이메일과 비밀번호 정보를 기본으로 갖는다
- [ ] 사용자 설정 페이지 연동
    - [ ] `/settings` url로 접근할 경우 모든 사용자의 정보를 확인하고 사용자를 선택할 수 있다
    - [ ] 모든 사용자를 가져와 보여준다
    - [ ] 사용자 설정 페이지에서 사용자를 선택하면, 이후 요청에 선택한 사용자의 인증 정보가 포함된다
- [ ] 장바구니 기능 구현
    - [ ] 장바구니에 상품 추가
    - [ ] 장바구니에 담긴 상품 제거
    - [ ] 장바구니 목록 조회
    - [ ] 사용자 정보는 요청 Header의 Authorization 필드를 사용해 인증 처리를 하여 얻는다
- [ ] 장바구니 페이지 연동
    - [ ] 1단계에서 구현한 상품 목록 페이지(/)에서 담기 버튼을 통해 상품을 장바구니에 추가할 수 있다
    - [ ] `/cart` url로 접근할 경우 장바구니 페이지를 조회할 수 있다
    - [ ] 장바구니 목록을 확인하고 상품을 제거할 수 있다
- [x] 데이터베이스
    - [x] 상품
        - [x] 모든 상품을 조회한다
        - [x] 상품을 생성한다
        - [x] 상품을 수정한다
        - [x] 상품을 삭제한다
    -[ ] 사용자
        - [x] 모든 사용자를 조회한다
        - [ ] 특정 사용자를 조회한다
    - [ ] 장바구니
        - [ ] 장바구니에 담긴 상품 목록을 조회한다
        - [ ] 장바구니에 상품을 추가한다
        - [ ] 장바구니에 상품을 제거한다

## 테이블 구조

```sql
CREATE TABLE product
(
    id    bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name  varchar(255)       NOT NULL,
    image text               NOT NULL,
    price int                NOT NULL
);

CREATE TABLE member
(
    id       bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    email    varchar(255)       NOT NULL,
    password varchar(255)       NOT NULL
);

```


