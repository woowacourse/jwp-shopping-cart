# 기능 구현 목록

## step1
- [x] 예외 처리
  - [x] 상품명
    - [x] Null or Empty인 경우
    - [x] 길이가 1~255의 범위를 벗어난 경우
  - [x] 가격
    - [x] Null or Empty인 경우
    - [x] 숫자가 아닌 경우
    - [x] 천만 초과인 경우
    - [x] 0 이하인 경우
  - [x] URL
    - [x] Null or Empty인 경우
    - [x] 길이가 1~255의 범위를 벗어난 경우

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


## step2
- [x] /settings url로 접근할 경우 모든 사용자의 정보를 가져오는 기능

- [ ] 장바구니
  - [x] 장바구니 담기 선택 시, 사용자 인증 및 장바구니 저장
  - [ ] 장바구니에 담긴 상품 제거
  - [x] /cart로 이동 시, 해당 계정의 장바구니 목록 조회

- [ ] 관리자 페이지에서 상품 삭제 시 장바구니에 있는 해당 상품 정보도 같이 삭제
