# 장바구니 - 협업 미션

<details>
<summary>매트와 페어 규칙 및 목표</summary>

## 페어 규칙

- 교대 시간은 10분으로 지정한다.
- github과 본인 장비를 활용하여 진행한다.
- 커밋 메시지는 한글로
- 테스트 메서드명은 영어로 하고 DisplayName 을 한글로
- 일일회고를 진행한다. (목표를 잘 지키며 개발했는지 이야기 나누기)

## 목표

### 공통

- 프론트와 협업을 하며 API 를 작성할때 어떤 부분을 중심적으로 논의해야 할지 고민하고, 기록해두기
- 항상 의식적으로 근거를 가지고 코드 짜기

### 매트

- 현재 알고 있는 Spring MVC에 대한 구조를 다시 한 번 인지하며 해당 기술을 적절히 활용하기
- 처음부터 너무 과하게 설계하지 않기
- MVC 를 다시 한번 공부하며 페어의 이해를 돕기 위해 노력하기

### 야호

- ATDD 를 중심으로 개발하기
- auth 에 대한 대략적인 흐름을 파악해보기

</details>

<details>
<summary>1단계 기능 목록</summary>

### 기능 목록

[링크](https://www.notion.so/0f0d2f9b1c4b4f6cb02b0f7215f8cccc)

- 회원가입
    - [x] 모든 필드는 null이 될 수 없다.
    - [x] 이메일 형식에 대한 검증 (test@test.com)
    - [x] 비밀번호는 10자에서 20자 사이
    - [x] 전화번호 형식에 대한 검증 (000-0000-0000)
- [x] 회원 정보 조회
- [x] 로그인
- 회원 정보 수정
    - [x] 모든 필드는 null이 될 수 없다.
    - [x] 전화번호 형식에 대한 검증 (000-0000-0000)
- [x] 회원 탈퇴

### 도메인 설계

- Customer
    - email
    - password
    - address
    - phoneNumber

### 리팩터링/고민사항

- [x] 생성자의 매개변수에 동일한 타입이 존재하는 경우 `빌더 패턴 고려 vs 원시값 포장`
- [x] 원시값 포장
- [x] 동작하는 코드를 우선적으로 개발하기
- [x] customer 생성 및 수정에 관한 validation 추가
- [x] test fixture 만들기
- [x] token validate 추가
- [x] customer 생성자를 정적 팩터리 메서드로 수정

</details>

<details>
<summary>소니의 1단계 첫번째 리뷰</summary>

- dto
    - [x] @Valid 어노테이션을 적절히 활용했는지 확인하기
- service
    - [x] 도메인과 적절한 책임 분배가 이루어졌는지 확인하기  
      ex. AuthService 의 validateCustomer - 비밀번호 검증을 customer 역할로
- Auth
    - [x] springframework에서 제공하는 HTTP 상수 Authorization 사용하기
    - [x] config에서 jwtTokenProvider bean 등록 후 주입해주기
    - [x] Interceptor 사용하지 않은 이유찾기 / 사용하기
- Error
    - [ ] API 명세에 맞게 에러 코드를 세부적으로 나누어 사용하기
        - ex. InvalidCustomerException, NotInCustomerCartItemException
- domain
    - [x] 현재 password를 RawPassword로 수정하고, EncodedPassword 를 따로 만들기
        + Customer 에서는 EncodedPassword 만 받을 수 있도록 수정하기
- test
    - [x] JwtTokenProvider 에 대한 테스트 추가하기
- 개인적으로 리팩터링 하고싶은 부분
    - [x] Authorization 자체가 없는 경우 커스텀 예외 처리

</details>

<details>
<summary>소니의 1단계 두번째 리뷰</summary>

- Service
    - [x] optional.get() 대신 optional.orElseThrow() 사용하기
    - [x] 비밀번호를 검증 로직을 customer로 이동

</details>

<details>
<summary>2단계 기능 목록</summary>

### 기능 목록

[링크](https://puzzled-mongoose-068.notion.site/API-7fd3d9e631e747f895ebced15b351db3)

- 고객(customer) 관련 기능
    - 회원가입
    - 회원 정보 조회
    - 회원 정보 수정
    - 회원 탈퇴
    - [x] username 중복 확인
    - [x] email 중복 확인
    - [x] username, email 중복 확인에 대한 인수테스트 추가
- 상품(product) 관련 기능
    - 상품 조회
    - 상품 전체 조회
    - 상품 추가
    - 상품 삭제
    - [x] product 테이블에 selling (판매중인지) 칼럼 추가
    - [x] product 삭제 시 실제로 delete 하는 것이 아닌 selling=false 로 수정
    - [x] 상품 전체 조회했을 때 delete 되지 않은 product만 보여주도록 수정
    - [x] 상품 필드에 description 추가
- 장바구니(cartItem) 관련 기능
    - 장바구니 조회
    - 장바구니 추가
    - 장바구니 삭제
    - [x] 장바구니 품목 수량 수정
    - [x] cart -> cartItem 으로 변경하기
    - [x] cartItem 테이블에 quantity 칼럼 추가
    - [x] 이미 담겨있는 상품을 다시 담을 경우 수량을 더해 update (insert ignore 사용)
    - [x] username 기반에서 token 기반으로 수정
- 주문(order) 관련 기능
    - 주문하기
    - 주문 단건 조회
    - 모든 주문 내역 조회
    - [x] username 기반에서 token 기반으로 수정

### 레거시 리팩터링

- [x] test fixture 만들어 중복코드 제거하기
- [x] jdbcTemplate 대신 NamedParameterJdbcTemplate과 SimpleJdbcInsert 사용하도록 수정
- [x] controller 중복 Path RequestMapping 으로 제거
- [x] 파라미터 final 제거
- [x] dto로 사용되고 있는 domain 패키지를 dto 패키지로 수정
- [x] domain 새로 만들기
- api 명세 수정
    - [x] 팀 회의로 결정된 api 명세에 맞도록 path 수정
    - [x] 팀 회의로 결정된 api 명세에 맞도록 request, response 수정
    - [x] 팀 회의로 결정된 api 명세에 맞도록 예외처리 수정
- [x] dao에서 조회하는 값이 없느면 error를 반환하던 코드를 Optional을 반환하도록 수정
- [x] PathVariable customerName 를 token 을 이용하도록 수정

</details>

<details>
<summary>소니의 2단계 첫번째 리뷰</summary>

- service
    - [x] CartItemService에서 findCartsByCustomerName의 중복 로직 없애기
        - (hint: findCartIdsByCustomerName 에서 join 사용)
    - [x] Transactional 에서 적절한 옵션 사용하기 (hint: 조회 로직에는 readOnly 옵션)
    - [x] CartItemService에서 updateQuantity가 LoginCustomer에 맞는 사용자의 cartItem인지 확인하기
    - [ ] OrderService에서 for문을 순회하며 db를 조회하는 로직의 비용 문제 해결하기

</details>

<details>
<summary>소니의 2단계 두번째 리뷰</summary>

- repository
  - [ ] for문을 이용해 단건 조회하는 대신 한번에 List로 받아오기
  - [ ] Key를 productId로 하고 Value는 Product로 하는 Map을 만들어 cartItem 만들기

</details>
