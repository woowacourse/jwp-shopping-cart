# jwp-shopping-cart

## 장바구니

- [x] 사용자 기능 구현
- [x] 사용자 설정 페이지 연동
    - [x] settings.html 파일을 이용해서 사용자를 선택하는 기능
    - [x] /settings url로 접근할 경우 모든 사용자의 정보를 확인하고 사용자를 선택할 수 있다.
    - [x] 사용자 설정 페이지에서 사용자를 선택하면, 이후 요청에 선택한 사용자의 인증 정보가 포함된다.
- [ ] 장바구니 기능 구현
    - [ ] 장바구니에 상품 추가
    - [ ] 장바구니에 담긴 상품 제거
    - [ ] 장바구니 목록 조회
    - [ ] 사용자 정보는 요청 Header의 Authorization 필드를 사용해 인증 처리를 하여 얻습니다.
    - [ ] 인증 방식은 Basic 인증을 사용합니다.
        - Authorization: Basic ZW1haWxAZW1haWwuY29tOnBhc3N3b3Jk
        - type: Basic
        - credentials : email:password를 base64로 인코딩한 문자열
- [ ] 장바구니 페이지 연동

### 도메인 엔티티

- [x] 상품
    - [x] id
    - [x] 이름
        - [x] 1 ~ 20 자로 제한한다.
    - [x] 이미지
    - [x] 가격
        - [x] 숫자로만 이루어진다.
        - [x] 0원 이상 1억원 이하이다.
        - [x] 10원 단위여야 한다.
- [x] 사용자
    - [x] id
    - [x] email
        - [x] '@'를 1개 포함해야 한다.
        - [x] 길이는 5 ~ 30 글자이다.
    - [x] password
        - [x] 길이는 6 ~ 30 글자이다.

### DAO

- [x] Product CRUD
- [x] User
    - [x] 이메일로 유저 조회
    - [x] 유저 전체 조회
    - [x] 유저 장바구니 상품 추가 (Authentication)
    - [x] 유저 장바구니 상품 제거 (Authentication)
    - [x] 유저의 장바구니 전체 조회 (Authentication)

### Service

- [x] Product
    - [x] 상품 추가
    - [x] 상품 전체 조회
    - [x] 상품 수정
    - [x] 상품 삭제
- [x] User
    - [x] 유저 전체 조회
    - [x] 유저 장바구니 상품 추가
    - [x] 유저 장바구니 상품 제거
    - [x] 유저의 장바구니 전체 조회

### API 명세

#### 상품

- [x] 상품 Create
    - POST "/products"
    - Request: name, price, image
    - Response: 200 OK
- [x] 상품 Read
    - GET "/products/all"
    - Response: products{{id, name, image, price}, {..}}
- [x] 상품 Update
    - PUT "/products/{id}"
    - Request: name, price, image
    - Response: 200 OK
- [x] 상품 Delete
    - DELETE "/products/{id}"
    - Response: 200 OK

#### 유저 장바구니

- [ ] 유저 장바구니 상품 추가 (Authentication)
    - POST "/user/cart"
    - Request: productId
    - Response: 200 OK
- [ ] 유저 장바구니 상품 제거 (Authentication)
    - DELETE "/user/cart/{id}"
    - Response: 200 OK
- [ ] 유저의 장바구니 전체 조회 (Authentication)
    - GET "/user/cart/all"
    - Response: products{{id, name, image, price}, {...}}
