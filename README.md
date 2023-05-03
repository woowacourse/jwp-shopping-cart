# jwp-shopping-cart

## 요구 기능 목록

### view

- [x] 상품 목록 페이지 연동
    - [x] "/" 로 접근하면 상품 목록 페이지(index.html)로 접근한다.
    - [x] 상품 조회 기능이 동작하게 만든다.
    - [ ] 장바구니에 상품을 추가하는 기능이 동작하게 한다.
- [x] 관리자 도구 페이지 연동
    - [x] "/admin" 으로 접근할 경우 관리자 도구 페이지(admin.html)로 접근한다.
    - [x] 상품 추가 기능이 동작하게 만든다.
    - [x] 상품 수정 기능이 동작하게 만든다.
    - [x] 상품 삭제 기능이 동작하게 만든다.
- [ ] 사용자 설정 페이지 연동
    - [x] "/settings"로 접근하면, 유저를 선택하는 페이지(settings.html)로 접근한다.
    - [ ] 사용자를 선택하면, 이후 요청에서 사용자의 인증 정보가 포함된다.
- [ ] 장바구니 페이지 연동
    - [ ] "/cart"로 접근하면, 장바구니 페이지(cart.html)로 접근한다.
    - [ ] 장바구니 목록을 제거하는 기능이 동작하게 한다.

### service

- [x] 상품 service
    - [x] 상품 생성
    - [x] 상품 목록 조회
    - [x] 상품 수정
    - [x] 상품 삭제

- [ ] 장바구니 service
    - [ ] 장바구니에 상품 추가
    - [ ] 장바구니에 담긴 상품 제거
    - [x] 장바구니 목록 조회
    - [ ] 사용자 정보는 요청 Header의 Authorization 필드를 사용해 인증 처리를 하여 얻습니다.
        - Basic 인증방식을 사용한다.

- [x] 유저 service
    - [x] 저장된 유저 전체를 조회

### domain

- [x] 상품
    - [x] 상품 ID
    - [x] 상품 이름
    - [x] 상품 이미지(url)
    - [x] 상품 가격

- [x] 유저
    - [x] 유저 ID
    - [x] 유저의 이메일
    - [x] 유저의 비밀번호

### validate

- [ ] validate
    - [x] Product
        - [x] 가격 : 0이상인 정수
        - [x] 이름 : 255자까지
        - [x] imageUrl : 확장자 종류 validation(regex)
    - [x] User
        - [x] email : 이메일 형식에 대한 validation

### 시간이 날 경우 추가로 적용하고 싶은 내용

- [ ] handlerInterceptorAdaptor가 아닌 다른 방식을 이용해서 LoginInterceptor 구현
- [ ] 회원가입 기능(+로그인) 만들기
- [ ] 장바구니에서 상품의 수량 추가하기

## 📚 API

### Product

| 기능 | Method | URL             | request body                   |
|----|--------|-----------------|--------------------------------|
| 생성 | POST   | /products       | product{name, imageUrl, price} |
| 수정 | PUT    | /products/{id}  | product{name, imageUrl, price} |
| 삭제 | DELETE | /products/{id}  | x                              |
