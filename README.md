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


## 기능 목록
- [x] 상품 관리 CRUD API를 작성한다.
  - [x] 상품 등록 API를 구현한다.
  - [x] 상품 수정 API를 구현한다.
    - [x] 일치하는 상품이 없는 경우 예외가 발생한다.
  - [x] 상품 삭제 API를 구현한다.
    - [x] 일치하는 상품이 없는 경우 예외가 발생한다.
- [x] 상품 전체 조회 API를 구현한다.
- [x] 관리자 도구 페이지를 연동한다.
- [ ] 사용자 기능을 구현한다.
  - [ ] 사용자 정보는 `email`, `password`, `name`이 있다.
    - [ ] `email`은 xxx@xxxx.xxx 형식으로 이뤄진다.
    - [ ] `password`는 최소 4자, 최대 12자까지 가능하다.
    - [ ] `name`은 최소 2자 최대 5자까지 가능하다.
  - [ ] 사용자 인증은 Basic 방식을 사용한다.
  - [ ] credentials 값은 email:password 를 base64로 인코딩한 문자열이다.
- [ ] 사용자 설정 페이지를 연동한다.
- [ ] 장바구니 기능을 구현한다.
  - [ ] 장바구니에 상품을 추가한다.
    - [ ] 이미 장바구니에 추가된 항목은 다시 추가할 수 없다.
  - [ ] 장바구니에 담긴 상품을 제거한다.
  - [ ] 장바구니 목록을 조회한다.
- [ ] 장바구니 페이지를 연동한다.

## 상품 요구사항
  - [x] 상품의 이름은 최소 1자, 최대 50자까지 가능하다.
  - [x] 상품의 금액은 최소 10원, 최대 1억원 까지 가능하다.


