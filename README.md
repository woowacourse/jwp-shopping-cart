# jwp-shopping-cart

| <img src="https://avatars.githubusercontent.com/u/101039161?v=4" alt="" width=200> | <img src="https://avatars.githubusercontent.com/u/82203978?v=4" alt="" width=200/> |
|:----------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------:|
|                         [키아라](https://github.com/kiarakim)                         |                         [헤나](https://github.com/hyena0608)                       |  

- [x] 도메인
    - [x] 상품
        - [x] 상품명 : 1 이상 20 이하의 문자열
        - [x] 가격  : 0 또는 양수
        - [x] 이미지 : null일 수 없다
- [x] 테이블

```sql
CREATE TABLE IF NOT EXISTS products
(
id      BIGINT      NOT NULL AUTO_INCREMENT,
name    VARCHAR(10) NOT NULL,
price   DOUBLE      NOT NULL,
image   TEXT        NOT NULL,
PRIMARY KEY(id)
);
```

- [x] 상품 목록 페이지 연동
- [x] 상품 관리 CRUD API 작성
    - [x] 상품 저장 API
      ```
      POST /products
      
      Request Body
      {
        "name": "HENA",
        "price": 10000.0,
        "image": "https://url.kr/6foxtq"
      }
      
      Response
      HttpStatus : 201
      Location : products/1
      ```
    - [x] 상품 전체 조회 API
      ```
      GET /products
      
      Response
      [
        {
            "id": 1,
            "name": "HENA",
            "price": 10000.0,
            "image": "https://url.kr/6foxtq"
        }
      ]
      HttpStatus : 200 Ok
      ```
    - [x] 상품 삭제 API
      ```
      DELETE /products/{id}
      
      Response
      HttpStatus : 204 No Content
      ```
    - [x] 상품 수정 API
      ```
      PATCH /product/{id}
      
      Request Body
      {
        "name": "HENA",
        "price": 10000.0,
        "image": "https://url.kr/6foxtq"
      }
      
      Response
      {
        "id": 2,
        "name": "HENA",
        "price": 10000.0,
        "image": "https://url.kr/6foxtq"
      }
      HttpStatus : 200 Ok
      ```
- [x] 관리자 도구 페이지 연동

## 2단계 요구사항

### - 인증
- [x] 사용자 기능 구현
    - [x] 사용자 정보(아이디, 이름, 이메일, 패스워드) 저장 기능
- [ ] 사용자 설정 페이지 연동
    - [ ] 설정 페이지(`/settings`)
      - [ ] 사용자 전체 정보 확인


### - 장바구니 기능 구현
- [ ] 장바구니 기능 구현
    - [ ] 상품 목록 페이지(`/`)
        - [ ] 장바구니 상품 추가
    - [ ] 장바구니 목록 페이지(`/cart`)
        - [ ] 장바구니 목록 조회
        - [ ] 장바구니 상품 삭제
- [ ] 장바구니 페이지 연동
  - [ ] 장바구니 상품 추가
  - [ ] 장바구니 목록 조회
    - [ ] 장바구니 상품 조회
    - [ ] 장바구니 상품 제거
