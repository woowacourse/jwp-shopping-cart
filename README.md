# 장바구니
장바구니 미션 저장소

#1단계 요구사항 정리

- [x] 회원가입
  - [x] 요청으로 이름, 이메일, 비밀번호를 받는다.
  - [x] 응답으로 이름, 이메일을 반환한다. 201
- [x] 로그인
  - [x] 요청으로 이메일, 비밀번호를 받는다.
  - [x] 응답으로 이름, 이메일, 토큰을 반환한다. 200
- [x] 회원 정보 조회
  - [x] 요청으로 토큰을 받는다.
  - [x] 응답으로 이름, 이메일을 반환한다. 200
- [ ] 회원 정보 수정 (비밀번호 수정)
  - [ ] 요청으로 기존 비밀번호와 바꿀 비밀번호, 토큰을 받는다.
  - [ ] 응답으로 이름을 반환하다. 200
- 회원 탈퇴
  - [ ] 요청으로 비밀번호와 토큰을 받는다.
  - [ ] 204

[API 문서](https://www.notion.so/brorae/1-API-c10e17f6fdc940bbb2379ec7e07b1cb4)
## ✏️ Code Review Process
[텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## dd

1. 사용자가 email, password를 입력한다("/users/login")
2. 컨트롤러가 받아서 서비스로 준다. AuthController, AuthService
3. email, password 를 검증한다. AuthService
4. 성공하면 토큰을 생성한다. AuthService
5. 토큰과 email. password를 응답한다. AuthService, AuthController


