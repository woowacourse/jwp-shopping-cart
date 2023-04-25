# jwp-shopping-cart

## 기능 목록

- [ ] 상품 목록 페이지 연동
    - [ ] `/` url로 접근할 경우 상품 목록 페이지를 들어가야 한다.
    - [ ] 모든 상품의 정보를 가져와 보여준다.
- [ ] 상품 관리 CRUD API 작성
    - [ ] 상품 생성 
    - [ ] 상품 목록 조회
    - [ ] 상품 수정
    - [ ] 상품 삭제
- [ ] 관리자 도구 페이지 연동
    - [ ] `/admin` url로 접근할 경우 관리자 도구 페이지에 들어가야 한다.
    - [ ] 모든 상품의 정보를 가져와 보여준다.
    - [ ] 상품을 추가할 수 있다.
    - [ ] 상품을 수정할 수 있다.
    - [ ] 상품을 삭제할 수 있다.
- [ ] 데이터베이스
    - [x] 모든 상품을 조회한다
    - [ ] 상품을 생성한다
    - [ ] 상품을 조회한다
    - [ ] 상품을 수정한다
    - [ ] 상품을 삭제한다

  
## 테이블 구조
```sql
CREATE TABLE product (
                      id bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
                      name varchar(255) NOT NULL,
                      image text NOT NULL,
                      price int NOT NULL
);

```


