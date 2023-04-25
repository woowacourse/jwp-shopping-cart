# jwp-shopping-cart

# 1단계
##  🎯 기능 목록

- [ ]  상품 목록 페이지 연동
    - [ ]  상품 목록 표시
- [ ]  상품 관리 CRUD API
    - [ ]  Create
    - [ ]  Read
    - [ ]  Update
    - [ ]  Delete
- [ ]  관리자 도구 페이지 연동
    - [ ]  `/admin` url로 관리자 도구 페이지 조회
    - [ ]  상품 관리 CRUD API 연동

---

## 🛠️ 설계

### DB

Product

| column | type |  |
| --- | --- | --- |
| id | BIGINT | PK, AUTO_INCREMENT |
| name | VARCHAR(64) |  |
| price | INT |  |
| image | VARCHAR(256) |  |

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
