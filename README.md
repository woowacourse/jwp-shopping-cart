# 기능 구현 목록


- [x] 상품 목록 페이지
    - [x] 상품 리스트 조회

- [ ] 관리자 페이지
    - [ ] 상품 객체 생성
    - [ ] 상품 리스트 조회
    - [ ] 상품 객체 업데이트
    - [ ] 상품 객체 삭제

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
