# jwp-shopping-cart

## 장바구니

- [x] 사용자 기능 구현
- [ ] 사용자 설정 페이지 연동
    - [x] settings.html 파일을 이용해서 사용자를 선택하는 기능
    - [x] /settings url로 접근할 경우 모든 사용자의 정보를 확인하고 사용자를 선택할 수 있다.
    - [ ] 사용자 설정 페이지에서 사용자를 선택하면, 이후 요청에 선택한 사용자의 인증 정보가 포함된다.
- [ ] 장바구니 기능 구현
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

### Service

- [x] 장바구니 서비스
    - [x] 상품 추가
    - [x] 상품 전체 조회
    - [x] 상품 수정
    - [x] 상품 삭제

### API 명세

- [x] 상품 Create
    - POST "/product"
    - Request: name, price, image
    - Response: 200 OK
- [x] 상품 Read
    - GET "/products"
    - Response: products{ {id, name, image, price}, {..}}
- [x] 상품 Update
    - PUT "/product"
    - Request: id, name, price, image
    - Response: 200 OK
- [x] 상품 Delete
    - DELETE "/product/{id}"
    - Response: 200 OK

