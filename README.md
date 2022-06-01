# 장바구니

장바구니 미션 저장소

## ✏️ Code Review Process

[텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 1단계 요구사항

- [x] 회원가입
    - [x] account, nickname, password, address, phoneNumber 을 받아 회원을 생성한다.
    - [x] account 의 글자수가 4~15를 벗어나면 예외를 발생한다.
    - [x] nickname 의 글자수가 2~10을 벗어나면 예외를 발생한다.
    - [x] password 의 글자수가 8~20을 벗어나면 예외를 발생한다.
    - [x] password 가 대소문자, 숫자, 특수문자가 모두 조합되지 않으면 예외를 발생한다.
    - [x] address 의 글자수가 255자를 초과하면 예외를 발생한다.
    - [x] phoneNumber 의 양식이 일치하지 않으면 예외를 발생한다.
        - [x] start, middle, last 의 형식을 지닌다.
        - [x] start 는 3글자, middle과 last는 4글자이다.
    - [x] account 가 중복되면 예외를 발생한다.
    - [x] account 에 대문자가 포함되어 있으면 소문자로 변경한다.
    - [x] account 에 특수문자가 포함되어 있으면 제거한다.

- [x] Token 기반의 로그인
    - [x] account 와 password 를 받아 토큰을 생성한다.

- [x] 회원 정보 조회
  - [x] request header 로 토큰을 받아 회원 정보를 조회한다. 
  - [x] request header 에 token 이 포함되지 않으면 예외를 발생한다.
  - [x] 알맞은 회원정보가 없을 시, 회원 정보가 없다는 예외를 발생한다.

- [x] 회원 정보 수정
    - [x] nickname, address, phoneNumber 을 받아 회원 정보를 수정한다.
    - [x] request header 에 token 이 포함되지 않으면 예외를 발생한다.
    - [x] nickname 의 글자수가 2~10을 벗어나면 예외를 발생한다.
    - [x] address 의 글자수가 255자를 초과하면 예외를 발생한다.
    - [x] phoneNumber 의 양식이 일치하지 않으면 예외를 발생한다.
        - [x] start, middle, last 의 형식을 지닌다.
        - [x] start 는 3글자, middle과 last는 4글자이다.

- [x] 회원탈퇴
    - [x] request header 에 token 이 포함되지 않으면 예외를 발생한다.
    - [x] 비밀번호를 받아 회원을 탈퇴한다.
    - [x] 비밀번호가 일치하지 않으면 예외를 발생한다.

- [x] JWT 의존성 변경
- [ ] Error Response DTO 생성
- [ ] Service 에 Transactional 적용
- [ ] 비밀번호 암호화
- [ ] Interceptor 써보기
- [ ] JwtProvider 설정 값 생성자 주입으로 변경
- [ ] RestDocs 적용

