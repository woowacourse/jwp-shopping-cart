## 기능 목록 명세

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
    - [x] 모델 추가 : List\<Product>
- [ ] 상품 관리 CRUD API 작성
  /product
  - [x] Create
    - [x] 요청 : Post
      - [x] 상품 이름, 가격, 이미지
    - [x] 응답 : 200
    - [ ] 예외 :
      - [ ] 입력값이 비어있는 경우
      - [ ] 입력값이 아래의 조건을 만족하지 않은 경우
        - [ ] 상품 이름 : 50자 미만
        - [ ] 가격 : 0 이상 10억 이하
      - [ ] DB 저장이 안된 경우
  - [x] Update
    - [x] 요청 : put
      - id 전송
      -  [x] 상품 이름, 가격, 이미지
    - [x] 응답 : 200 /admin
    - [ ] 예외 :
      - [ ] 입력값이 비어있는 경우
      - [ ] 입력값이 아래의 조건을 만족하지 않은 경우
        - [ ] 상품 이름 : 50자 미만
        - [ ] 가격 : 0 이상 10억 이하
  - [ ] Delete
    - [ ] 요청 : delete
    - id 전송
    - [ ] 응답 : 200 /admin
- [x] 관리자 도구 페이지 연동
  - [x] 요청 : Get /admin
  - [x] 응답 : admin.html
    - [x] 모델 추가 : List\<Product>

---

DB 테이블

```sql
CREATE TABLE PRODUCT (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    price INT NOT NULL,
    image VARCHAR(2000) NOT NULL,
    PRIMARY KEY (id)
);
```