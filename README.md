# jwp-shopping-cart

# 1단계

## 🎯 기능 목록

- [ ]  상품 목록 페이지 연동
    - [ ]  상품 목록 표시
- [ ]  상품 관리 CRUD API
    - [x]  Create
    - [x]  Read
    - [ ]  Update
    - [ ]  Delete
- [ ]  관리자 도구 페이지 연동
    - [x]  `/admin` url로 관리자 도구 페이지 조회
    - [ ]  상품 관리 CRUD API 연동
- [x] 상품
    - [x] 상품의 이름의 길이는 1자 이상 64자 이하이다.
    - [x] 상품은 최대 10_000_000원 이다.
    - [x] 상품의 이미지는 null일 수 있다.
    - [x] 이미지가 null일 경우 default image를 보여준다.

---

## 🛠️ 설계

### DB

Product

| column | type         |                    |
|--------|--------------|--------------------|
| id     | BIGINT       | PK, AUTO_INCREMENT |
| name   | VARCHAR(64)  |                    |
| price  | INT          |                    |
| image  | VARCHAR(256) | NULLABLE           |

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

- Read
    - GET /product
    - Response Body

        ```json
        {
        	"products": [
        				{
        			"id":0,
        			"name":"",
        			"price":0,
        			"image":""
        			},
        			{..}
        	..
        	]
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
