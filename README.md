# jwp-shopping-cart

## [1단계]

### API 명세서

| Method |         URI         | Description |
|:------:|:-------------------:|:-----------:|
|  GET   |      /products      |    상품 목록    |
|  GET   |       /admin        |   어드민 페이지   |
|  POST  |   /admin/product    |    상품 추가    |
|  GET   | /admin/product/{id} |  상품 수정 페이지  |
|  PUT   | /admin/product/{id} |    상품 수정    |
| DELETE | /admin/product/{id} |    상품 삭제    |

### 기능 요구사항

- [x] 상품 목록 페이지 연동
    - [x] index.html 파일을 이용하여 상품 목록이 노출되는 페이지를 완성한다.
- [x] 상품 관리 CRUD API 작성
- [x] 관리자 도구 페이지 연동

## [2단계]

### API 명세서

| Method |        URI         | Description |
|:------:|:------------------:|:-----------:|
|  GET   |     /settings      |   설정 페이지    |
|  POST  | /settings/{userId} |   사용자 선택    |
|  GET   |       /cart        |  장바구니 페이지   |
|  POST  | /cart/{productId}  | 장바구니에 상품 담기 |
| DELETE | /cart/{productId}  | 장바구니 상품 제거  |

### 기능 요구사항

- [x] 사용자 기능 구현
    - [x] 사용자가 가지고 있는 정보는 다음과 같다. (필요한 경우 사용자 정보 종류를 추가할 수 있다.)
        - email
        - password
        - (추가) name
        - (추가) address
        - (추가) age
- [x] 사용자 설정 페이지 연동
    - [x] '/settings' 로 접근하는 경우 모든 사용자의 정보를 확인하고 선택하는 페이지로 이동한다.
    - [x] 사용자를 선택하면, 다음 요청부터 선택한 사용자의 인증 정보가 포함된다.
- [x] 장바구니 기능 구현
    - [x] 상품 추가 기능
    - [x] 담긴 상품 제거 기능
    - [x] 목록 조회
- [x] 장바구니 페이지 연동
    - [x] 상품 목록 페이지에서 '담기' 버튼을 눌러 해당 상품을 장바구니에 추가할 수 있다.
    - [x] '/cart' 로 접근하는 경우 장바구니 페이지를 조회할 수 있다.
