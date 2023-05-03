## 기능 목록 명세

### 도메인

- Product
    - [x] 상품 이름 : 50자 이상인 경우 예외를 던진다.
    - [x] 가격 : 0 미만 10억 초과인 경우 예외를 던진다.
    - [x] 이미지 : 2000자 초과일 경우 예외를 던진다.

- 엔티티
    - 상품
        - id
        - 이름
        - 이미지
        - 가격

--- 


- [x] 상품 목록 페이지 연동
    - [x] 요청 : Get /
    - [x] 응답 : index.html
        - [x] 모델 추가 : List\<ResponseProductDto>
- [x] 상품 관리 CRUD API 작성
  /product
    - [x] Create
        - [x] 요청 : Post
            - [x] 상품 이름, 가격, 이미지
        - [x] 응답 : 201
        - [x] 예외 :
            - [x] 입력값이 비어있는 경우
            - [x] 입력값이 도메인의 조건을 만족하지 않은 경우
    - [x] Update
        - [x] 요청 : put
            - id 전송
            -  [x] 상품 이름, 가격, 이미지
        - [x] 응답 : 200 /admin
        - [x] 예외 :
            - [x] 입력값이 비어있는 경우
            - [x] 입력값이 도메인의 조건을 만족하지 않은 경우
            - [x] DB 관련 예외가 생긴 경우
    - [x] Delete
        - [x] 요청 : delete
        - id 전송
        - [x] 응답 : 200 /admin
        - [x] 예외 :
            - [x] DB 관련 예외가 생긴 경우
- [x] 관리자 도구 페이지 연동
    - [x] 요청 : Get /admin
    - [x] 응답 : admin.html
        - [x] 모델 추가 : List\<ResponseProductDto>

---

## 사용자 인증

- [x] 사용자 설정 페이지 연동
  - [x] 요청 : Get /settings
  - [x] 응답 : settings.html
    - [x] 모델 추가 : 선택 가능한 사용자 정보
    
  - [x] 사용자 선택 시 사용자 정보를 쿠키에 저장한다.

## 장바구니 기능 구현

- [x] 장바구니 페이지 연동
  - [x] 요청 : Get /cart
  - [x] 응답 : cart.html
    - [x] 요청한 사용자를 인증한다.
    - [x] 사용자가 접근할 수 있는 권한의 상품 목록을 반환한다.

- [ ] 사용자 인증 처리
  - [ ] 장바구니 관련 요청이 있을 때마다 사용자 확인

- [ ] 장바구니에 상품 추가
- [ ] 장바구니에 담긴 상품 제거
- [ ] 장바구니 목록 조회

DB 테이블

Product 테이블
```sql
CREATE TABLE PRODUCT (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    price INT NOT NULL,
    image VARCHAR(2000) NOT NULL,
    PRIMARY KEY (id)
);
```

Member 테이블
```sql
CREATE TABLE MEMBER (
                        id INT NOT NULL AUTO_INCREMENT,
                        email VARCHAR(50) NOT NULL,
                        password VARCHAR(50) NOT NULL,
                        PRIMARY KEY (id)
);
```

CART 테이블
```sql
CREATE TABLE CART (
    id INT NOT NULL AUTO_INCREMENT,
    product_id INT NOT NULL,
    member_id INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT `winners_ibfk_1` FOREIGN KEY (product_id) REFERENCES PRODUCT (id) ON DELETE CASCADE,
    CONSTRAINT `winners_ibfk_2` FOREIGN KEY (member_id) REFERENCES MEMBER (id) ON DELETE CASCADE
);
```
