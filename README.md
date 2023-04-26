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
  * `GET`: /items
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
  * `POST`: /items
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
  * `PUT`: /items/{id}
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
  * `DELETE`: /items/{id}
```text
request: 
http://localhost:8080/items/1
```


## 기능 목록
- [x] 상품 관리 CRUD API를 작성한다.
  - [x] 상품 등록 API를 구현한다.
  - [x] 상품 수정 API를 구현한다.
  - [x] 상품 삭제 API를 구현한다.
- [x] 상품 전체 조회 API를 구현한다.
- [x] 관리자 도구 페이지를 연동한다.

## 상품 요구사항
  - [x] 상품의 이름은 최소 1자, 최대 50자까지 가능하다.
  - [x] 상품의 금액은 최소 10원, 최대 1억원 까지 가능하다.


