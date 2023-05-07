# 상품 관리 API

## 상품 조회 - `GET` `/products`

### 요청

```http request
GET /products HTTP/1.1
```

### 응답

예시

```json
{
  "status": 200,
  "success": true,
  "data": {
    "products": [
      {
        "id": 1,
        "name": "상품명 테스트1",
        "imgUrl": "https://cdn.pixabay.com/photo/2014/06/03/19/38/test-361512_960_720.jpg",
        "price": 100
      },
      {
        "id": 2,
        "name": "상품명 테스트2",
        "imgUrl": "https://t1.daumcdn.net/cartoon/7520d517fbe0271cdceb64e57ff1b26aa2abb680",
        "price": 101
      },
      {
        "id": 3,
        "name": "로제 파스타",
        "imgUrl": "https://avatars.githubusercontent.com/u/61582017?v=4",
        "price": 23000
      }
    ]
  }
}
```

## 상품 추가  `POST` `/products`

## 요청

```http request
POST /products HTTP/1.1
Content-Type: application/json
```

예시
```json
{
    "name": "로제 파스타",
    "price": 23000,
    "imgUrl": "https://avatars.githubusercontent.com/u/61582017?v=4"
}
```

## 응답


예시

```json
{
    "status": 201,
    "success": true
}
```

## 상품 수정 PUT `/products/{id}`

## 요청

```http request
PUT /products/2 HTTP/1.1
Content-Type: application/json
```

```json
{
  "name": "이름",
  "price": 10000,
  "imgUrl": "dfowf"
}
```

## 응답

예시

```json
{
    "status": 200,
    "success": true
}
```

# 상품 삭제 DELETE `/products/{id}`

## 요청

```http request
DELETE /products/1 HTTP/1.1
```


## 응답

예시
```json
{
    "status": 200,
    "success": true
}
```