# 장바구니 협업

# 회원 가입

### 회원 가입 시 필요한 정보

- email - 가입 이전에 중복 체크
- nickname
- password

method: POST

URL: “/api/members”

content-type: application/json

### 이메일 중복 체크 성공 시

method: GET

URL: “/members/check-email?email={email}”

content-type: application/json

응답 코드: 200 OK

```
{
  unique: "true"
}
```

### 이메일 중복 체크 과정에서 규칙 검증 실패 시

응답 코드: 400 Bad Request

### 회원 가입 성공 시

응답 코드: 201 Created

Location 주지 않기

### 회원 가입 실패 시

응답 코드: 400 Bad Request

1. 비밀번호 규칙 검증 실패
2. 이메일 규칙 검증 실패
3. 이메일 중복 검증 실패
4. 닉네임 규칙 검증 실패
5. 항목 누락

1 ~ 5 모두 프론트에서 먼저 검증하지만 만약의 경우를 대비해 백엔드에서 한번 더 검증

1. 서버가 뻗은 경우 (500 Internal Server Error)

```jsx
{
  message: "...",
}
```

### 비밀번호 규칙

```jsx
최소 8자, 최대 20 자
최소 하나의 문자, 하나의 숫자 및 하나의 특수문자 :
        "^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,20}$"
```

### 이메일 규칙

```java
"^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
```

### 닉네임 규칙

```java
한글 최대 5자
```

# 로그인

### 로그인 형식

- email
- password

method: POST

URL: “/api/login”

content-type: application/json

### 로그인 성공 시

응답 코드: 200 OK

responseBody:

```jsx
{
  nickname: "...", 
  token: "..."
}
```

- 모든 조회는 토큰에 저장된 정보를 가지고 함

### 로그인 실패 시

응답 실패: 400 Bad Request

# 로그아웃

프론트엔드에서 JWT 토큰 삭제

# 회원 권한 인가

request

```
Authorization: Bearer {token}
```

### 로그인이 안되어 있으면

응답 코드: 401 Unauthorized

### 토큰의 시간이 만료되었으면

응답 코드: 401 Unauthorized (인증 안된 경우와 메시지 다르게 구분)

- 403은 생략

  ### 다른 회원 정보에 접근하려고 하면

  응답 코드: 403 Forbidden


# 회원 정보 페이지

### 비밀번호 확인

method: POST

URL: “/api/members/password-check”

응답 코드: 200 OK

```
{
  success: "true"
}
```

### 회원 정보 조회

method: GET

URL: “/api/members/me” + 토큰 정보로 식별

응답 코드: 200 OK

response body

```
{
  email: "...",
  nickname: "...",
}
```

### 회원 정보 수정

method: PATCH

(닉네임 + @) URL: “/api/members/me” + 토큰 정보로 식별

request body

```
{
  nickname: "...",
}
```

(비밀번호) URL: “/api/members/password” + 토큰 정보로 식별

request body

```json
{
  password: "..."
}
```

응답 코드: 204 No Content

### 회원 탈퇴

method: DELETE

URL: “/api/members/me” + 토큰 정보로 식별

응답 코드: 204 No Content