# 장바구니 API

---

# Product

### 목록 조회

**HTTP request**

```text
GET /api/products HTTP/1.1
Content-Type: application/json
Accept: application/json
Content-Length: 35
Host: localhost:8080
```

**HTTP response**

```text
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
Content-Length: 47

{
	[
	  "id" : 1
	  "name" : "맥북"
	  "price" : 1000
		"stockQuantity" : 10
	  "thumbnailImage" : {
			"url" : "url",
			"alt" : "이미지입니다."
		}
	],
	[
	  "id" : 2
	  "name" : "애플 워치"
	  "price" : 100
		"stockQuantity" : 10
	  "thumbnailImage" : {
			"url" : "url",
			"alt" : "이미지입니다."
		}
	]
}
```

### 추가

(별도의 판매자가 있는지, 모두가 다 추가가 가능한지, 추가 기능이 있는지)
→ 관리자 기능이 없어서 상품 추가 기능도 없음

**HTTP request**

```text
POST /api/products HTTP/1.1
Content-Type: application/json
Accept: application/json
Content-Length: 35
Host: localhost:8080

{
	  "name" : "맥북"
	  "price" : 1000
		"stockQuantity" : 10
	  "thumbnailImage" : {
			"url" : "url",
			"alt" : "이미지입니다."
		}
}
```

**HTTP response**

```text
HTTP/1.1 201 Created
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Location: /api/products/1
Content-Type: application/json
Content-Length: 47

{
	  "id" : 1
	  "name" : "맥북"
	  "price" : 1000
		"stockQuantity" : 10
	  "thumbnailImage" : {
			"url" : "url",
			"alt" : "이미지입니다."
		}
}
```

### 단건 조회

**HTTP request**

```text
GET /api/products/{productId} HTTP/1.1
Content-Type: application/json
Accept: application/json
Content-Length: 35
Host: localhost:8080
```

**HTTP response**

```text
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
Content-Length: 47

{
	  "id" : 1
	  "name" : "맥북"
	  "price" : 1000
		"stockQuantity" : 10
	  "thumbnailImage" : {
			"url" : "url",
			"alt" : "이미지입니다."
		}
}
```

### 삭제(별도의 판매자가 있는지, 모두가 다 추가가 가능한지, 추가 기능이 있는지)

→ **“sold out” (`불필요`)**

→ 관리자 기능이 없어서 상품 삭제 기능도 없음.

→ 그러나 상품 개수가 0인 상품 정보는 존재할 수 있고, 이 경우 sold out 표시를 해줘야한다고 생각함. 상품의 재고가 없는 것과 상품 자체가 없는 것은 다르다.

- **sold out 을 표시할건지, 표시없이 그냥 없앨건지**

  **→ 백엔드 의견은 표시없이 그냥 삭제되는게 맞다라고 생각**

**HTTP request**

```text
DELETE /api/products/{productId} HTTP/1.1
Content-Type: application/json
Accept: application/json
Content-Length: 35
Host: localhost:8080
```

**HTTP response**

```text
HTTP/1.1 204 No Content
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
Content-Length: 47
```

---

# Cart item(장바구니)

### 목록 조회

**HTTP request**

```text
GET /api/mycarts HTTP/1.1
Content-Type: application/json
Accept: application/json
Content-Length: 35
Host: localhost:8080
Authorization: Bearer XXXXXXXX
```

**HTTP response**

```text
HTTP/1.1 200 Ok
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
Content-Length: 47

{
	[
	  "id" : 1
	  "productId" : 1
	  "name" : "애플워치"
	  "price" : 1000
		"quantity" : 10
	  "thumbnailImage" : {
			"url" : "url",
			"alt" : "이미지입니다."
		}
	],
	[
	  "id" : 2
	  "productId" : 3
	  "name" : "맥북"
	  "price" : 1000
	  "quantity" : 10
	  "thumbnailImage" : {
			"url" : "url",
			"alt" : "이미지입니다."
		}
	]
}
```

### 단건 조회

**HTTP request**

```text
GET /api/mycarts/{cartItemId} HTTP/1.1
Content-Type: application/json
Accept: application/json
Content-Length: 35
Host: localhost:8080
Authorization: Bearer XXXXXXXX
```

**HTTP response**

```text
HTTP/1.1 200 Ok
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
Content-Length: 47

{
  "id" : 13,
  "productId" : 20,
  "price" : 2000,
  "name" : "상품",
  "quantity" : 1,
  "thumbnailImage" : {
    "src" : "https://www.test.com",
    "alt" : "imageAlt"
  }
}
```

### 추가

### 장바구니 처음 담기

**HTTP request**

```text
POST /api/mycarts HTTP/1.1
Content-Type: application/json
Accept: application/json
Content-Length: 35
Host: localhost:8080
Authorization: Bearer XXXXXXXX

{
	"productId" : 1
	"quantity" : 1
}
```

**HTTP response**

```text
HTTP/1.1 201 Created
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Location: /api/mycarts/1
Content-Type: application/json
Content-Length: 47

{
  "id" : 1,
  "productId" : 1,
  "price" : 2000,
  "name" : "상품",
  "quantity" : 1,
  "thumbnailImage" : {
    "src" : "https://www.test.com",
    "alt" : "imageAlt"
  }
}
```

### 장바구니 개수 업데이트

**HTTP request**

```text
PATCH /api/mycarts HTTP/1.1
Content-Type: application/json
Accept: application/json
Content-Length: 35
Host: localhost:8080
Authorization: Bearer XXXXXXXX

{
	"cartItemId" : 1
	"quantity" : 2
}
```

**HTTP response**

```text
HTTP/1.1 200 Ok
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
Content-Length: 47
```

**400 터지면 GET(/api/mycarts) 보내기**

### 삭제

**HTTP request**

```text
DELETE /api/mycarts HTTP/1.1
Content-Type: application/json
Accept: application/json
Content-Length: 35
Host: localhost:8080
Authorization: Bearer XXXXXXXX

{
	"cartItemIds" : [1, 2, 3]
}
```

**HTTP response**

```text
HTTP/1.1 204 No Content
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
Content-Length: 47
```

---

# Order(주문)

### 목록 조회

**HTTP request**

```text
GET /api/myorders HTTP/1.1
Content-Type: application/json
Accept: application/json
Content-Length: 35
Host: localhost:8080
Authorization: Bearer XXXXXXXX
```

**HTTP response**

```text
  HTTP/1.1 200 OK
  Vary: Origin
  Vary: Access-Control-Request-Method
  Vary: Access-Control-Request-Headers
  Content-Type: application/json
  Content-Length: 47
  
  {
      [
        "id" : 1,
          "orderedProducts" : [{
              "productId" : 1,
              "quantity" : 10,
              "price" : 1000,
              "name" : "상품이름",
              "thumbnailImage" : {
                  "url" : "url",
                  "alt" : "이미지입니다."
              }
          },
          {
              "productId" : 2,
              "quantity" : 10,
              "price" : 1000,
              "name" : "또다른 상품",
              "thumbnailImage" : {
                  "url" : "url",
                  "alt" : "이미지입니다."
              }
          }],
          [
        "id" : 2,
          "orderedProducts" : [{
              "productId" : 1,
              "quantity" : 10,
              "price" : 1000,
              "name" : "상품이름",
              "thumbnailImage" : {
              "url" : "url",
              "alt" : "이미지입니다."
              }
          },
          {
              "productId" : 2,
              "quantity" : 10,
              "price" : 1000,
              "name" : "또다른 상품",
              "thumbnailImage" : {
              "url" : "url",
              "alt" : "이미지입니다."
          }
          }]
      ]
  }
```

### 추가(주문하기)

**HTTP request**

```text
POST /api/myorders HTTP/1.1
Content-Type: application/json
Accept: application/json
Content-Length: 35
Host: localhost:8080
Authorization: Bearer XXXXXXXX

{
	"cartItemIds" : [1, 2, 3]
}
```

**HTTP response**

```text
HTTP/1.1 201 Created
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Location: /api/myorders/1
Content-Type: application/json
Content-Length: 47
```

### 단건 조회

**HTTP request**

```text
GET /api/myorders/{orderId} HTTP/1.1
Content-Type: application/json
Accept: application/json
Content-Length: 35
Host: localhost:8080
Authorization: Bearer XXXXXXXX
```

**HTTP response**

```text
  HTTP/1.1 200 OK
  Vary: Origin
  Vary: Access-Control-Request-Method
  Vary: Access-Control-Request-Headers
  Content-Type: application/json
  Content-Length: 47
  
  {
        "id" : 1
        "orderedProducts" : [{
              "productId" : 1,
              "quantity" : 10,
              "price" : 1000,
              "name" : "상품이름",
              "imageUrl" : "url"
          },
          {
              "productId" : 2,
              "quantity" : 10,
              "price" : 1000,
              "name" : "또다른 상품",
              "imageUrl" : "url"
          }
      ]
  }
```

---

## 에러

### 상품(4000번대)

- **상품 단건 조회**

  | 에러 상황 | 에러 코드 | 에러 메세지 | 상태 코드 |
                                      | :------------: | :-------------: |:------------: |:------------: |
  | ProductTable에 없는 상품(productId)인 경우 | 6001  | Not Exist Product | 404 |
  | 가격이 음수인 경우 | 4004  | Invalid Price | 400 |

<br>

### 장바구니(5000번대)

- **장바구니 담기**

  | 에러 상황 | 에러 코드 | 에러 메세지 | 상태 코드 |
                                        | :------------: | :-------------: |:------------: |:------------: |
  |이미 장바구니에 존재 하는 경우| 5001  | Already Exist | 400 |
  |quantity 유효성 검사 실패 (숫자가 아닌 경우, 음수 등등)| 5002  | Invalid Quantity | 400 |

<br>

- **장바구니 담기**

  | 에러 상황 | 에러 코드 | 에러 메세지 | 상태 코드 |
                                          | :------------: | :-------------: |:------------: |:------------: |
  |quantity 유효성 검사 실패 (숫자가 아닌 경우, 음수 등등)| 5002  | Invalid Quantity | 400 |
  |내가 담은 장바구니 item 이 아닌 경우| 5003  | Invalid CartItem | 400 |
  |장바구니에 없는 상품(cartItemId)인 경우| 6002  | Not Exist CartItem | 404 |

<br>

### 주문하기(7000번대)

- 가*주문 추가**

  | 에러 상황 | 에러 코드 | 에러 메세지 | 상태 코드 |
                                          | :------------: | :-------------: |:------------: |:------------: |
  |재고가 요청한 quantity보다 적은 경우| 7001  | Out Of Stock | 400 |
  
