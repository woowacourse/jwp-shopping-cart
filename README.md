# jwp-shopping-cart

## 기능 목록

- [x] 상품 목록 페이지 연동
    - [x] `/` url로 접근할 경우 상품 목록 페이지를 들어가야 한다.
    - [x] 모든 상품의 정보를 가져와 보여가준다.
- [x] 상품 관리 CRUD API 작성
    - [x] 전체 상품 조회 `GET /product` -> `200 OK`
    - [x] 상품 생성 `POST /products` -> `201 CREATED`
    - [x] 상품 수정 `PUT /products/{id}` -> `204 NO CONTENT`
    - [x] 상품 삭제 `DELETE /products/{id}` -> `204 NO CONTENT` 
- [x] 관리자 도구 페이지 연동
    - [x] `/admin` url로 접근할 경우 관리자 도구 페이지에 들어가야 한다.
    - [x] 모든 상품의 정보를 가져와 보여준다.
    - [x] 상품을 추가할 수 있다.
    - [x] 상품을 수정할 수 있다.
    - [x] 상품을 삭제할 수 있다.
- [x] 데이터베이스
    - [x] 모든 상품을 조회한다
    - [x] 상품을 생성한다
    - [x] 상품을 수정한다
    - [x] 상품을 삭제한다

## 테이블 구조

```sql
CREATE TABLE product
(
    id    bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name  varchar(255)       NOT NULL,
    image text               NOT NULL,
    price int                NOT NULL
);

```

## 상품 관리 API
1. `GET` 전체 상품 조회
- 요청
  ```text
  GET 127.0.0.1:8080/products
  ```
- 응답
  ```text
  200 OK
  
  [
    {
        "id": 1,
        "name": "mouse",
        "image": "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg",
        "price": 100000
    },
    {
        "id": 2,
        "name": "keyboard",
        "image": "https://i1.wp.com/blog.peoplefund.co.kr/wp-content/uploads/2020/01/진혁.jpg?fit=770%2C418&ssl=1",
        "price": 250000
    }
  ]
  ```
2. `POST` 상품 등록
- 요청
  ```text
  POST 127.0.0.1:8080/products
  Content-Type: application/json; charset=UTF-8
  
  {
    "name": "mouse",
    "image": "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg",
    "price": 500000
  }
  ```
- 응답
  ```text
  201 CREATED
  ```
  
3. `PUT` 상품 수정
- 요청
  ```text
  PUT 127.0.0.1:8080/products/1
  Content-Type: application/json; charset=UTF-8
  
  {
    "name": "keyboard",
    "image": "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg",
    "price": 1000000
  }
  ```
- 응답
  ```text
  204 NO CONTENT
  ```

4. `DELETE` 상품 삭제
- 요청
  ```text
  DELETE 127.0.0.1:8080/products/1
  ```
- 응답
  ```text
  204 NO CONTENT
  ```
