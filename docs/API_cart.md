# 장바구니 회원 API

회원의 장바구니 기능은 인증 헤더를 필요로합니다.

#### 공통 헤더

| name          | description | required |
|---------------|-------------|----------|
| Authorization | 인증할 사용자 정보  | O        |

## 장바구니 목록 조회 API

### 요청

```http request
GET /user/cart HTTP/1.1
Authorization: Basic ${BASE64_ENCODED_STRING}
```
### 응답

| Name    | Type    | Description | Required |
|---------|---------|------------|----------|
| status  | Integer | Http 응답 코드 | O|
| success | Boolean | 성공 여부      | O|
| data | ProductsResponseDto | 장바구니의 상품 리스트 | O|

#### ProductsResponseDto

| Name     | Type                       | Description | Required |
|----------|----------------------------|-------------|----------|
| products | List\<ProductResponseDto\> | 상품          | O|

#### ProductResponseDto

| Name   | Type    | Description | Required | 
|--------|---------|-------------|----------|
| id     | Long    | 상품 아이디      | O|
| name   | String  | 상품 이름       | O|
| price  | Integer | 상품 가격       | O|
| imgUrl | String  | 상품 이미지 URL  | O|

## 장바구니 상품 추가 API

### 요청

```http request
POST /user/cart HTTP/1.1
Content-Type: application/json
Authorization: Basic ${BASE64_ENCODED_STRING}
```

#### 바디


| Name | Type | Description | Required |
|------|------|-------------|----------|
|productId| Long| 추가할 상품의 id| O|

### 응답


| Name    | Type    | Description | Required |
|---------|---------|------------|----------|
| status  | Integer | Http 응답 코드 | O|
| success | Boolean | 성공 여부      | O|


## 장바구니 상품 제거 API

### 요청

```http request
DELETE /user/cart/{cartItemId} HTTP/1.1
Authorization: Basic ${BASE64_ENCODED_STRING}
```

#### 파라미터

| Name       | Type | Description      | Required |
|------------|------|------------------|----------|
| cartItemId | Long| 삭제할 장바구니 아이템의 id | O|

### 응답


| Name    | Type    | Description | Required |
|---------|---------|------------|----------|
| status  | Integer | Http 응답 코드 | O|
| success | Boolean | 성공 여부      | O|
