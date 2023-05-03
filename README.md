# jwp-shopping-cart

## 2단계 흐름 정리

- `USERS` 테이블에 이메일과 비밀번호 저장 (Mock Data)
- 해당 데이터를 `Settings.html`에 넘겨주기 `userService.findAll()`
- Select 버튼 클릭 시, LocalStroage `credentials`에 이메일:비밀번호 저장
- 장바구니 담기 클릭 시, HTTP Request Header의 `Authorization`에 Basic Credentials 입력 및 요청
- Controller가 해당 값을 받은 후 디코딩 (학습테스트 - `BasicAuthorizationExtractor` 참고)
- select 된 유저가 맞는 지 검증
    - 맞을 시 Cart 테이블에 저장 `cartService.addProduct()`
    - 아닐 시 예외 발생
- `/cart`url로 접근할 경우 장바구니 페이지 조회 `cartService.findByEmail()`
- DELETE 버튼 클릭 시 장바구니에서 상품 제거 `cartService.deleteById()`

## 2단계 기능 요구사항

- [ ]  사용자 기능 구현
    - [x]  사용자 기본 정보
        - email
        - password
    - [x]  사용자 설정 페이지 연동
        - [x]  `/settings` ****url로 접근
        - [x]  사용자 선택 시, 이후 요청에 선택한 사용자의 인증 정보 포함
    - [x]  사용자 인증 처리
        - [x]  사용자 정보 : 요청 Header의`Authorization`필드 디코딩
        - [x]  인증 방식 :[Basic 인증](https://en.wikipedia.org/wiki/Basic_access_authentication)
        - [x]  type: Basic
        - [x]  credentials :`email:password`를 base64로 인코딩한 문자열

- [ ]  장바구니 기능 구현
    - [ ]  장바구니 기본 정보
        - user_id
        - product_id
        - quantity
    - [ ]  장바구니 페이지 연동
        - [ ]  1단계에서 구현한 상품 목록 페이지(**`/`**)에서 담기 버튼을 통해 상품을 장바구니 추가
        - [ ]  `cart.html` 파일과 장바구니 관련 API를 이용하여 장바구니 페이지를 완성합니다.
    - [ ]  장바구니 관리 CRUD 목록 조회 및 제거
        - [ ]  장바구니 상품 추가
        - [ ]  장바구니 목록 조회 (`/cart`)
        - [ ]  장바구니 상품 제거

### 기능 요구 사항 분석 후 추가 구현 사항

- [x]  사용자
    - [x]  `@LoginUser` 어노테이션 구현
- [ ]  장바구니
    - [ ]  quantity 칼럼을 만들어서 Update 수량 변경 기능 추가 (PATCH)
- [ ]  그 외
    - [ ]  Entity 내 필드에 VO 설정
    - [ ]  `@Valid`
    - [ ]  `@ControllerAdvice`와 `@RestControllerAdvice` 분리

### ProductAPI

| HTTP Method | URL            | 설명       | HTTP Status |
|-------------|----------------|----------|-------------|
| get         | /              | 상품 목록 조회 | 200         |
| post        | /products      | 상품 생성    | 201         |
| put         | /products/{id} | 상품 수정    | 200         |
| delete      | /products/{id} | 상품 삭제    | 204         |

### CartAPI

| HTTP Method | URL        | 설명         | HTTP Status |
|-------------|------------|------------|-------------|
| get         | /cart      | 장바구니 목록 조회 | 200         |
| post        | /cart      | 장바구니 추가    | 201         |
| patch       | /cart/{id} | 장바구니 수량 수정 | 200         |
| delete      | /cart/{id} | 장바구니 삭제    | 204         |

