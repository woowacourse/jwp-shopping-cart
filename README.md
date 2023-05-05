# jwp-shopping-cart
## 미션 설명
Spring Web MVC를 이용하여 쇼핑몰의 상품 관리 기능을 구현하는 미션
## 구현할 기능 목록
- [x] 상품 목록 페이지 연동
  - [x] 상품목록 나타내기
- [x] 관리자 페이지
  - [x] 상품 관리 CRUD 활용
- [x] 사용자
  - [x] 이메일
    - [x] null, blank 체크
    - [x] 이메일 형식 체크
  - [x] 비밀번호
    - [x] null, blank 체크
- [x] API
  -[x] 상품
    -[x] 상품 생성 post
    - [x] 상품 전체 불러오기 Get
    - [x] 특정 상품 불러오기(id를 통해)
    - [x] 상품 수정 Put
    - [x] 상품 삭제 Delete
  - [x] 장바구니
    - [x] 장바구니 상품 추가
    - [x] 장바구니 상품 목록 조회
    - [x] 장바구니 상품 삭제
- [x] 페이지 연동
  - [x] 상품목록 페이지 연동
  - [x] 관리자 페이지 연동
  - [x] 사용자 설정 페이지 연동
  - [x] 장바구니 페이지 연동
- [X] 예외처리
  - [x] 상품등록 및 수정 요청 값 예외처리
  - [x] 상품 엔티티 값 예외처리
  - [X] 상품의 잘못된 id 조회 예외처리
## 리팩토링 목록
- [x] controller 분리하기
- [x] 가독성 향상시키기
- [x] 도메인에서 view의 의존성 없애기
- [x] 존재하지 않는 상품(리소스)요청시 404에러로 수정
- [x] 수정 요청을 patch에서 put으로 수정
- [x] 테스트 코드 성공과 실패 케이스를 나누어서 관리하기