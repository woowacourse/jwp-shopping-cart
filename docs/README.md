# jwp-shopping-cart

# 👨‍🍳 기능 목록

## 상품 목록 페이지 연동

- [x] 사용자로부터 `/`에 대해 GET 요청을 받으면 index.html 페이지를 반환한다.
- [x] 상품에 대한 데이터를 담을 클래스를 만든다.
    ```
  GET / HTTP/1.1
  ```

## 관리자 도구 페이지 연동

- [x] 사용자로부터 `/admin`에 대해 GET 요청을 받으면 admin.html 페이지를 반환한다.
    ```
  GET /admin HTTP/1.1
  ```

### 상품 관리 CRUD API 작성

- [x] 사용자로부터 GET 요청을 받으면 DB에서 상품 목록을 불러와 반환한다.
    ```
  GET /admin HTTP/1.1
  ```
- [x] 사용자로부터 POST 요청을 받으면 새 상품을 DB에 저장한다.
    ```
  POST /admin/products HTTP/1.1
  ```
- [x] 사용자로부터 PUT 요청을 받으면 상품의 정보를 수정하여 DB에 저장한다.
    ```
  PUT /admin/products/{product_id} HTTP/1.1
  ```
- [x] 사용자로부터 DELETE 요청을 받으면 상품을 DB에서 제거한다.
    ```
  DELETE /admin/products/{product_id} HTTP/1.1
  ```

## 테스트 코드 작성

- [x] DAO CRUD 테스트
- [ ] 컨트롤러 CRUD 테스트