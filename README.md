# jwp-shopping-cart

## 기능 목록
### step1
- [x] 상품 목록 페이지 연동
  - [x] index.html 파일 내 TODO 주석을 참고하여 설계한 상품 정보에 맞게 코드를 변경
  - [x] index.html 파일을 이용하여 상품 목록이 노출되는 페이지를 완성
  - [x] '/' url로 접근할 경우 상품 목록 페이지를 조회
  - [x] 상품 기본 정보 : 상품 ID, 상품 이름, 상품 이미지, 상품 가격

- [x] 상품 관리 CRUD API 작성
  - [x] Create : POST - /admin
  - [x] Read : GET - /admin
  - [x] Update : POST - /admin/{id}
  - [x] Delete : DELETE - /admin/{id}

- [x] 관리자 도구 페이지 연동
  - [x] admin.html, admin.js 파일 내 TODO 주석을 참고하여 코드를 변경
  - [x] admin.html 파일과 상품 관리 CRUD API를 이용하여 상품 관리 페이지를 완성
  - [x] '/admin' url로 접근할 경우 관리자 도구 페이지를 조회

### step2
- [x] 사용자 기능 구현
  - [x] 사용자가 가지고 있는 정보 : email, password
  
- [ ] 사용자 설정 페이지 연동
  - [x] settings.html, settings.js 파일 내 TODO 주석을 참고하여 설계한 사용자 정보에 맞게 코드를 변경
  - [x] settings.html 파일을 이용해서 사용자를 선택하는 기능을 구현
  - [x] /settings url로 접근할 경우 모든 사용자의 정보를 확인하고 사용자를 선택 가능
  - [ ] 사용자 설정 페이지에서 사용자를 선택하면, 이후 요청에 선택한 사용자의 인증 정보가 포함
  
- [ ] 장바구니 기능 구현
  - [x] 장바구니에 상품 추가
  - [x] 장바구니에 담긴 상품 제거
  - [x] 장바구니 목록 조회
  - [ ] 사용자 정보는 요청 Header의 Authorization 필드를 사용해 인증 처리를 하여 획득
  - [ ] 인증 방식은 Basic 인증 사용
  
- [ ] 장바구니 페이지 연동
  - [ ] cart.html, cart.js 파일 내 TODO 주석을 참고하여 설계한 장바구니 정보에 맞게 코드를 변경
  - [ ] 장바구니 상품 추가 
    - [ ] 1단계에서 구현한 상품 목록 페이지(/)에서 담기 버튼을 통해 상품을 장바구니에 추가
  - [ ] 장바구니 목록 조회 및 제거
    - [ ] cart.html 파일과 장바구니 관련 API를 이용하여 장바구니 페이지를 완성
    - [ ] /cart url로 접근할 경우 장바구니 페이지를 조회
    - [ ] 장바구니 목록을 확인하고 상품을 제거하는 기능을 동작하도록

---
- cart 테이블은 컬럼은 둘다 FK인데 어떻게 처리해야할까?
- cart에 product 추가하는 경우 반환값에 location 설정해주는게 맞나? 애초에 created가 맞을까? 리소스 생성은 아닌데?
