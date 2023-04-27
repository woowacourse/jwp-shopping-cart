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

- [x] 관리자 페이지
    - [x] 상품 객체 생성
    - [x] 상품 리스트 조회
    - [x] 상품 객체 업데이트
    - [x] 상품 객체 삭제

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
