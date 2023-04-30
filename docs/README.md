# jwp-shopping-cart

# 📚 도메인 모델 네이밍 사전

| 한글명  | 영문명     | 설명      | 분류    |
|------|---------|---------|-------|
| 상품   | Product | 상품 정보   | class |
| 사용자  | User    | 사용자 정보  | class |
| 장바구니 | Cart    | 장바구니 정보 | class |

<br>

## DB(DAO)

- H2 데이터베이스를 사용한다.
- DB 테이블 설계
    - Database 명 : `shopping_cart`
        - Table 명 : `product`

          | id | name | image_url | price |
           |----|---------|-------------|-------|
          | 1 | chicken | https://... | 10000 |
          | 2 | pizza | https://... | 12000 |

        - Table 명 : `user`

          | id | email                 | password |
          |----|-----------------------|----------|
          | 1 | songsy405@naver.com   | abcd     | 
          | 2 | songsy405@pusan.ac.kr | 1234     | 

        - Table 명 : `cart`
      
          | id | member_id | product_id |
          |----|-----------------------|----------|
          | 1 | 1 | 3 |
          | 2 | 1 | 2 |

    
# 👨‍🍳 기능 목록

## 상품 목록 페이지 연동

- [x] 사용자로부터 `/`에 대해 GET 요청을 받으면 index.html 페이지를 반환한다.
- [x] 상품에 대한 데이터를 담을 클래스를 만든다.
    ```
  GET / HTTP/1.1
  ```

## 관리자 도구 페이지 연동

- [x] 사용자로부터 `/admin`에 대해 GET 요청을 받으면 admin.html 페이지를 반환한다.
    ```
  GET /admin HTTP/1.1
  ```
- [x] 잘못된 요청을 받을 경우 에러처리한다.

### 상품 관리 CRUD API 작성

- [x] 사용자로부터 GET 요청을 받으면 DB에서 상품 목록을 불러와 반환한다.
    ```
  GET /admin HTTP/1.1
  ```
- [x] 사용자로부터 POST 요청을 받으면 새 상품을 DB에 저장한다.
    ```
  POST /admin/products HTTP/1.1
  ```
- [x] 사용자로부터 PUT 요청을 받으면 상품의 정보를 수정하여 DB에 저장한다.
    ```
  PUT /admin/products/{product_id} HTTP/1.1
  ```
- [x] 사용자로부터 DELETE 요청을 받으면 상품을 DB에서 제거한다.
    ```
  DELETE /admin/products/{product_id} HTTP/1.1
  ```

## 사용자 설정 페이지 연동

- [x] 사용자로부터 `/settings`에 대해 GET 요청을 받으면 settings.html 페이지를 반환한다.
- [x] 사용자에 대한 데이터를 담을 클래스를 만든다.
    ```
  GET /settings HTTP/1.1
  ```

### 사용자 CRUD API 작성

(별도의 요구사항이 없기 때문에 데이터 추가 api만 구현하였다)

- [x] user 테이블에 사용자 정보를 추가하는 메서드를 구현한다.

## 장바구니 페이지 연동

- [x] 사용자로부터 `/cart`에 대해 GET 요청을 받으면 cart.html 페이지를 반환한다.
- [x] 장바구니에 대한 데이터를 담을 클래스를 만든다.
    ```
  GET /cart HTTP/1.1
  ```

### 장바구니 CRUD API 작성

- [x] 사용자로부터 인증 정보와 함께 GET 요청을 받으면 DB에서 해당 사용자의 장바구니 목록을 불러와 반환한다.
    ```
    GET /cart/products HTTP/1.1
    Authorization: Basic ZW1haWxAZW1haWwuY29tOnBhc3N3b3Jk
    ```
- [x] 사용자로부터 인증 정보와 함께 POST 요청을 받으면 새 상품을 DB cart 테이블에 저장한다.
    ```
    POST /cart/{product_id} HTTP/1.1
    Authorization: Basic ZW1haWxAZW1haWwuY29tOnBhc3N3b3Jk
    ```
  - [ ] 이미 존재하는 상품을 추가하려 할 경우 예외처리한다.
- [x] 사용자로부터 인증 정보와 함께 DELETE 요청을 받으면 상품을 DB cart 테이블 에서 제거한다.
    ```
    DELETE /cart/{product_id} HTTP/1.1
    Authorization: Basic ZW1haWxAZW1haWwuY29tOnBhc3N3b3Jk
    ```
  - [ ] cart에 존재하지 않는 상품을 제거하려 할 경우 예외처리 한다.
- [x] credentials로는 email:password를 base64로 인코딩한 문자열을 사용한다.

## 테스트 코드 작성

- [x] product DAO CRUD 테스트
- [x] 컨트롤러 CRUD 테스트
- [ ] cart DAO CRUD 테스트

# 📌 Commit Convention

커밋 메시지는 다음과 같은 형태로 작성합니다.

```Bash
> "커밋의 타입: 커밋 메세지 내용"
ex) "docs: 기능 목록 추가"
```

커밋의 타입은 아래 10가지 중 가장 적절한 것을 선택해 작성합니다.

|  커밋의 타입  |              설명               |
|:--------:|:-----------------------------:|
|   feat   |           새로운 기능 추가           |
|   fix    |             버그 수정             |
|   test   |           테스트 코드 추가           |
|   docs   | 문서를 추가 혹은 수정 (ex. README 수정)  |
|  chore   |   빌드 태스크 업데이트, 패키지 매니저를 설정    |
| refactor |            코드 리팩토링            |
|  style   | 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우 |

- 상세한 컨벤션
  내용은 [Angular JS Git Commit Message Conventions](https://gist.github.com/stephenparish/9941e89d80e2bc58a153)
  를 참고

# 📌 Code Convention

- [우아한 테크코스 Java 코딩 컨벤션](https://github.com/woowacourse/woowacourse-docs/tree/main/styleguide/java)을
  준수합니다.
- IntelliJ의 Formatter를 적용합니다.