## auth
- [ ] post /api/auth
    - [ ] id, 비밀번호 확인
    - [ ] token 생성

## member
- [ ] post /api/members
    - [ ] email, password, name 요청
    - [ ] 비밀번호 암호화
    - [ ] 저장

- [ ] get /api/members/me
    - [ ] header token
    - [ ] id, email, name 반환

- [ ] put /api/members/me/name
    - [ ] header token, body name
    - [ ] name 수정

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
- [ ] 유효한 토큰인지 확인

## argumentResolver
- [ ] 토큰을 기반으로 멤버 정보를 가져온다.
