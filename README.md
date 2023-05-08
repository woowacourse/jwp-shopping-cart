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

- [x] 사용자 기능 구현
    - [x] 사용자 설정 페이지 연동
    - [x] 사용자 객체 구현
    - [x] 사용자 테이블 설계, 초기화 설정

- [x] 장바구니 기능 구현
    - [x] 장바구니 페이지 연동
    - [x] 장바구니 아이템 테이블 설계
    - [x] 장바구니 관리 API 설계
    - [x] 장바구니 아이템 객체 구현
    - [x] 장바구니 관리 API 구현, 페이지 - DB 연동
        - [x] DAO 구현
        - [x] 아이템 등록 기능 구현
        - [x] 아이템 조회 기능 구현
        - [x] 아이템 삭제 기능 구현
    - [x] 관리자 상품 삭제 시 장바구니에 담긴 상품 정보 처리 (FK 참조 문제)
        - 일단 같이 삭제되게 만들기
        - 추후 삭제 여부 컬럼 만들어서 관리할 수도 있다.

## API 명세

### 화면 조회 API

|           | method | URI       |
|-----------|--------|-----------|
| 홈 화면      | GET    | /         |
| 관리자 화면    | GET    | /admin    |
| 사용자 선택 화면 | GET    | /settings |
| 장바구니 화면   | GET    | /cart     |

### [상품 관리 REST API](docs/PRODUCTS_API.md)

### [장바구니 관리 REST API](docs/CART_ITEMS_API.md)

## ERD

![image](https://user-images.githubusercontent.com/97426362/236440197-ab9a1102-6569-45db-9b3e-93a1407ce3ed.png)

## 코드리뷰 피드백 및 리팩터링 목록

### 1단계

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
- [x] [예측하지 못한 예외에 대한 Handler 추가](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1181092506)
- [x] [PUT vs PATCH](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1181092924)
- [x] [서비스 테스트에서 Mocking을 사용한 이유](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1181093100)
- [x] [REST API 관련 학습 및 자원 표기 방법 변경](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1184665185)
    - 더 넓은 의미에서 사용할 수 있는, 복수형으로 자원 표기하도록 RestController의 URI 변경함
- [x] [현재 상황에서 서비스 계층 트랜잭션 어노테이션, read-only 옵션의 필요성](https://github.com/woowacourse/jwp-shopping-cart/pull/175#discussion_r1181093361)

### 2단계

- [x] 실행 테스트의 편의를 위해 더미 데이터 저장하기
- [x] DTO Null 검증 예외 메시지 구체화
- [x] 사용자 인증 절차 리팩터링
    - [x] Interceptor, ArgumentResolver 사용하여 중복 제거
    - [x] 통합 테스트 작성
        - [x] 테스트 픽스쳐 적용, 테스트 DB 초기데이터와 일치하도록 설정
- [x] [테스트 실패 케이스 해결 (테스트 독립적으로 구성하기)](https://github.com/woowacourse/jwp-shopping-cart/pull/337#discussion_r1186779720)
    - [ ] 정확한 원인 파악 (해당 오류와 @Sql 어노테이션의 인과관계?)
        - 테스트 독립성 문제이기 때문에 인메모리 DB를 사용하면 고려할 필요 없는 문제이기는 하다.
- [x] [DB 초기화 전략 수정 (로컬 DB에 걸맞게 사용하기)](https://github.com/woowacourse/jwp-shopping-cart/pull/337#discussion_r1186787097)
    - 테스트 DB의 경우 같은 DataSource(@JdbcTest)나 ApplicationContext(@SpringBootTest 내 테스트들)를 공유하는 경우,
    - auto_increment 값 문제로 매번 테이블을 아예 새로 만들어주거나 관련 처리를 해줘야 한다. => 그럴 바엔 인메모리로 변경함
    - 그럼에도 테스트에서도 로컬 DB를 사용할 수 있는 방법은 없을까?(테스트 간 의존도가 너무 높아서 디버깅이 어려워 일단 중단함)
- [x] [ResController 확장성 고려하여 어노테이션 대신 ResponseEntity 사용](https://github.com/woowacourse/jwp-shopping-cart/pull/337#discussion_r1186780234)
- [x] [에러 발생 시 클라이언트 쪽 alert 띄우기](https://github.com/woowacourse/jwp-shopping-cart/pull/337#discussion_r1186780402)
- [x] [/cartitems URL 구분자 추가](https://github.com/woowacourse/jwp-shopping-cart/pull/337#discussion_r1186780736)
- [ ] [이메일, 비밀번호 검증 로직 추가](https://github.com/woowacourse/jwp-shopping-cart/pull/337#discussion_r1186781377)
- [ ] [인증 관련 클래스 클린 코드](https://github.com/woowacourse/jwp-shopping-cart/pull/337/files/64e714428fd62e79b2d59f61f6a0159fc8d7f071#r1186781824)
    - [ ] final 키워드 누락 수정
    - [ ] 불필요한 상수 선언, 소문자 변환 등 수정
    - [ ] 조건문 정리
    - [ ] 함수 분리
    - [x] 공백 삭제
- [x] [AuthenticationPrincipal 불필요한 메서드 삭제](https://github.com/woowacourse/jwp-shopping-cart/pull/337#discussion_r1186782502)
- [x] [AuthenticationInterceptor 반환값 수정](https://github.com/woowacourse/jwp-shopping-cart/pull/337#discussion_r1186782820)
- [x] [인증 Extractor Bean 의존성 주입으로 변경(어떤 객체를 빈 등록 해야할까?)](https://github.com/woowacourse/jwp-shopping-cart/pull/337#discussion_r1186783618)
- [x] [외래키 사용 이유, 장단점](https://github.com/woowacourse/jwp-shopping-cart/pull/337/files/64e714428fd62e79b2d59f61f6a0159fc8d7f071#r1186786702)
- [x] [패키지 구조와 Dao 추상화 관련](https://github.com/woowacourse/jwp-shopping-cart/pull/337#discussion_r1186786575)
- [ ] snake case vs carmel case 통일
- [ ] 예외메시지 형식 / 마침표 등 통일, 관리하기 쉽게 만들기
