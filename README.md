# 기능 구현 목록

- [x] 예외 처리
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

- [x] 상품 목록 페이지
    - [x] 상품 리스트 조회
    - [ ] 장바구니에 상품 추가

- [x] 관리자 페이지
    - [x] 상품 객체 생성
    - [x] 상품 리스트 조회
    - [x] 상품 객체 업데이트
    - [x] 상품 객체 삭제

- [ ] 세팅 페이지
  - [ ] 모든 사용자 정보 조회(이메일, 패스워드)
  - [ ] 사용자 선택

- [ ] 장바구니 페이지
  - [ ] 장바구니에 담긴 상품 제거
  - [ ] 장바구니에 담긴 상품 조회

- [x] DB 연동
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
