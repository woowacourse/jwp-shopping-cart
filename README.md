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
    - [x] 이미지가 null일 경우 default image를 보여준다.

---

## 🛠️ 설계

### DB

Product

| column | type        |                    |
|--------|-------------|--------------------|
| id     | BIGINT      | PK, AUTO_INCREMENT |
| name   | VARCHAR(64) |                    |
| price  | INT         |                    |
| image  | TEXT        | NULLABLE           |

### API

- Create
    - POST /product
    - Request Body

        ```json
        {
          "name":"",
          "price":0,
          "image":""
        }
        ```

- Update
    - PUT /product/:productId
    - Request Body

        ```json
        {
          "name":"",
          "price":0,
          "image":""
        }
        ```

- Delete
    - DELETE /product/:productId

---

# 2단계

## 🎯 기능 목록

- [x]  테이블 생성
    - [x]  사용자 테이블
    - [x]  장바구니 테이블
- [x]  사용자 기능 구현
    - [x]  사용자 목록 표시
- [x]  사용자 설정 페이지 연동
    - [x]  API 연동
- [ ]  장바구니 기능 구현
    - [ ]  인증 기능
    - [ ]  물건 추가
    - [ ]  물건 삭제
    - [ ]  사용자별 장바구니 물건 표시
- [ ]  장바구니 페이지 연동
    - [ ]  CRD API 연동

- [x] 사용자 검증
    - [x] 이메일의 길이는 1자 이상 32자 이하이다.
    - [x] 이메일의 형식을 만족해야 한다. (xx@xxx.xx)
    - [x] 비밀번호는 1자 이상 32자 이하이다.

## 🛠️ 설계

### DB

- user

| column   | type        |                    |
|----------|-------------|--------------------|
| id       | BIGINT      | PK, AUTO_INCREMENT |
| email    | VARCHAR(32) |                    |
| password | VARCHAR(32) |                    |

- cart

| column     | type   |                    |
|------------|--------|--------------------|
| id         | BIGINT | PK, AUTO_INCREMENT |
| user_id    | BIGINT |                    |
| product_id | BIGINT |                    |

### API

- User
    - ~~Create~~
        - POST /user
        - Request Body

            ```json
            {
            	"email":"",
            	"password":""
            }
            ```

    - Read
        - GET /users
        - Response Body

            ```json
            {
            	"users": [
            		{
            			"id":0,
            			"email":"",
            			"password":""
            		},
            		...
            	]
            }
            ```

    - ~~Update~~
    - ~~Delete~~
- Cart
    - Create
        - POST /cart/{userId}
        - Request Body

            ```json
            {
            	"user_id":0,
            	"product_id":0
            }
            ```

    - Read
        - GET /cart/{userId}
        - Request Body

            ```json
            {
            	"products": [
            		{
            			"id":0,
            			"name":"",
            			"price":0,
            			"image":""
            		},
            		...
            	]
            }
            ```

    - ~~Update~~
    - Delete
        - DELETE /cart/{cartId}
