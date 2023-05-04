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
- [x] 수정, 삭제 id 검증 로직 추가
- [x] 서비스 계층 추가
- [x] 서비스 테스트 작성
- [x] [수정 모달 폼 이미지 URL undefined 버그 해결](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1178012078)
- DB 관련
    - [x] [DB 생성 경로, gitignore 수정](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1178018723)
    - [x] [DDL id에 정수값 unsigned 적용](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1178024440)
- [x] [뷰 컨트롤러 Model과 ModelAndView 사용 방식의 차이 학습 뒤 선택하기](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1178008480)
- 응답 상태 코드 검토
    - [x] [자원 생성 응답 코드는 무엇으로 해야 할까?](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1178009514)
    - [x] [아무것도 반환하지 않는다면 어떤 응답 코드를 반환해야 할까?](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1178013053)
- [x] [Controller 내부 DTO 클래스를 요청 DTO 클래스로 분리](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1178005150)
- [x] [ExceptionHandler 클래스 분리](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1178005150)
- [x] [RequestMapping 어노테이션 제거 검토](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1178010139)
    - /product/.../... 와 같이 여러 계층이 존재한다면 RequestMapping이 없는게 파악하기 더 좋을 것 같다.
    - 하지만 현재 코드에서는 모두 같은 깊이의 계층이므로 (delete 요청의 자원 명시 제외) RequestMapping이 가독성을 높여준다고 볼 수 있지 않을까?
- [x] [URI에 불필요한 path 없애기](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1178005150)
- [x] [Dao 제네릭 적용](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1178016852)
- [ ] [예측하지 못한 예외 Handler 추가](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1181092506)
- [x] [PUT vs PATCH](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1181092924)
- [x] [서비스 테스트에서 Mocking을 사용한 이유](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1181093100)
- [ ] [REST API 관련 학습 및 자원 표기 방법 변경](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1184665185)
- [x] [현재 상황에서 서비스 계층 트랜잭션 어노테이션, read-only 옵션의 필요성](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1181093361)
- [ ] 실행 테스트의 편의를 위해 더미 데이터 저장하기
