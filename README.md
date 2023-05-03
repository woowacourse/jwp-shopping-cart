# jwp-shopping-cart

## 미션 요구사항

- [x] 상품 목록 페이지 연동 ([GET] / )([GET] /products)
- [x] 관리자 상품 목록 페이지 연동 ([GET] /admin)
- [x] 상품 관리 CUD API 작성
    - [x] 상품 생성
        - [POST] /products
    - [x] 상품 수정
        - [PUT] /products/{id}
    - [x] 상품 삭제
        - [DELETE] /products/{id}


어떤 사용자의 장바구니에 상품을 추가하거나 제거할 것인지에 대한 정보는 
Basic Auth를 이용하여 인증을 하도록 합니다. 사용자 설정은 설정페이지에서 다룹니다.

장바구니 기능은 인증 기반으로 기능을 구현하고 
장바구니에 상품 추가, 제거, 목록 조회가 가능해야 합니다. 
이 때 필요한 도메인 설계와 DB 테이블 설계 그리고 이에 맞는 패키지 설계에 유의해주세요.

- [ ] 사용자 설정 페이지 연동 
      - [ ] settings.html, settings.js 파일 내 TODO 주석을 참고하여 설계한 사용자 정보에 맞게 코드변경
      - [ ] 사용자를 선택하는 기능 
      - [ ] /settings url로 접근한 경우 모든 사용자를 확인하고 선택 가능
      - [ ] 사용자를 선택하면 이후 요청에 선택한 사용자의 인증 정보 포함
- [ ] 사용자 기능 구현
    - [ ] email
    - [ ] password
- [ ] 장바구니 페이지 연동
      - [ ] cart.html, cart.js 파일 내 TODO 주석을 참고하여 설계한 장바구니 정보에 맞게 코드변경
      - [ ] 상품 목록 페이지에서 담기 버튼을 통해 장바구니를 추가
- [ ] 장바구니 기능 구현
    - [ ] 장바구니에 상품추가
    - [ ] 장바구니에 담긴 상품 제거
      - [ ] 장바구니 목록 조회
