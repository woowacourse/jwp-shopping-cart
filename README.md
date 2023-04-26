# jwp-shopping-cart

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
