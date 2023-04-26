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

### API

#### 생성

|     URL     |                  /products                  |
|:---:|:-------------------------------------------:|
| HTTP METHOD |                    POST                     |
 | BODY | String: Name,  int: Price, String: ImageUrl |


#### 수정

|     URL     |                      /products                       |
|:---:|:----------------------------------------------------:|
| HTTP METHOD |                         PUT                          |
| BODY | int: Id, String: Name,  int: Price, String: ImageUrl |

#### 삭제

|     URL     | /products |
|:---:|:---------:|
| HTTP METHOD |  DELETE   |
| BODY |  int: Id  |

