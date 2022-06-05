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
