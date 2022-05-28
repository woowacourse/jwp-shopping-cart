# 기능 요구 사항

# 회원가입

- [ ]  email, password, nickname을 받아 회원 정보를 등록한다.
    - [ ]  email 중복 검증
    - [ ]  email 규칙 검증
    - [ ]  password 규칙 검증
    - [ ]  nickname 규칙 검증
    - [ ]  누락 정보 검증

# 로그인(토큰 기반)

- [ ]  email, password를 받아 로그인을 할 수 있다.
    - [ ]  email, password가 db에 저장된 값과 일치하는지 검증
    - [ ]  jwtToken을 생성
    - [ ]  nickname과 token을 response로 반환

# 회원 정보

## 회원 정보 식별

- [ ]  token으로 회원을 식별하여 db에서 회원을 가져온다.

## 회원 정보 조회

- [ ]  비밀번호를 검증한다.
- [ ]  email, nickname을 응답한다.

## 회원 정보 수정

- [ ]  nickname을 수정할 수 있다.
- [ ]  password를 수정할 수 있다.

nickname와 password는 회원 가입 시와 같은 조건으로 검증한다.

## 회원 탈퇴

- [ ]  db에서 해당 회원 정보를 삭제한다.