# jwp-shopping-cart

## 요구사항

### step1

- [x] 상품 목록 페이지 연동
    - [x] url과 컨트롤러 매핑
    - [x] 도메인 객체 구현
    - [x] 상품 조회 기능 구현

- [x] 상품 관리 CRUD API 작성
    - [x] DB 설계
    - [x] API 설계
    - [x] DAO 구현

- [x] 관리자 도구 페이지 연동
    - [x] url과 컨트롤러 매핑
        - [x] 관리자 도구 페이지 url 매핑
    - [x] 상품 추가 기능 구현
    - [x] 상품 수정 기능 구현
    - [x] 상품 삭제 기능 구현

- [x] 각 요청에 맞는 Dto로 분리 (id 필요 유무)
- [x] 통합 테스트 작성
- [x] 검증 로직 추가
  - 수정, 삭제 시 존재하는 id인지 확인

### step2

- [x] 사용자 기능 구현
  - [x] 사용자 객체 구현
    - 사용자는 아이디, 이메일, 패스워드, 전화번호의 정보를 가진다.

- [x] 사용자 설정 페이지 연동
  - [x] 사용자에 대한 CRUD 기능 구현
    - [x] DB 설계
    - [x] API 구현
    - [x] DAO 구현

- [x] 장바구니 기능 구현
  - [x] 장바구니 TABLE 설계

  - [x] 장바구니 페이지 연동 
    - [x] table 설계 
    - [x] 사용자의 Email과 Password로 Basic Authentication을 할 수 있다.
    - [x] productId로 사용자의 장바구니에 상품을 추가할 수 있다.
    - [x] productId로 사용자의 장바구니에서 상품을 삭제할 수 있다.
    - [x] 장바구니에 담긴 모든 상품을 조회할 수 있다.

### 발생 예외 정리
- product
  - Product
    - 이름이 1~30자가 아닌 경우 -> IllegalArgumentException
    - 이미지 URL이 1~1000자가 아닌 경우 -> IllegalArgumentException
    - 가격이 영~십억이 아닌 경우 -> IllegalArgumentException
  - update, delete
    - productId가 유효하지 않은 경우 -> NoSuchElementException

- member 패키지
  - Member
    - IllegalArgumentException 발생
    - Email: 이메일 패턴을 만족하지 않는 경우 ("^[A-Za-z0-9+_.-]+@(\\S+)$")
    - Password: 8~40자가 아닌 경우, 영문자 숫자 조합이 아닌 경우
    - PhoneNumber: 10~20자가 아닌 경우, 오직 숫자로만 이루어지지 않은 경우
  - add
    - 이메일이 중복인 경우 -> DuplicateEmailException

- cartitems 패키지
  - 공통
    - 인증되지 않은 사용자인 경우 -> UnauthorizedMemberException
  - add
    - 존재하지 않는 상품을 추가할 경우 -> NoSuchElementException
    - 이미 담은 상품을 다시 추가할 경우 -> DuplicateCartItemException
  - delete
    - 존재하지 않는 상품을 삭제할 경우 -> NoSuchElementException
