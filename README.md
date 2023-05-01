# 장바구니 미션

## 개요

- Spring Web MVC를 이용하여 쇼핑몰의 상품 관리 기능 구현
    - 상품 생성, 상품 목록 조회, 상품 수정, 상품 삭제
    - 사용자 생성, 사용자 목록 조회, 사용자 선택, 사용자 삭제
    - 장바구니 상품 추가, 장바구니 목록 조회, 장바구니 상품 삭제

- 상품 관리 페이지를 Thymeleaf를 이용하여 랜더링
    - `/` : 상품 목록 페이지
    - `/admin` : 관리자 도구 페이지
    - `/settings` : 사용자 목록 페이지
    - `/cart` : 장바구니 목록 페이지

- 인증 기능 구현
    - Basic Auth

## API Docs

```http
POST /products
```

### Request Body

| Parameter     | Type         | Description          |
|:--------------|:-------------|:---------------------|
| `name`        | `string`     | **Required**. 상품명    |
| `imageUrl`    | `string`     | **Required**. 이미지 경로 |
| `price`       | `integer`    | **Required**. 상품 가격  |
| `description` | `string`     | **Required**. 상품 설명  |
| `categoryIds` | `List<Long>` | 카테고리 ID 목록           |

### Response

```http
Location: /products/{id}
```

---

```http
GET /products
```

### Response

```javascript
{
    [
        {
            "id": 1,
            "name": "치킨",
            "imageUrl": "/chicken.png",
            "price": 15000,
            "description": "맛있는 치킨",
            "categoryResponseDtos": [
                {
                    "id": 2,
                    "name": "한식"
                },
                {
                    "id": 6,
                    "name": "치킨"
                }
            ]
        },
        {
            "id": 2,
            "name": "피자",
            "imageUrl": "/pizza.png",
            "price": 15000,
            "description": "맛있는 피자",
            "categoryResponseDtos": [
                {
                    "id": 2,
                    "name": "한식"
                },
                {
                    "id": 7,
                    "name": "분식"
                }
            ]
        }
    ]
}
```

---

```http
PUT /products/{id}
```

### Path Parameter

| Parameter | Description         |
|:----------|:--------------------|
| `id`      | **Required**. 상품 ID |

### Request Body

| Parameter     | Type         | Description          |
|:--------------|:-------------|:---------------------|
| `name`        | `string`     | **Required**. 상품명    |
| `imageUrl`    | `string`     | **Required**. 이미지 경로 |
| `price`       | `integer`    | **Required**. 상품 가격  |
| `description` | `string`     | **Required**. 상품 설명  |
| `categoryIds` | `List<Long>` | 카테고리 ID 목록           |

---

```http
DELETE /products/{id}
```

### Path Parameter

| Parameter | Description         |
|:----------|:--------------------|
| `id`      | **Required**. 상품 ID |

---

```http
POST /carts?productId={productId}
```

### Request Header

```http
Authorization: Basic {auth}
```

### Request Param

| Parameter   | Description         |
|:------------|:--------------------|
| `productId` | **Required**. 상품 ID |

### Response

```http
Location: /carts/{id}
```

--- 

```http
GET /carts
```

### Request Header

```http
Authorization: Basic {auth}
```

### Response

```javascript
{
    [
        {
            "id": 1,
            "productId": 1,
            "name": "치킨",
            "imageUrl": "/chicken.png",
            "price": 15000,
            "description": "맛있는 치킨",
        },
        {
            "id": 2,
            "productId": 2,
            "name": "피자",
            "imageUrl": "/pizza.png",
            "price": 15000,
            "description": "맛있는 피자",
        }
    ]
}
```

---

```http
DELETE /carts/{id}
```

### Request Header

```http
Authorization: Basic {auth}
```

### Path Parameter

| Parameter | Description           |
|:----------|:----------------------|
| `id`      | **Required**. 장바구니 ID |

---

## 기능 목록

### DB

```
src/main/resources/data.sql
```

### Service

- 상품
    - 생성, 조회, 수정, 삭제
- 카테고리
    - 조회
- 멤버
    - 조회
- 카트
    - 생성, 조회, 삭제

### DAO

- 상품
    - 생성, 조회, 수정, 삭제
- 카테고리
    - 조회
- 상품카테고리
    - 생성, 조회, 삭제
- 멤버
    - 조회
- 카트
    - 생성, 조회, 삭제
