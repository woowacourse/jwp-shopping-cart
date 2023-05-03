# jwp-shopping-cart

## 1단계 기능 요구사항

- [x] 상품 목록 페이지 연동
    - [x] index.html 파일 내 TODO 주석을 참고하여 설계한 상품 정보에 맞게 코드를 변경
    - [x] index.html 파일을 이용하여 상품 목록이 노출되는 페이지를 완성
    - [x] '/' url로 접근할 경우 상품 목록 페이지를 조회
    - [x] 상품 기본 정보 : 상품 ID, 상품 이름, 상품 이미지, 상품 가격
- [x] 상품 관리 CRUD API 작성
    - [x] Create : POST - /admin
    - [x] Read : GET - /admin
    - [x] Update : POST - /admin/{id}
    - [x] Delete : DELETE - /admin/{id}
- [x] 관리자 도구 페이지 연동
    - [x] admin.html, admin.js 파일 내 TODO 주석을 참고하여 코드를 변경
    - [x] admin.html 파일과 상품 관리 CRUD API를 이용하여 상품 관리 페이지를 완성
    - [x] '/admin' url로 접근할 경우 관리자 도구 페이지를 조회

--- 

## CRUD API 설계

### 상품 삭제 : DELETE

**Request**

```http request
DELETE /products/1 HTTP/1.1
Host: localhost:8080
```

**Response**

```http request
HTTP/1.1 200
Content-Type: text/plain;charset=UTF-8

{
    "status": "OK",
    "data": [],
    "message": "success"
}
```

### 상품 추가 : POST

**Request**

```http request
POST /products HTTP/1.1
Host: localhost:8080

{
    "name" : "치킨",
    "imgUrl" : "https://barunchicken.com/wp-content/uploads/2022/07/%EA%B3%A8%EB%93%9C%EC%B9%98%ED%82%A8-2.jpg",
    "price" : 100
}
```

**Response**

```http request
HTTP/1.1 200
Content-Type: application/json

{
    "status": "OK",
    "data": {
        "id": 1
    },
    "message": "success"
}
```

### 상품 수정 : PUT

**Request**

```http request
PUT /products/1 HTTP/1.1
Host: localhost:8080

{
    "name" : "치킨",
    "imgUrl" : "https://barunchicken.com/wp-content/uploads/2022/07/%EA%B3%A8%EB%93%9C%EC%B9%98%ED%82%A8-2.jpg",
    "price" : 200
}
```

**Response**

```http request
HTTP/1.1 200
Content-Type: text/plain;charset=UTF-8


{
    "status": "OK",
    "data": [],
    "message": "success"
}
```

--- 
