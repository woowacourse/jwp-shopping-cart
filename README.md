# jwp-shopping-cart

## 기능 구현 목록

- [x] 상품 목록 페이지 연동
  - [x] `/`로 이동하면 상품 목록 페이지를 조회할 수 있어야 한다.
  - [x] 상품의 목록은 `ID`, `이름`, `이미지`, `가격`을 가지고 있어야 한다.
- [x] 상품 관리 CRUD API 작성
  - [x] 생성, 조회, 수정, 삭제 API를 작성해야 한다.
  - [x] API 스펙은 정해진게 없으므로, API 설계를 직접 진행 한 후 기능을 구현한다.
- [x] 관리자 도구 페이지 연동
  - [x] `/admin`으로 이동하면 관리자 도구 페이지를 조회할 수 있어야 한다.
  - [x] 상품 추가, 수정, 삭제 기능이 동작하게 만들어야 한다.
- [ ] 사용자 기능 구현
  - [ ] 사용자의 기본 정보는 `email`과 `password` 가 있다. 
- [ ] 사용자 설정 페이지 연동
  - [ ] `/settings` 로 접근할 경우 모든 사용자의 정보를 확인하고 사용자를 선택할 수 있어야한다.
  - [ ] 사용자 설정 페이지에서 사용자를 선택하면, 이후 요청에 선택한 사용자의 인증 정보가 포함된다.
    - [ ] 사용자 정보는 요청 헤더의 `Authorization` 필드를 사용해 인증 처리하여 얻으며, `Basic` 인증 방식을 사용한다.
      - type: Basic
      - credentials : email:password를 base64로 인코딩한 문자열
      - ex) email@email.com:password -> ZW1haWxAZW1haWwuY29tOnBhc3N3b3Jk
- [ ] 장바구니 기능 구현
  - [ ] 장바구니에 상품 추가
  - [ ] 장바구니에 담긴 상품 제거
  - [ ] 장바구니 목록 조회
  - [ ] 장바구니 페이지 연동 
    - [ ] 1단계에서 구현한 상품 목록 페이지(`/`)에서 담기 버튼을 통해 상품을 장바구니에 추가할 수 있다.
    - [ ] `/cart`로 접근할 경우 장바구니 페이지를 조회할 수 있어야 한다.
    - [ ] 장바구니 목록을 확인하고 상품을 제거하는 기능을 동작하게 한다.  


<br><br><br><br>

## 상품 관리 API 명세
### 생성
- `/products` URL에 `POST` 요청을 보내면 상품이 생성되어야 한다.
- `body`에는 `이름(name)`, `이미지(imageUrl)`, `가격(price)`을 보낸다.  


- 요청(Request)
```
POST /products HTTP/1.1
Content-Type: application/json
```

```json
{
  "name" : "킨더조이",
  "price" : 1000,
  "imageUrl": "https://th3.tmon.kr/thumbs/image/29c/4d6/045/3b3fc88a6_700x700_95_FIT.jpg"
}
```

<br>

- 응답(Response)  
```
HTTP/1.1 201
Content-Type: application/json
Location:"/products/1"
```

```json
{
  "id" : 1,
  "name" : "킨더조이",
  "price" : 1000,
  "imageUrl": "https://th3.tmon.kr/thumbs/image/29c/4d6/045/3b3fc88a6_700x700_95_FIT.jpg"
}
```

<br><br>

### 수정
- `/products/{id}` URL에 `PATCH` 요청을 보내면 상품이 수정되어야 한다.
- `body`에는 `이름(name)`, `이미지(imageUrl)`, `가격(price)`을 보낸다.  


- 요청(Request)
```
PATCH /products/{id} HTTP/1.1
Content-Type: application/json
```

```json
{
  "name" : "킨더조이",
  "price" : 1000,
  "imageUrl": "https://th3.tmon.kr/thumbs/image/29c/4d6/045/3b3fc88a6_700x700_95_FIT.jpg"
}
```

<br>

- 응답(Response)
```
HTTP/1.1 200 
Content-Type: application/json
```

```json
{
  "id" : 1,
  "name" : "킨더조이",
  "price" : 1000,
  "imageUrl": "https://th3.tmon.kr/thumbs/image/29c/4d6/045/3b3fc88a6_700x700_95_FIT.jpg"
}
```

<br><br>

### 삭제
- `/products/{id}` URL에 `DELETE` 요청을 보내면 상품이 삭제되어야 한다.


- 요청(Request)
```
DELETE /products/{id} HTTP/1.1
Content-Type: application/json
```
<br>

- 응답(Response)
```
HTTP/1.1 200 
Content-Type: application/json
```

<br><br>

### 조회
- `/products` URL에 `GET` 요청을 보내면 상품이 조회되어야 한다.


- 요청(Request)
```
GET /products HTTP/1.1
Content-Type: application/json
```

<br>

- 응답(Response)
```
HTTP/1.1 200 
Content-Type: application/json
```

```json
[
  {
    "id": 1,
    "name": "글렌피딕",
    "price": 100000,
    "imageUrl": "image1"
  },
  {
    "id": 2,
    "name": "글렌리벳",
    "price": 200000,
    "imageUrl": "image2"
  },
  {
    "id": 3,
    "name": "글렌모렌지",
    "price": 300000,
    "imageUrl": "image3"
  },
  {
    "id": 4,
    "name": "글렌드로낙",
    "price": 400000,
    "imageUrl": "image4"
  }
]
```
