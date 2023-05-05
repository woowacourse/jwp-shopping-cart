# 상품 관리 REST API

|                  | method | URI            | response status | 
|------------------|--------|----------------|-----------------|
| [상품 등록](##상품-등록) | POST   | /products      | 201 CREATED     |
| [상품 수정](##상품-수정) | PUT    | /products      | 204 NO CONTENT  |
| [상품 삭제](##상품-삭제) | DELETE | /products/{id} | 204 NO CONTENT  |

## 상품 등록

### Request

```http request
POST /products HTTP/1.1
{
    "name": "치킨",
    "image-url": "https://ifh.cc/g/bQpAgl.jpg",
    "price": 10000
}
```

#### body

| name      | type    | description | required |
|-----------|---------|-------------|----------|
| name      | String  | 상품명         | O        |
| image-url | String  | 상품 이미지 URL  | O        |
| price     | Integer | 상품가격        | O        |

### Response

```http request
HTTP/1.1 201
```

## 상품 수정

### Request

```http request
PUT /products HTTP/1.1
{
    "id": 1,
    "name": "치킨",
    "image-url": "https://ifh.cc/g/bQpAgl.jpg",
    "price": 10000
}
```

#### body

| name      | type    | description | required |
|-----------|---------|-------------|----------|
| id        | Long    | 수정할 상품 아이디  | O        |
| name      | String  | 상품명         | O        |
| image-url | String  | 상품 이미지 URL  | O        |
| price     | Integer | 상품가격        | O        |

### Response

```http request
HTTP/1.1 204
```

## 상품 삭제

### Request

```http request
DELETE /products/1 HTTP/1.1
```

#### path variable

| name | type | description | required |
|------|------|-------------|----------|
| id   | Long | 삭제할 상품 아이디  | O        |

### Response

```http request
HTTP/1.1 204
```

## 응답 실패 메시지

| error message                | response status           |
|------------------------------|---------------------------|
| internal server error        | 500 INTERNAL_SERVER_ERROR |
| must not be null             | 400 BAD REQUEST           |
| 상품 이름은 빈 문자열일 수 없습니다         | 400 BAD REQUEST           |
| 상품 이름은 최소 1, 최대 30 글자입니다     | 400 BAD REQUEST           |
| 이미지 URL은 빈 문자열일 수 없습니다       | 400 BAD REQUEST           |
| 이미지 URL은 최소 1, 최대 1000 글자입니다 | 400 BAD REQUEST           |
| 유효한 상품 금액이 아닙니다              | 400 BAD REQUEST           |
| 존재하지 않는 id입니다. value: {id}   | 400 BAD REQUEST           |
