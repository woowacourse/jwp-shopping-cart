# 장바구니

장바구니 미션 저장소

## ✏️ Code Review Process

[텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 1단계 요구사항

- [ ] 회원가입
    - [x] account, nickname, password, address, phoneNumber 을 받아 회원을 생성한다.
    - [x] account 의 글자수가 4~15를 벗어나면 예외를 발생한다.
    - [x] nickname 의 글자수가 2~10을 벗어나면 예외를 발생한다.
    - [x] password 의 글자수가 8~20을 벗어나면 예외를 발생한다.
    - [x] password 가 영어 대문자, 소문자, 숫자 중 2종류 이상을 조합하지 않으면 예외를 발생한다.
    - [ ] address 의 글자수가 255자를 초과하면 예외를 발생한다.
    - [ ] phoneNumber 의 양식이 일치하지 않으면 예외를 발생한다.
        - [ ] start, middle, last 의 형식을 지닌다.
        - [ ] start 는 3글자, middle과 last는 4글자이다.
    - [x] account 가 중복되면 예외를 발생한다.
    - [x] account 에 대문자가 포함되어 있으면 소문자로 변경한다.
    - [x] account 에 특수문자가 포함되어 있으면 제거한다.

- [ ] 로그인 아이디 중복 확인 API
    - [ ] account 가 중복되면 false를 반환한다.

- [ ] Token기반의 로그인
    - [ ] account 와 password 를 받아 토큰을 생성한다.

- [ ] 회원 정보 수정
    - [ ] nickname, address, phoneNumber 을 받아 회원 정보를 수정한다.
    - [ ] request header 에 token 이 포함되지 않으면 예외를 발생한다.
    - [ ] nickname 의 글자수가 2~10을 벗어나면 예외를 발생한다.
    - [ ] address 의 글자수가 255자를 초과하면 예외를 발생한다.
    - [ ] phoneNumber 의 양식이 일치하지 않으면 예외를 발생한다.
        - [ ] start, middle, last 의 형식을 지닌다.
        - [ ] start 는 3글자, middle과 last는 4글자이다.

- [ ] 회원탈퇴
    - [ ] request header 에 token 이 포함되지 않으면 예외를 발생한다.
    - [ ] 비밀번호를 받아 회원을 탈퇴한다.
