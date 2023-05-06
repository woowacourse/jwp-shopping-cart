# 장바구니 관리 REST API

|                    | method | URI                    | response status | 
|--------------------|--------|------------------------|-----------------|
| [아이템 조회](##아이템-조회) | GET    | /cartitems             | 200 OK          |
| [아이템 등록](##아이템-등록) | POST   | /cartitems/{productId} | 201 CREATED     |
| [아이템 삭제](##아이템-삭제) | DELETE | /cartitems/{id}        | 204 NO CONTENT  |

## 아이템 조회

### Request

```http request
GET /cartitems HTTP/1.1
```

### Response

```http request
HTTP/1.1 200
Content-Type: application/json

[
    {
        "id": 1,
        "productName": "치킨",
        "productImageUrl": "https://ifh.cc/g/bQpAgl.jpg",
        "productPrice": 10000
    },
    {
        "id": 2,
        "productName": "짜장면",
        "productImageUrl": "https://ifh.cc/g/bQpAgl.jpg",
        "productPrice": 10000
    }
]
```

#### body

| name            | type    | description  | required |
|-----------------|---------|--------------|----------|
| id              | Long    | 장바구니 아이템 아이디 | O        |
| productName     | String  | 상품명          | O        |
| productImageUrl | String  | 상품 이미지 URL   | O        |
| productPrice    | Integer | 상품가격         | O        |

## 아이템 등록

### Request

```http request
POST /cartitems/{productId} HTTP/1.1
Authorization: Basic {credentials}
```

#### path variable

| name      | type | description      | required |
|-----------|------|------------------|----------|
| productId | Long | 장바구니에 추가할 상품 아이디 | O        |

### Response

```http request
HTTP/1.1 201
```

## 아이템 삭제

### Request

```http request
DELETE /cartitems/{id} HTTP/1.1
Authorization: Basic {credentials}
```

#### path variable

| name | type | description      | required |
|------|------|------------------|----------|
| id   | Long | 삭제할 장바구니 아이템 아이디 | O        |

### Response

```http request
HTTP/1.1 204
```

## 응답 실패 메시지

| error message                   | response status           |
|---------------------------------|---------------------------|
| internal server error           | 500 INTERNAL_SERVER_ERROR |
| 사용자 정보가 존재하지 않습니다               | 401 UNAUTHORIZED          |
| 잘못된 사용자 정보입니다                   | 401 UNAUTHORIZED          |
| 사용자 정보와 장바구니 아이템 정보가 일치하지 않습니다. | 403 FORBIDDEN             |
| 이미 장바구니에 담은 상품입니다               | 400 BAD REQUEST           |
| 존재하지 않는 id입니다. value: {id}      | 400 BAD REQUEST           |
