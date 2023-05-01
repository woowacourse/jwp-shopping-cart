# jwp-shopping-cart

## 1단계 피드백 사항 및 추가 수정 사항
- [x] view의 객체(ProductRequest) 가 Service 에 그대로 전달되었을 때 생기는 문제점
- [x] 컨트롤러에서 Service 에서 사용되는 값들을 알게 되면 안 좋은 점
- [x] View 를 반환하는 컨트롤러와 API 작업을 하는 컨트롤러 분리하기
- [x] Model이 반환되는 과정
- [x] ModelAndView 학습
- [x] 아무것도 반환하지 않을 때 200을 반환한 이유 / 어떤 값을 반환해야 하는지 학습
- [x] Dao 에 제네릭 적용해보기
- [x] update, delete 시 해당하는 id 가 없을 때 예외 핸들링
- [x] Service layer 테스트 추가
- [x] 테스트 DB 분리
- [ ] 테스트 픽스처 적용해보기
- [x] 어플리케이션 초기 실행 시 상품 목록에 더미데이터 추가



## 1단계 기능 요구 사항

- [x] 상품 목록 페이지 연동
  - [x] / url로 접근할 경우 index.html을 통해 상품 목록 페이지를 조회 가능하다.
  - [x] 상품 기본 정보는 상품 ID, 상품 이름, 상품 이미지, 상품 가격이다.

- [x] 관리자 도구 페이지 상품 관리 CRUD API
  - [x] 생성(/admin/product POST)
  - [x] 조회(/admin)
  - [x] 수정(/admin/product/{id} PUT)
  - [x] 삭제(/admin/product/{id} DELETE)
