# 프로그램 개요
쇼핑몰을 구현한 프로그램입니다.
사용자는 상품을 장바구니에 담을 수 있습니다.
Admin 사용자는 판매할 상품을 등록하거나, 수정, 삭제할 수 있습니다.

# 프로그램 설계

## Class Diagram
```mermaid
classDiagram
    Web Environment <--> ViewController
    class ViewController {
        화면을 호출
    }
    
    Web Environment <--> ProductController
    ProductController --> ProductService
    ProductService --> ProductRepository
    
    Web Environment <--> UserController
    UserController --> UserService
    UserService --> UserRepository
```

## Database
### Database 사용법
- H2 Database 사용
  - DB 접근 방법
    - 1)`JwpCartApplication` 실행
    - 2)`localhost:8080/h2-console` 접속
    - 3)`application.properties` 참고하여 Login 정보 입력하고 `Connect`

### DDL
```sql
CREATE TABLE product
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    name      VARCHAR(50)  NOT NULL,
    price     BIGINT       NOT NULL,
    image_url VARCHAR(500) NOT NULL,정
    PRIMARY KEY (id)
);
```


# API 명세 및 기능 구현 목록

## 화면 렌더링 관련 기능

### ✅ GET /
메인 화면을 렌더링하여 반환합니다.

**구현 내용**
> - [x] index.html 반환
>     - [x] 등록된 모든 Product 정보 렌더링

### ✅ GET /admin
Admin 화면을 렌더링하여 반환합니다.

**구현 내용**
> - [x] admin.html 반환
>     - [x] 등록된 모든 Product 정보 렌더링

### ✅ GET /users
전체 사용자 정보를 확인하고, 특정 사용자의 계정으로 로그인할 수 있는 화면을 반환합니다.

**구현 내용**
> - [x] users.html 반환
>   - [x] 등록된 모든 User 정보 렌더링

### ✅ GET /carts/me
나의 장바구니를 확인하고, 포함된 상품을 삭제할 수 있는 화면을 반환합니다.

**구현 내용**
> - [x] cart.html 반환


## Product 관련 Admin 기능

### ✅ POST /products
새로운 Product를 추가합니다.

**구현 내용**
> - [x] Product 생성
>     - [x] Product 추가 요청 유효성 검증
>         - [x] 이름은 공백일 수 없다.
>         - [x] 가격이 0원 이상 이어야 한다.
>         - [x] Image URL은 공백일 수 없다.
> - [x] DB 저장

**Request example**
```json
POST /products HTTP/1.1
Content-type: application/json; charset=UTF-8
Host: localhost:8080

{
    "name": "자전거",
    "price": 240000,
    "imageUrl": "test.com/image-url-test"
}
```

**Response example**
```json
HTTP/1.1 200 OK
Content-Type: application/json
```

### ✅ PUT /products/{product_id}
특정 Product를 수정합니다.

**구현 내용**
> - [x] DB에서 id에 해당하는 Product 수정
>     - [x] Product 수정 요청 유효성 검증
>         - [x] 이름은 공백일 수 없다.
>         - [x] 가격이 0원 이상 이어야 한다.
>         - [x] Image URL은 공백일 수 없다.

**Request example**
```json
PUT /products/1 HTTP/1.1
Content-type: application/json; charset=UTF-8
Host: localhost:8080

// 3가지 필드 모두 Required
{
    "name": "강화된 자전거", 
    "price": 240000,
    "imageUrl": "test.com/image-url-test"
}
```

**Response example**
```json
HTTP/1.1 200 OK
Content-Type: application/json
```

### ✅ DELETE /products/{product_id}
특정 Product를 삭제합니다.

**구현 내용**
> - [x] DB에서 id에 해당하는 Product 삭제

**Request example**
```json
DELETE /products/1 HTTP/1.1
Content-type: application/json; charset=UTF-8
Host: localhost:8080
```

**Response example**
```json
HTTP/1.1 204 No Content
Content-Type: application/json
```


## User 관련 기능

### POST /users
회원을 등록합니다.

**구현 내용**
> - [x] User 생성
>   - [x] User 정보 유효성 검증
>     - [x] email: email 형식에 맞아야 한다.
>     - [x] password:
>       - [x] 10자리 이상
>       - [x] 영문자, 숫자, 특수문자를 모두 포함
>         - [x] 특수문자는 "!@#$%^&*()"만 가능
> - [ ] DB에 저장

**Request example**
```json
POST /users HTTP/1.1
Content-type: application/json; charset=UTF-8
Host: localhost:8080
        
{
    "email": "test@test.test", // Required
    "password": 1234abcd!@, // Required
    "name": "김철수", // Optional
    "phoneNumber": "01012341234" // Optional
}
```

**Response example**
```json
HTTP/1.1 200 OK
Content-Type: application/json
```

## Cart 관련 기능

### ✅ GET carts/me/products
로그인 된 사용자의 장바구니에 상품을 추가합니다.

**구현 내용**
> - [x] 로그인 된 사용자의 장바구니에 포함된 모든 상품 정보 반환
>   - [x] 로그인 확인

**Request example**
```json
GET carts/me/products HTTP/1.1
Content-type: application/json; charset=UTF-8
Host: localhost:8080
Authorization: Basic ZW1haWxAZW1haWwuY29tOnBhc3N3b3Jk
```

**Response example**
```json
HTTP/1.1 200 OK
Content-Type: application/json

//TODO Response body example 추가하기
```

### POST carts/me/{product_id}
로그인 된 사용자의 장바구니에 상품을 추가합니다.

**구현 내용**
> - [x] 요청 받은 ID에 해당하는 Product를 Cart에 추가
>   - [x] 로그인 확인
> - [ ] DB에 저장

**Request example**
```json
POST carts/me/{product_id} HTTP/1.1
Content-type: application/json; charset=UTF-8
Host: localhost:8080
Authorization: Basic ZW1haWxAZW1haWwuY29tOnBhc3N3b3Jk
```

**Response example**
```json
HTTP/1.1 200 OK
Content-Type: application/json
```

### DELETE carts/me/{product_id}
로그인 된 사용자의 장바구니에서 상품을 삭제합니다.

**구현 사항**
> - [x] 요청 받은 ID에 해당하는 Product를 Cart에서 삭제
>   - [x] 로그인 확인
> - [ ] DB에 저장

**Request example**
```json
DELETE carts/me/{product_id} HTTP/1.1
Content-type: application/json; charset=UTF-8
Host: localhost:8080
Authorization: Basic ZW1haWxAZW1haWwuY29tOnBhc3N3b3Jk
```

**Response example**
```json
HTTP/1.1 200 OK
Content-Type: application/json
```


## 클라이언트 기능

### index.html
- URL: `localhost:8080/`
- 전체 상품 정보를 확인할 수 있는 메인 페이지

### admin.html & admin.js
- URL: `localhost:8080/admin`
- Product 조회, 추가, 수정, 삭제가 가능한 Admin 페이지
