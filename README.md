# jwp-shopping-cart

## 요구사항
- [x] 상품 목록 페이지 연동
  - index.html 파일을 이용하여 상품 목록이 노출되는 페이지를 완성합니다.
  - / url로 접근할 경우 상품 목록 페이지를 조회할 수 있어야 합니다.
- [x] 상품 관리 CRUD API 작성
  - [x] 상품 생성
  - [x] 상품 목록 조회
  - [x] 상품 수정
  - [x] 상품 삭제
- [x] 관리자 도구 페이지 연동
  - /admin url로 접근할 경우 관리자 도구 페이지를 조회할 수 있어야 합니다.
- [x] 설정 페이지 연등
  - /settings url로 접근할 경우 설정 페이지를 조회할 수 있어야 한다.
- [ ] 장바구니 기능 구현
  - [x] 장바구니에 상품을 추가할 수 있어야 한다.
  - [ ] 장바구니에서 상품을 제거할 수 있어야 한다.
  - [ ] 장바구니 목록을 조회할 수 있어야 한다.

## API

### product

#### 생성

- request

|     URL     |                  /products                  |
|:---:|:-------------------------------------------:|
| HTTP METHOD |                    POST                     |
 | BODY | String: Name,  int: Price, String: ImageUrl |

- response

|   HEADER    | LOCATION : /products/{id} |
|:-----------:|:-------------------------:|
| HTTP STATUS |       201(CREATED)        |
| BODY |                           |


#### 수정

- request

|     URL     |                      /products                       |
|:---:|:----------------------------------------------------:|
| HTTP METHOD |                         PUT                          |
| BODY | int: Id, String: Name,  int: Price, String: ImageUrl |

- response

|   HEADER    |          |
|:-----------:|:--------:|
| HTTP STATUS | 200(OK)  |
| BODY | int: Id, String: Name,  int: Price, String: ImageUrl  |


#### 삭제

- request

|     URL     | /products |
|:---:|:---------:|
| HTTP METHOD |  DELETE   |
| BODY |  int: Id  |

- response

|   HEADER    |  |
|:-----------:|:------------------------:|
| HTTP STATUS |      204(NOCONTENT)      |
| BODY |                          |


### cart


#### 생성

- request

|     URL     |                 /carts                  |
|:-----------:|:---------------------------------------:|
| HTTP METHOD |                  POST                   |
|   HEADER    | AUTHORIZATION : Basic ${email:password} |
|    BODY     |             Long: productId             |

- response

|   HEADER    | LOCATION : /carts/{id} |
|:-----------:|:----------------------:|
| HTTP STATUS |      201(CREATED)      |
| BODY |                        |

#### 삭제

- request

|     URL     |                 /carts                  |
|:---:|:---------------------------------------:|
| HTTP METHOD |                 DELETE                  |
|   HEADER    | AUTHORIZATION : Basic ${email:password} |
| BODY |             Long: productId             |

- response

|   HEADER    |  |
|:-----------:|:------------------------:|
| HTTP STATUS |      204(NOCONTENT)      |
| BODY |                          |
