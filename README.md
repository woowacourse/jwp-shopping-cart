# jwp-shopping-cart

## 2단계 기능 요구사항
- [x] 사용자 기능 구현
  - [x] 사용자 정보(email, password)
- [x] 사용자 설정 페이지 연동
  - [x] /settings 로 접근 시 모든 사용자 정보 확인 및 선택 가능
  - [x] 사용자를 선택하면 이후 요청에 선택한 사용자의 인증정보 포함됨
- [x] 장바구니 기능 구현
  - [x] 장바구니에 상품 추가
    - [x] 상품목록 페이지(/) 에서 담기 버튼 클릭 시 장바구니에 추가 ("/", post)
  - [x] 장바구니에 담긴 상품 제거 ("/carts/{id}", delete)
  - [x] 장바구니 목록 조회
    - [x] /carts 로 접근 시 장바구니 페이지 조회 ("/carts", get)
  - [x] 사용자 정보는 요청 Header의 Authorization 필드를 사용해 인증 처리
    - [x] 인증 방식은 Basic 인증 사용
      ```
            Authorization: Basic ZW1haWxAZW1haWwuY29tOnBhc3N3b3Jk
            type: Basic
            credentials : email:password를 base64로 인코딩한 문자열

## 1단계 피드백 사항 및 추가 수정 사항
- [x] view의 객체(ProductRequest) 가 Service 에 그대로 전달되었을 때 생기는 문제점
- [x] 컨트롤러에서 Service 에서 사용되는 값들을 알게 되면 안 좋은 점
- [x] View 를 반환하는 컨트롤러와 API 작업을 하는 컨트롤러 분리하기
- [x] Model이 반환되는 과정
- [x] ModelAndView 학습
- [x] 아무것도 반환하지 않을 때 200을 반환한 이유 / 어떤 값을 반환해야 하는지 학습
- [x] Dao 에 제네릭 적용해보기
- [x] update, delete 시 해당하는 productId 가 없을 때 예외 핸들링
- [x] Service layer 테스트 추가
- [x] 테스트 DB 분리
- [x] 어플리케이션 초기 실행 시 상품 목록에 더미데이터 추가    

  <br/>
- [x] DB 초기 설정 문제 해결하기
- [x] 존재하지 않는 id의 update, delete 메서드를 호출하면 예외가 발생하는지 확인
- [x] 특정 도메인에 대한 데이터가 존재하지 않아서 발생하는 에러는 DB에서 관리하는 게 맞을까요? 아니면 서비스 레이어에서 관리하는 게 맞을까요?
- [x] Service 테스트에 대해서 Mocking하는 게 좋을 지 통합 테스트를 하는 게 좋을지


## 1단계 기능 요구 사항

- [x] 상품 목록 페이지 연동
  - [x] / url로 접근할 경우 index.html을 통해 상품 목록 페이지를 조회 가능하다.
  - [x] 상품 기본 정보는 상품 ID, 상품 이름, 상품 이미지, 상품 가격이다.

- [x] 관리자 도구 페이지 상품 관리 CRUD API
  - [x] 생성(/admin/product POST)
  - [x] 조회(/admin)
  - [x] 수정(/admin/product/{productId} PUT)
  - [x] 삭제(/admin/product/{productId} DELETE)
