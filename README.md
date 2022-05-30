## auth
- [x] post /api/auth
    - [x] id, 비밀번호 확인
    - [x] token 생성

## member
- [x] post /api/members
    - [X] email, password, name 요청
    - [x] 비밀번호 암호화
    - [x] 저장

- [x] get /api/members/me
    - [x] header token
    - [x] id, email, name 반환

- [x] put /api/members/me/name
    - [x] header token, body name
    - [x] name 수정

- [ ] put /api/members/me/password
    - [ ] header token, body oldPassword, newPassword
    - [ ] newPassword가 oldPassword와 같은 경우 예외
    - [ ] newPassword 암호화
    - [ ] password 수정

- [ ] delete /api/members/me
    - [ ] header token, body password
    - [ ] password가 일치하는 지 확인
    - [ ] 삭제


## interceptor
- [x] 유효한 토큰인지 확인

## argumentResolver
- [x] 토큰을 기반으로 멤버 정보를 가져온다.
