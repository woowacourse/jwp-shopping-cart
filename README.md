## 기능 목록 명세

### 도메인

- Product
    - [x] 상품 이름 : 50자 이상인 경우 예외를 던진다.
    - [x] 가격 : 0 미만 10억 초과인 경우 예외를 던진다.
    - [x] 이미지 : 2000자 초과일 경우 예외를 던진다.

- User
    - [ ] email
    - [x] password
        - 비밀번호 조건
            - [x] 비밀번호는 최소 8자 이상, 최대 20자 이하이다.
            - [x] 다음으로 구성된다.
                - 최소 하나의 숫자
                - 최소 하나의 문자 : 단, 대소문자는 구별한다.
                - 최소 하나의 특수문자 : ~`!@#$%^&*()-+=
            - [x] 비밀번호 조건에 어긋나면 예외가 발생한다.

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
        -
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

- [ ] 사용자 설정 페이지 연동
    - [x] 요청 : Get /settings
    - [x] 응답 : settings.html

- [ ] 사용자 선택
    - [ ] 사용자 인증 정보
        - [ ] Header : Authorization
        - [ ] type : Basic
        - [ ] credentials : email:password (encoded with base64)

- [ ] 장바구니 기능
    - [ ] 사용자 인증
    - [ ] 장바구니 상품 추가
    - [ ] 장바구니 상품 제거
    - [ ] 장바구니 목록 조회

DB 테이블

```sql
CREATE TABLE PRODUCT
(
    id    INT           NOT NULL AUTO_INCREMENT,
    name  VARCHAR(50)   NOT NULL,
    price INT           NOT NULL,
    image VARCHAR(2000) NOT NULL,
    PRIMARY KEY (id)
);
```
