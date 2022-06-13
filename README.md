
### API  

### no required authorization  

#### 회원 가입
- request  

`POST /api/customer/signup`  

```json
{
    "username" : "customer1",
    "password" : "password123",
    "phoneNumber" : "01012345678",
    "address" : "Seoul..."
}
```

- response
```
201 created
body empty
```

#### 상품 등록  

- request  

`POST /api/products`

```json
{
  "name" : "과자",
  "price" : 1000,
  "stock" : 20,
  "imageURL" : "http..."
}
```

- response

`201 created`  

```
Location : "/api/products/{productId}"
```

#### 상품 단일 조회  

- request  

`GET /api/products/{productId}`  

- response  

`200 ok`  

```json
{
  "product" : {
    "id" : 1, 
    "name" : "과자", 
    "price" : 1000, 
    "stock" : 20, 
    "imageURL" : "http...."
  }
}
```

#### 상품 전체 조회  

- request  

`GET /api/products`  

- response  

`200 ok`  

```json
{
  "products" : [
    {
      "id" : 1, 
      "name" : "과자", 
      "price" : 1000, 
      "stock" : 20, 
      "imageURL" : "http...."
    }, 
    {
      "id" : 2, 
      "name" : "소주", 
      "price" : 2000, 
      "stock" : 15,
      "imageURL" : "http...."
    }
  ]
}
```  

#### 상품 삭제  

- request  

`DELETE /api/products/{productId}`  

- response  

`204 no content`  

### required authorization
#### 로그인  

- request  

`POST /api/customer/login`  

```json
{
    "username" : "customer1",
    "password" : "password123"
}
```

- response

`200 ok`

```json
{
    "access-token" : "jwt token here"
}
```


#### 회원 정보 조회
- 
- request

`GET /api/customer`  

- response

`200 ok`  

```json
{
  "customer" : {
    "username" : "customer1",
    "phoneNumber" : "01012345678",
    "address" : "Seoul..."
  }
}
```

#### 회원 정보 수정
- request  

`PATCH /api/customer`  

```json
{
  "phoneNumber" : "01087654321",
  "address" : "Seoul..."
}
```

- response

`204 no-content`

#### 회원 탈퇴
- request  

`DELETE /api/customer`  

- response

`204 no content`  


### 카트  

#### 카트 전체 아이템 조회  

- request

`GET /api/cartItems`  

- response  

`200 ok`

```json
{
  "cartItems" : [
    {
      "id" : 1,
      "productId" : 5,
      "name" : "음식1",
      "price" : 1000,
      "quantity" : 2,
      "imageURL" : "http:..."
    } , 
    {
      "id" : 2,
      "productId" : 7,
      "name" : "음식2",
      "price" : 1000,
      "quantity" : 3,
      "imageURL" : "http:..."
    }
  ]
}
```

#### 아이템 추가  

- request  

`POST /api/cartItems`  

```json
{
  "productId" : 1, 
  "quantity" : 2
}
```

- response

`201 created`  

#### 아이템 수량 수정  

- request

`PATCH /api/cartItems/{cartItemId}?quantity={quantity}`  

- response  

`200 ok`

#### 아이템 삭제  

- request  

`DELETE /api/cartItems/{cartItemId}`  

- response  

`204 no content`

### Error point
- 에러 메시지 전달
- 에러 코드 전달

```json
{
  "error" : {
    "message" : "존재하지 않는..."
  }
}
```
// 리스트 전달

## 1단계 기능 목록
- 테이블과 데이터 추가
    - customer username, password, phoneNumber, address
- customer
    - username
        - 영어와 숫자가 아니면 예외 발생
        - 3자 이상 15자 이하가 아니면 예외 발생
    - password
        - 영어와 숫자가 둘 다 존재하지 않으면 예외 발생
        - 8자 이상 20자 이하가 아니면 예외 발생
    - phoneNumber
        - 열한자의 숫자가 아니면 예외 발생
    - address

    
