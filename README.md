## 기능 목록 명세

- 엔티티
  - 상품
    - id
    - 이름
    - 이미지
    - 가격

--- 

- [ ] 상품 목록 페이지 연동
  - [ ] 요청 : Get /
  - [ ] 응답 : index.html
    - [ ] 모델 추가 : List\<Product>
- [ ] 상품 관리 CRUD API 작성
  /product
  - [ ] Create
    - [ ] 요청 : Post
      - [ ] 상품 이름, 가격, 이미지
    - [ ] 응답 : 200
    - [ ] 예외 : 
      - [ ] 입력값이 비어있는 경우
      - [ ] 입력값이 아래의 조건을 만족하지 않은 경우
        - [ ] 상품 이름 : 50자 미만
        - [ ] 가격 : 0 이상 10억 이하
  - [ ] Read
    - [ ] 요청 : Get
    - [ ] 응답 : 200, List\<Product>
  - [ ] Update
    - [ ] 요청 : put || patch
      - id 전송
      -  [ ] 상품 이름, 가격, 이미지
    - [ ] 응답 : 200 /admin redirect
      - [ ] 모델 추가 : List\<Product>
    - [ ] 예외 :
      - [ ] 입력값이 비어있는 경우
      - [ ] 입력값이 아래의 조건을 만족하지 않은 경우
        - [ ] 상품 이름 : 50자 미만
        - [ ] 가격 : 0 이상 10억 이하
  - [ ] Delete
    - [ ] 요청 : delete
    - id 전송
    - [ ] 응답 : 200 /admin redirect
      - [ ] 모델 추가 : List\<Product>
- [ ] 관리자 도구 페이지 연동
  - [ ] 요청 : Get /admin
  - [ ] 응답 : admin.html
    - [ ] 모델 추가 : List\<Product>

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