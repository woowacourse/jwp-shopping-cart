# 장바구니 미션

## 1단계 - 1차 피드백

- [ ] `테스트`
    - [ ] 시나리오 작성 후 해당 시나리오를 따라 테스트 코드 작성
    - [x] Controller Test 추가
- [x] `Auth`
    - [x] `login` 이외의 기능이 인증, 인가에 관련된 부분인지 고민
        - `login` : Auth
        - `이 외 기능` : Customer
- [x] `Exception`
    - [x] 서버에서 에러가 발생한 경우 Bad Request 가 맞는지?
        - `Exception`으로 에러가 발생한다면 500 반환
    - [x] `ExceptionHandler` 코드 중복 제거
- [x] `Util` - CustomerInformationValidator
    - [x] Util 클래스로 분리한 목적과 Util 클래스의 정의
        - Util클래스 : 특정 비즈니스 로직과 독립적인 기능을 수행하는 클래스
        - 현재 `CustomerInformationValidator` 클래스는 `Customer` 클래스의 값을 검증하는 기능을 수행하기때문에 `Domain`으로 재분리
- [x] `DB` - `Customer` 테이블
    - [x] name이 unique로 되어있음. -> email로 수정 필요
- [x] ArgumentResolver 기능 구현

## 2차 피드백

- [ ] `CustomerController`
    - [ ] 기존 CustomerRequest 외 Request 객체 활용
- [x] `Domain`
    - [x] `Email`, `Password`를 객체로 분리하여 검증 관리
        - 현재 코드에서 Validator로 분리하여 처리하는 것에 대해 객체지향의 역할과 책임의 관점에서 고민
- [x] `Exception`
    - [x] `Exception.class` -> `RuntimeException.class`
        - 500 에러 반환 처리
    - [x] 응답 메세지 한글로 통일화
        - 파악 용이
- [ ] URL
    - `GET /customers`로 개인정보를 조회하는 것이 맞는지
- [x] `Argumnet Resolver`
    - UI 패키지로 분리한 이유, config 패키지로 분리에 대해 고민
- [x] `AuthorizationExtractor`
    - 메서드 분리
- [ ] `CustomerService`
    - [ ] `Service`에서 DTO 응답
        - Controller 가 아닌 Service에서 응답한 이유
    - [ ] `CustomerResponse.from(customer)`, 정적 팩터리 메서드 활용
- [x] DTO 클래스 기본 생성자의 접근제어자 private

### 시나리오

Feature: 지하철 노선 관리
Scenario: 지하철 노선 생성
When 지하철 노선 생성을 요청하면
Then 지하철 노선 생성이 성공한다.
Scenario: 지하철 노선 목록 조회
Given 지하철 노선 생성을 요청하고

- Feature : 회원 관리
    - Scenario : 회원 가입 성공
        - When : 회원 가입을 요청하면
        - Then : 회원 가입이 성공한다.
    - Scenario : 회원 가입 실패
        - Given : 기존 회원 목록에 입력한 Email이 존재할 경우
        - When : 회원 가입을 요청하면
        - Then : 회원 가입이 실패한다.
    - Scenario : 로그인 성공
        - Given : 회원 가입을 요청하고
        - When : 로그인을 요청하면
        - Then : 로그인에 성공한다.
    - Scenario : 로그인 실패(존재하지 않는 ID)
        - When : 회원 목록에 존재하지 않는 정보로 로그인을 요청하면
        - Then : 회원 가입이 실패한다.
    - Scenario : 로그인 실패(올바르지 않는 Password)
        - Given : 회원 가입을 요청하고
        - When : 올바르지 않은 비밀번호로 로그인을 요청하면
        - Then : 로그인에 실패한다.
    - Scenario : 회원 정보 조회 성공
        - Given : 회원 가입을 요청하고
        - Given : 로그인을 요청하고
        - When : 회원 조회를 요청하면
        - Then : 회원 정보 조회에 성공한다.
    - Scenario : 회원 정보 조회 실패
        - Given : 회원 가입을 요청하고
        - When : 회원 정보 조회를 요청하면
        - Then : 회원 정보 조회에 실패한다.
    - Scenario : 회원 수정 성공
        - Given : 회원 가입을 요청하고
        - Given : 로그인을 요청하고
        - When : 회원 수정을 요청하면
        - Then : 회원 수정에 성공한다.
    - Scenario : 회원 수정 실패
        - Given : 회원 가입을 요청하고
        - When : 회원 수정을 요청하면
        - Then : 회원 수정에 실패한다.
    - Scenario : 회원 삭제 성공
        - Given : 회원 가입을 요청하고
        - Given : 로그인을 요청하고
        - When : 회원 삭제를 요청하면
        - Then : 회원 삭제에 성공한다.
    - Scenario : 회원 삭제 실패
        - Given : 회원 가입을 요청하고
        - When : 회원 삭제를 요청하면
        - Then : 회원 삭제에 실패한다.
