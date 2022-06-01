## 장바구니 미션

## 기능 요구 사항

- 회원가입, 로그인, 회원 정보 조회, 비밀번호 수정, 회원 탈퇴를 제공하는 API 구현

### 회원가입

- 회원가입 request : POST /users
    - [x] 중복되는 이메일이 존재하는 경우, 예외 메세지와 함께 상태코드 400을 반환한다.
    - [x] 중복되는 이름이 존재하는 경우, 예외 메세지와 함께 상태코드 400을 반환한다.
    - [x] 이름, 이메일 혹은 비밀번호가 공백이거나 누락된 경우, 상태코드 400을 반환한다.
    - [x] 이메일이 지정된 형식(ex. alpha@naver.com)이 아닌 경우, 상태코드 400을 반환한다.
    - [x] 요청 성공시, 상태코드 201을 반환한다.

### 로그인

- 로그인 request : POST /login
    - [x] 이메일 혹은 비밀번호 중 하나라도 공백이거나 누락된 경우, 상태코드 400을 반환한다.
    - [x] 이메일이 지정된 형식(ex. alpha@naver.com)이 아닌 경우, 상태코드 400을 반환한다.
    - [x] DB에 저장되지 않은 이메일으로 로그인을 시도하는 경우, 상태코드 400을 반환한다.
    - [x] 이메일에 대응되는 비밀번호가 아닌 경우, 상태코드 400을 반환한다.
    - [x] 요청 성공시, 토큰과 함께 상태코드 200을 반환한다.

### 회원 정보 조회

- 회원 정보 조회 request : GET /users/me
    - [x] 유효하지 않은 토큰을 사용할 경우, 상태코드 401을 반환한다.
    - [x] 요청 성공시, 상태코드 200을 반환한다.

### 비밀번호 수정

- 비밀번호 조회 request : PATCH /users
    - [x] 유효하지 않은 토큰을 사용할 경우, 상태코드 401을 반환한다.
    - [x] 기존 비밀번호 혹은 신규 비밀번호 중 하나라도 공백이거나 누락된 경우, 상태코드 400을 반환한다.
    - [x] 기존 비밀번호가 DB에 저장된 비밀번호와 일치하지 않은 경우, 상태코드 400을 반환한다.
    - [x] 요청 성공시, 상태코드 200을 반환한다.

### 회원 탈퇴

- 회원 탈퇴 request : DELETE /users
    - [ ] 유효하지 않은 토큰을 사용할 경우, 상태코드 401을 반환한다.
    - [ ] 비밀번호가 공백이거나 누락된 경우, 상태코드 400을 반환한다.
    - [ ] 기존 비밀번호가 DB에 저장된 비밀번호와 일치하지 않은 경우, 상태코드 400을 반환한다.
    - [ ] 요청 성공시, 상태코드 204를 반환한다.