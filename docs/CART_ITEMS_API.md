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
