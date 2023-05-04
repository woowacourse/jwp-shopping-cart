# jwp-shopping-cart

## TABLE
```sql
CREATE TABLE IF NOT EXISTS ITEM
(
    item_id     BIGINT      NOT NULL    AUTO_INCREMENT,
    name        VARCHAR     NOT NULL,
    image_url   VARCHAR     NOT NULL,
    price       INT         NOT NULL
)

```

## API SPEC
* 모든 상품 조회
  * `GET: /items`
  * `STATUS CODE`: `200 OK`
```text
request: 
http://localhost:8080/items

response:
{
    {
        "name": "치킨",
        "image": "http://images/something/where"
        "price": "10000"
    },
    
    {
        "name": "자전거",
        "image": "http://images/something/where"
        "price": "50000"
    }
}
```

* 단일 상품 등록
  * `POST: /items`
  * `STATUS CODE`: `201 CREATED`, `400 BAD REQUEST`
```text
request: 
http://localhost:8080/items

{
    "name": "치킨",
    "image": "http://images/something/where"
    "price": "10000"
}


response:
{
    "id": "1",
    "name": "치킨",
    "image": "http://images/somthing/where"
    "price": "10000"
}

```


* 단일 상품 수정
  * `PUT: /items/{id}`
  * `STATUS CODE`: `200 OK`, `400 BAD REQUEST`
```text
request: 
http://localhost:8080/items/1

{
    "name": "치킨",
    "image": "http://images/something/where"
    "price": "15000"
}


response:
{
    "id": "1",
    "name": "치킨",
    "image": "http://images/something/where"
    "price": "15000"
}

```


* 단일 상품 삭제
  * `DELETE: /items/{id}`
  * `STATUS CODE`: `200 OK`, `400 BAD REQUEST`
```text
request: 
http://localhost:8080/items/1
```

* 장바구니 조회
  * `GET: /carts`
  * * `STATUS CODE`: `200 OK`, `401 UNAUTHORIZED`, `400 BAD REQUEST`
```text
response:
    {
        "id": 1,
        "name": "자전거1",
        "imageUrl": "https://www.altonsports.com/prdimg/get/21-INNOZEN24_P_01%281060X600%29.jpg",
        "price": 10000
    },
    {
        "id": 2,
        "name": "자전거2",
        "imageUrl": "https://cdn.imweb.me/thumbnail/20220817/7b35b82e7c1ce.jpg",
        "price": 50000
    },
    {
        "id": 3,
        "name": "자전거3",
        "imageUrl": "https://thumbnail8.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2022/01/25/11/8/3294fc8a-92a5-410b-b3bb-fb3c7e18a1d9.jpg",
        "price": 100000
    }
```

* 장바구니 추가
  * `POST: /carts/{itemId}`
  * * `STATUS CODE`: `201 CREATED`, `400 BAD REQUEST`, `401 UNAUTHORIZED`
```text
response
{
    "cartId": 4,
    "itemResponses": [
        {
            "id": 1,
            "name": "자전거1",
            "imageUrl": "https://www.altonsports.com/prdimg/get/21-INNOZEN24_P_01%281060X600%29.jpg",
            "price": 10000
        },
        {
            "id": 2,
            "name": "자전거2",
            "imageUrl": "https://cdn.imweb.me/thumbnail/20220817/7b35b82e7c1ce.jpg",
            "price": 50000
        },
        {
            "id": 4,
            "name": "치킨",
            "imageUrl": "chiken@naver.com",
            "price": 15000
        }
    ]
}
```

* 장바구니 삭제
  * `DELETE: /carts/{itemId}`
  * * `STATUS CODE`: `200 OK`, `400 BAD REQUEST`, `401 UNAUTHORIZED`
```text
request: 
http://localhost:8080/carts/1
```



## 기능 목록
- [x] 상품 관리 CRUD API를 작성한다.
  - [x] 상품 등록 API를 구현한다.
  - [x] 상품 수정 API를 구현한다.
    - [x] 일치하는 상품이 없는 경우 예외가 발생한다.
  - [x] 상품 삭제 API를 구현한다.
    - [x] 일치하는 상품이 없는 경우 예외가 발생한다.
- [x] 상품 전체 조회 API를 구현한다.
- [x] 관리자 도구 페이지를 연동한다.
- [x] 사용자 기능을 구현한다.
  - [x] 사용자 정보는 `email`, `password`, `name`이 있다.
    - [x] `email`은 xxx@xxxx.xxx 형식으로 이뤄진다.
    - [x] `password`는 최소 4자, 최대 12자까지 가능하다.
    - [x] `name`은 최소 2자 최대 5자까지 가능하다.
  - [x] 사용자 인증은 Basic 방식을 사용한다.
  - [x] credentials 값은 name:email:password 를 base64로 인코딩한 문자열이다.
- [x] 사용자 설정 페이지를 연동한다.
- [x] 장바구니 기능을 구현한다.
  - [x] 장바구니에 상품을 추가한다.
    - [x] 이미 장바구니에 추가된 항목은 다시 추가할 수 없다.
  - [x] 장바구니에 담긴 상품을 제거한다.
  - [x] 장바구니 목록을 조회한다.
- [x] 장바구니 페이지를 연동한다.

## 상품 요구사항
  - [x] 상품의 이름은 최소 1자, 최대 50자까지 가능하다.
  - [x] 상품의 금액은 최소 10원, 최대 1억원 까지 가능하다.


