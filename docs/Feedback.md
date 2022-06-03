# 장바구니 미션

## 1단계 - 1차 피드백

- [ ] `테스트`
    - [ ] 시나리오 작성 후 해당 시나리오를 따라 테스트 코드 작성
    - [ ] Controller Test 추가
- [x] `Auth`
    - [x] `login` 이외의 기능이 인증, 인가에 관련된 부분인지 고민
      - `login` : Auth
      - `이 외 기능` : Customer
- [x] `Exception`
    - [x] 서버에서 에러가 발생한 경우 Bad Request 가 맞는지?
      - `Exception`으로 에러가 발생한다면 500 반환
    - [x] `ExceptionHandler` 코드 중복 제거
- [ ] `Util` - CustomerInformationValidator
    - [ ] Util 클래스로 분리한 목적과 Util 클래스의 정의
- [x] `DB` - `Customer` 테이블
    - [x] name이 unique로 되어있음. -> email로 수정 필요
- [x] ArgumentResolver 기능 구현
