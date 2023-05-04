# jwp-shopping-cart

## 미션 요구사항

- [x] 관리자 상품 목록 페이지 연동 ([GET] /admin)

- [x] 상품 목록 페이지 연동 ([GET] / )
- [x] 상품 관리 CUD API 작성
    - [x] 상품 생성
        - [POST] /products
    - [x] 상품 수정
        - [PUT] /products/{id}
    - [x] 상품 삭제
        - [DELETE] /products/{id}


- [x] 사용자 설정 페이지 연동
    - [x] [GET] /settings 
    - [x] url로 접근한 경우 모든 사용자를 확인하고 선택 가능
- [x] 사용자 기능 구현
    - [x] 사용자를 선택하는 기능
    - [x] 사용자를 선택하면 이후 요청에 선택한 사용자의 인증 정보 포함
  

- [x] 장바구니 페이지 연동
    - [x] [GET] /cart
- [x] 장바구니 기능 구현
    - [x] 장바구니에 상품추가
      - [x] [POST] /carts
    - [x] 장바구니에 담긴 상품 제거
      - [x] [DELETE] /carts/{cartId}
    - [x] 장바구니 목록 조회
      - [x] [GET] /carts
