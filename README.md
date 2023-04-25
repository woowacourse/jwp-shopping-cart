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

* 단일 상품 등록
  * `POST`: /items
```text
request: 
http://localhost:8080/items

{
    "name": "치킨",
    "image": "http://images/somthing/where"
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
    "image": "http://images/somthing/where"
    "price": "15000"
}


response:
{
    "id": "1",
    "name": "치킨",
    "image": "http://images/somthing/where"
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
- [ ] 상품 관리 CRUD API를 작성한다.
  - [ ] 상품 등록 API를 구현한다.
  - [ ] 상품 조회 API를 구현한다.
  - [ ] 상품 수정 API를 구현한다.
  - [ ] 상품 삭제 API를 구현한다.
- [ ] 상품 전체 조회 API를 구현한다.
- [ ] 관리자 도구 페이지를 연동한다.



