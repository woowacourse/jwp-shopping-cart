# jwp-shopping-cart
## 상품 목록 페이지 연동
- [x] 상품 목록이 노출되는 페이지를 만든다.
- [x] "/" url로 접근할 경우 상품 목록 페이지를 조회할 수 있다.

- [x] 상품이 가지고 있는 정보는 아래와 같다.
  - 상품 ID
  - 상품 이름
  - 상품 이미지
  - 상품 가격

## 상품 관리 CRUD API 작성
- [x] 상품 생성 API
- [x] 상폼 목록 조회 API
- [x] 상품 수정 API
- [x] 상품 삭제 API

## 관리자 도구 페이지 연동
- [x] admin.html 파일과 상품 관리 CRUD API를 이용하여 상품 관리 페이지를 완성한다.
- [x] "/admin" url로 접근할 경우 관리자 도구 페이지를 조회할 수 있어야 한다.
- [x] 상품 추가, 수정, 삭제 기능이 동작해야 한다.

## Validate
- [x] 상품을 생성할 때 Name은 Null을 허용하지 않는다.
- [x] 상품을 생성할 때 ImageUrl은 Null을 허용하지 않는다.
- [x] 상품을 수정할 때 Name은 Null을 허용하지 않는다.
- [x] 상품을 수정할 때 ImageUrl은 Null을 허용하지 않는다.

## step2
- [x] 사용자 기능 구현
  - [x] 사용자 기본 정보
    - email
    - password
- [ ] 사용자 설정 페이지 연동
  - [ ] settings.html 파일을 이용해서 사용자를 선택하는 기능을 구현
  - [ ] /settings url로 접근할 경우 모든 사용자의 정보를 확인하고 사용자를 선택
  - [ ] 사용자 설정 페이지에서 사용자를 선택하면, 이후 요청에 선택한 사용자의 인증 정보가 포함
- [ ] 장바구니 기능 구현
  - [ ] 장바구니와 관련된 아래 기능을 구현합니다.
    - [ ] 장바구니에 상품 추가
    - [ ] 장바구니에 담긴 상품 제거
    - [ ] 장바구니 목록 조회
  - [ ] 사용자 정보는 요청 Header의 Authorization 필드를 사용해 인증 처리(인증 방식은 Basic 인증을 사용)
- [ ] 장바구니 페이지 연동
  - [ ] 1단계에서 구현한 상품 목록 페이지(/)에서 담기 버튼을 통해 상품을 장바구니에 추가
  - [ ] cart.html 파일과 장바구니 관련 API를 이용하여 장바구니 페이지를 완성
  - [ ] /cart url로 접근할 경우 장바구니 페이지를 조회
  - [ ] 장바구니 목록을 확인하고 상품을 제거하는 기능을 동작

