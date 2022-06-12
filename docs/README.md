## 페어 규칙

- 쉬는 시간은 따로 없이 쭉 진행한다.
- 페어 교대는 Red, Green, Blue를 돌아가면서 진행한다.
- TDD는 작은 단위부터 진행한다.
- 일일 회고를 진행한다.
- 의견 충돌, 궁금한 점이 발생할 경우 노션에 기록해둔다.

## 요구 사항

- 회원
    - [x] 회원가입을 진행한다. `POST /customers -> 201(Created)`
    - [x] 회원 정보를 조회한다. `GET /customers/me -> 200(OK)`
    - [x] 회원 정보를 수정한다. `PUT /customers/me -> 204(No Content)`
    - [x] 회원을 탈퇴한다. `DELETE /customers/me -> 204(No Content)`
- 로그인
    - [x] 로그인을 진행한다.(JWT 발급) `POST /auth/token -> 200(OK)`
