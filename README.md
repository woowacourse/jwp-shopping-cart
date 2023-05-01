# jwp-shopping-cart
## 미션 설명
Spring Web MVC를 이용하여 쇼핑몰의 상품 관리 기능을 구현하는 미션
## 구현할 기능 목록
- [x] 상품 목록 페이지 연동
  - [x] 페이지 연동
  - [x] 상품목록 나타내기
- [x] 상품 관리 CRUD API 작성
  - [x] 상품 생성 Post(/products)
  - [x] 상품 전체 불러오기 Get(/products)
  - [x] 특정 상품 불러오기(id를 통해)
  - [x] 상품 수정 Patch(/products/{id})
  - [x] 상품 삭제 Delete(/products/{id})
- [x] 관리자 페이지 연동
  - [x] 상품 관리 CRUD 활용
  - [x] 관리자 페이지 연동
- [X] 예외처리
  - [x] 상품등록 및 수정 요청 값 예외처리
  - [x] 상품 엔티티 값 예외처리
  - [X] 상품의 잘못된 id 조회 예외처리
## 리팩토링 목록
- [x] controller 분리하기
- [x] 가독성 향상시키기
- [x] 도메인에서 view의 의존성 없애기
- [ ] 존재하지 않는 상품(리소스)요청시 404에러로 수정