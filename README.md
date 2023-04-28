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
- [x] 상품 관리 API를 구현한다.
  - [x] 상품 등록 API를 구현한다.
  - [x] 상품 수정 API를 구현한다.
    - [x] 일치하는 상품이 없는 경우 예외가 발생한다.
  - [x] 상품 삭제 API를 구현한다.
    - [x] 일치하는 상품이 없는 경우 예외가 발생한다.
  - [x] 상품 전체 조회 API를 구현한다.
- [ ] 사용자 기능 API를 구현한다.
  - [ ] 사용자 전체 조회 API를 구현한다.
- [ ] 장바구니 기능 API를 구현한다.
  - [ ] 장바구니 상품 추가 API를 구현한다.
  - [ ] 장바구니 상품 제거 API를 구현한다.
  - [ ] 장바구니에 담긴 상품 전체 조회 API를 구현한다.

## 화면 목록

- [x] 관리자 도구 페이지를 연동한다.
- [ ] 장바구니 페이지를 연동한다.
- [ ] 사용자 설정 페이지를 연동한다.

## 상품 요구사항
- [x] 상품의 이름은 최소 1자, 최대 50자까지 가능하다.
- [x] 상품의 금액은 최소 10원, 최대 1억원 까지 가능하다.

## 사용자 요구사항
- [ ] 사용자의 email은 이메일 형식이어야 한다.
- [ ] 사용자의 password는 공백일 수 없다.
