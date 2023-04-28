# jwp-shopping-cart

## 요구사항

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

## 코드리뷰 피드백 및 리팩터링 목록

- [x] @RestController의 반환 타입 코드 중복 수정
- [ ] 수정, 삭제 검증 로직 추가
- [ ] 서비스 계층 추가
- [ ] 서비스 테스트 작성
- [x] [수정 모달 폼 이미지 URL undefined 버그 해결](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1178012078)
- DB 관련
    - [ ] [DB 생성 경로 설정, gitignore 수정](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1178018723)
    - [ ] [DB auto increment에 unsigned 사용 관련 학습 후 적용](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1178024440)
- [ ] [Model과 ModelAndView의 차이 학습 뒤 선택하기](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1178008480)
- 응답 상태 코드 검토
    - [x] [자원 생성 응답 코드는 무엇으로 해야 할까?](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1178009514)
    - [x] [아무것도 반환하지 않는다면 어떤 응답 코드를 반환해야 할까?](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1178013053)
- [ ] [DTO 요청 클래스로 분리](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1178005150)
- [ ] [ExceptionHandler 클래스 분리](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1178005150)
- [ ] [RequestMapping 어노테이션 제거 검토](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1178010139)
- [x] [URI에 불필요한 path 없애기](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1178005150)
- [ ] [Dao 제네릭 적용](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1178016852)
- [ ] 실행 테스트의 편의를 위해 더미 데이터 저장하기
