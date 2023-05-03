# jwp-shopping-cart

## 기능 요구사항

### 1단계 기능 요구사항

- [x] 상품 목록 페이지 연동
    - [x] index.html 파일 내 TODO 주석을 참고하여 설계한 상품 정보에 맞게 코드를 변경
    - [x] index.html 파일을 이용하여 상품 목록이 노출되는 페이지를 완성
    - [x] '/' url로 접근할 경우 상품 목록 페이지를 조회
    - [x] 상품 기본 정보 : 상품 ID, 상품 이름, 상품 이미지, 상품 가격
- [x] 상품 관리 CRUD API 작성
    - [x] Create : POST - /products
    - [x] Read : GET - /products
    - [x] Update : PUT - /products/{id}
    - [x] Delete : DELETE - /products/{id}
- [x] 관리자 도구 페이지 연동
    - [x] admin.html, admin.js 파일 내 TODO 주석을 참고하여 코드를 변경
    - [x] admin.html 파일과 상품 관리 CRUD API를 이용하여 상품 관리 페이지를 완성
    - [x] '/admin' url로 접근할 경우 관리자 도구 페이지를 조회

---

### 2단계 기능 요구사항

- [ ] 사용자 기능 구현
    - [ ] 사용자 기본 정보 : email, password
- [ ] 사용자 설정 페이지 연동
    - [ ] settings.html, settings.js 파일 내 TODO 주석을 참고하여 설계한 사용자 정보에 맞게 코드 변경
    - [ ] settings.html 파일을 이용해서 사용자를 선택하는 기능 구현
    - [ ] `/settings`url로 접근할 경우 모든 사용자의 정보를 확인하고 사용자를 선택하는 기능 구현
    - [ ] 사용자 설정 페이지에서 사용자를 선택하면, 이후 요청에 선택한 사용자의 인증 정보 포함하는 기능 구현
- [ ] 장바구니 기능 구현
    - [ ] 장바구니 상품 추가 기능 : POST - /carts/{id}
    - [ ] 장바구니 상품 제거 기능 : DELETE - /carts/{id}
    - [ ] 장바구니 목록 조회 기능 : GET - /carts
- [ ] 장바구니 페이지 연동
    - [ ] cart.html, cart.js 파일 내 TODO 주석을 참고하여 설계한 장바구니 정보에 맞게 코드 변경
    - [ ] 장바구니 상품 추가
        - [ ] 1단계에서 구현한 상품 목록 페이지(`/`)에서 담기 버튼을 통해 상품을 장바구니에 추가하는 기능 구현
    - [ ] 장바구니 목록 조회 및 제거
        - [ ] cart.html 파일과 장바구니 관련 API를 이용하여 장바구니 페이지 완성
        - [ ] `/cart`url로 접근할 경우 장바구니 페이지를 조회하는 기능 구현
        - [ ] 장바구니 목록을 확인하고 상품을 제거하는 기능 구현

---

# CRUD API 설계

## Product

### 상품 조회 : GET

**Request**

```http request
GET /products HTTP/1.1
Host: localhost:8080
```

**Response**

```http request
HTTP/1.1 200
Content-Type: application/json

[
    {
        "id": 1,
        "name": "치킨",
        "imgUrl": "https://barunchicken.com/wp-content/uploads/2022/07/%EA%B3%A8%EB%93%9C%EC%B9%98%ED%82%A8-2.jpg",
        "price": 20000
    },
    {
        "id": 2,
        "name": "치킨",
        "imgUrl": "https://barunchicken.com/wp-content/uploads/2022/07/%EA%B3%A8%EB%93%9C%EC%B9%98%ED%82%A8-2.jpg",
        "price": 11000
    }
]
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
    "id": 1
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
Content-Type: application/json


{
    "status": "success"
}
```

### 상품 삭제 : DELETE

**Request**

```http request
DELETE /products/1 HTTP/1.1
Host: localhost:8080
```

**Response**

```http request
HTTP/1.1 200
Content-Type: application/json

{
    "status": "success"
}
```

--- 
