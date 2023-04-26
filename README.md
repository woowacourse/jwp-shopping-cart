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
    - [x] 모델 추가 : List\<Product>
- [x] 상품 관리 CRUD API 작성
  /product
  - [x] Create
    - [x] 요청 : Post
      - [x] 상품 이름, 가격, 이미지
    - [x] 응답 : 200
    - [x] 예외 :
      - [x] 입력값이 비어있는 경우
      - [x] 입력값이 도메인의 조건을 만족하지 않은 경우
      - [x] DB 관련 예외가 생긴 경우
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
- [x] 관리자 도구 페이지 연동
  - [x] 요청 : Get /admin
  - [x] 응답 : admin.html
    - [x] 모델 추가 : List\<Product>

---
```
컨트롤러 네이밍 찾아보기 x
Dto에 response/request 추가 x
URI 컨벤션 찾기 x
DTO 양방향 의존성이 생기는 문제 해결해보기 x 
--valid의 필요성? -> 오버엔지니어링? 안전하게 가야하는지-- x

dao → key holder 사용해서 id 반환하도록 하기
service에서 insert, update return값 다시 생각해보기(지금 void)
테스트 (컨트롤러, 서비스, 다오)
  전체테스트를 먼저 해야하는 이유
  전체테스트 -> 컨트롤러 보고 해야하는가...?
```
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