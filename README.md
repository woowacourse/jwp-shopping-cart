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
- [x] 사용자 기능 구현 
- [x] 사용자 설정 페이지 연동
- [x] 장바구니 기능 구현
  - [x] 장바구니에 상품 추가
  - [x] 장바구니에 담긴 상품 제거
  - [x] 장바구니 목록 조회
- [x] 장바구니 페이지 연동

## API

### PRODUCT

#### 생성

|     URL     |                  /product                  |
|:---:|:-------------------------------------------:|
| HTTP METHOD |                    POST                     |
 | BODY | String: Name,  int: Price, String: ImageUrl |

#### 읽기

- Request

|     URL     | /product |
|:---:|:--------:|
| HTTP METHOD |   GET    |
| BODY |          |

- Response
  - List<ProductResponse>
  - ProductResponse
    - long: id
    - String: name
    - String: imageUrl
    - int: price

#### 수정

|     URL     |                      /product                       |
|:---:|:----------------------------------------------------:|
| HTTP METHOD |                         PUT                          |
| BODY | int: Id, String: Name,  int: Price, String: ImageUrl |

#### 삭제

|     URL     | /product/{id} |
|:---:|:-------------:|
| HTTP METHOD |    DELETE     |
| BODY |      |

### CART

#### 생성

|     URL     |           /cart           |
|:---:|:-------------------------:|
| HTTP METHOD |           POST            |
 | HEADER | Authorization: Basic xxxx |
| BODY |      long: productId      |

### 읽기

- Request

|     URL     | /cart |
|:---:|:-----:|
| HTTP METHOD |  GET  |
| HEADER | Authorization: Basic xxxx |
| BODY |       |

- Response
  - List<CartItemResponse>
  - CartItemResponse
    - int: cartCount 
    - long: productId
    - String: name
    - String: imageUrl
    - int: price

#### 삭제

|     URL     | /cart/{id} |
|:---:|:----------:|
| HTTP METHOD |   DELETE   |
| HEADER | Authorization: Basic xxxx |
| BODY |    |
