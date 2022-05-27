# 장바구니

장바구니 미션 저장소

## 기능 요구 사항

- [ ] 회원가입
    - 회원가입 시에는 Body로 email, nickName, password를 입력받는다.
    - `성공`
        - 201 Created를 반환한다.
        - Location Header에 `/members/{id}` 를 반환한다.
    - `예외`
        - email
            - 중복된 이메일로 가입을 요청
            - 8자 이상 50자 이하가 아닌 경우
            - 이메일 형식이 아닌 경우
            - 이메일 내에 공백이 존재할 경우
        - nickName
            - 중복된 닉네임으로 가입을 요청
            - 1자 이상 10자 이하가 아닌 경우
            - 닉네임 내에 공백이 존재할 경우
        - password
            - 8자 이상 20자 이하가 아닌 경우
            - 패스워드 내에 공백이 존재할 경우
- [ ] 로그인
    - 로그인 시에는 Body로 email, password를 입력받는다.
    - `성공`
        - 200 OK를 반환한다.
        - Body로 accessToken, expirationTime을 반환한다.
    - `예외`
        - 로그인 실패 시 `401 Unauthorized`를 반환한다.
- [ ] 회원 정보 조회
    - `성공`
        - 200 OK를 반환한다.
        - Body로 id, email, nickName을 반환한다.
- [ ] 회원 정보 수정
    - 회원정보 수정 시 Body로 nickName을 입력받는다.
    - `성공`
        - 200 OK를 반환한다.
        - Body로 id, email, nickName을 반환한다.
    - `예외`
        - 중복된 닉네임으로 수정을 요청 (이전 닉네임과 같은 경우 예외가 아니다.)
        - 1자 이상 10자 이하가 아닌 경우
        - 닉네임 내에 공백이 존재할 경우
- [ ] 회원 탈퇴
    - `성공`
      - 204 No Content를 반환한다.
