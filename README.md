# jwp-hopping-cart

# 1단계

## 🎯 기능 목록

- [x]  상품 목록 페이지 연동
    - [x]  상품 목록 표시
- [x]  상품 관리 CRUD API
    - [x]  Create
    - [x]  Read
    - [x]  Update
    - [x]  Delete
- [x]  관리자 도구 페이지 연동
    - [x]  `/admin` url로 관리자 도구 페이지 조회
    - [x]  상품 관리 CRUD API 연동
- [x] 상품
    - [x] 상품의 이름의 길이는 1자 이상 64자 이하이다.
    - [x] 상품은 최대 10_000_000원 이다.
    - [x] 상품의 이미지는 null일 수 있다.
    - [x] 상품 이미지 주소의 최대 길이는 2048이다.
    - [x] 이미지가 null일 경우 default image를 보여준다
- [x] 사용자 기능 구현
- [x] 사용자 설정 페이지 연동
    - [x] 사용자 리스트 출력
    - [x] 사용자 선택 및 로그인
- [x] 장바구니 기능 구현
- [x] 장바구니 페이지 연동

---

## 🛠️ 설계

### DB

product

| column    | type        |                    |
|-----------|-------------|--------------------|
| id        | BIGINT      | PK, AUTO_INCREMENT |
| name      | VARCHAR(64) |                    |
| price     | INT         |                    |
| image_url | TEXT        | NULLABLE           |

member

| column    | type         |                    |
|-----------|--------------|--------------------|
| id        | BIGINT       | PK, AUTO_INCREMENT |
| email     | VARCHAR(255) | UNIQUE             |
| password  | VARCHAR(255) |                    |
| name      | VARCHAR(10)  |                    |

### API

#### /api/product

- Create (상품 생성)
    - POST /api/product
    - Request Body

        ```json
        {
          "name":"",
          "price":0,
          "imageUrl":""
        }
        ```

- Read (상품 조회)
    - GET /api/product
    - Response Body

        ```json
        {
          "products": [
            {
              "id":0,
              "name":"",
              "price":0,
              "imageUrl":""
            }
          ]
        }
        ```

- Update (상품 갱신)
    - PUT /api/product/:productId
    - Request Body

        ```json
        {
          "name":"",
          "price":0,
          "imageUrl":""
        }
        ```

- Delete (상품 삭제)
    - DELETE /api/product/:productId

#### /api/cart

- 장바구니 담기
    - POST /api/cart/:productId
    - Request Header
        - Authorization: BASIC email:password

- 장바구니 조회
    - GET /api/cart
    - Request Header
        - Authorization: BASIC email:password
    - Response Body
      ```json
      {
          "products": [
              {
                  "id": 0,
                  "name": "",
                  "price": 0,
                  "imageUrl": ""
              }
          ]
      }
      ```

- 장바구니 삭제
    - DELETE /api/cart/:productId
    - Request Header
        - Authorization: BASIC email:password
