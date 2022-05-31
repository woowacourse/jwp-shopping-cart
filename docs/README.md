## 회원 가입

### Request

```
POST /customers
Content-Type: application/json
```

```json
{
  "email": "123@gmail.com",
  "password": "1234",
  "nickname": "Movie"
}
```

### Response (정상)

```
HTTP/1.1 201 Created
Content-Type: application/json
Content-length:
Location: /customers/100
```

```json
{
  "email": "123@gmail.com",
  "nickname": "Movie"
}
```

### Response (예외, 중복 이메일?)

- 유효성 검사(**서버**에서 처리)
    - **중복된 이메일**
    - 이메일 형식이 지켜지지 않았을 경우
        - @ 유무
        - 공백 유무
        - @ 이후 . 유무
    - 비밀번호 형식
        - 최소 10자리 이상 : 영문자, 숫자, 특수문자 중 3종류 조합
    - 닉네임은 최소 2자리 이상 최대 10자리 이하

```
400 Bad Request
```

---

## 회원 탈퇴

### Request

```
DELETE /customers
Authorization : ????
```

### Response

```
204 no-content
```

### Response (예외)

```
401 Unauthorized
```

---

## 회원 정보 수정

### Request

```
PUT /customers
Content-Type: application/json
Authorization : ????
```

```json
{
  "nickname": "Marco",
  "password": "1234",
  "newPassword": "2345"
}
```

### Response(정상)

```
200 OK`
```

### Response (예외)

- 유효성 검사(클라이언트)
    - 비밀번호 형식
        - 최소 10자리 이상 : 영문자, 숫자, 특수문자 중 3종류 조합
    - 닉네임은 최소 2자리 이상 최대 10자리 이하
- 유효성 검사(서버)
    - 유효하지 않은 토큰이 전달됐을 때 (→ 401)
    - 이전 비밀번호가 현재 비밀번호와 일치하지 않을 경우 (→ 401)
    - 비밀번호 형식 (→400)
        - 최소 10자리 이상 : 영문자, 숫자, 특수문자 중 3종류 조합
    - 닉네임은 최소 2자리 이상 최대 10자리 이하 (→400)

```
401 Unauthorized
```

---

## 로그인

### Request

```
POST /auth/login
Content-Type: application/json
```

```json
{
  "email": "123@gmail.com",
  "password": "1234"
}
```

### Response (정상)

```
200 Ok
Content-Type: application/json
```

```json
{
  "nickname": "Movie",
  "accessToken": "?????"
}
```

### Response (예외)
- 유효성 검사(서버)
    - 존재하지 않는 이메일의 경우
    - 해당 이메일에 비밀번호가 일치하지 않는 경우
```
401 Unauthorized
```
---

## 로그아웃

### Request
- 논의 중
- 토큰을 서버에서 만료시킬 방법이 없음
- 리프레시 토큰? 블랙리스트?
```
POST /auth/logout
Authorization : ????
```
### Response
```
204 no-content
```

## 회원 조회
```
GET /customers
Authorization : ????
```
### Response
```
200 Ok
Content-Type: application/json
```
```json
{
"nickname" : "Movie",
"email" : "adsf@adsf.com"
}
```
