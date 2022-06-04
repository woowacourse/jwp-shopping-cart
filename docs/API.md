# 장바구니 협업

# 회원 관련 기능

---
## 이메일 중복 체크
### HTTP Request

```jsx
GET /api/members/email-check?email=email@email.com HTTP/1.1
```

### HTTP Response - 이메일 중복 체크 성공 시

```jsx
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json

{
    unique: "true"
}
```

### HTTP Response - 이메일 중복 체크 실패 시

```jsx
HTTP/1.1 400 Bad Request


{
    message: ~~
}
```

- 이메일 형식이 잘못되었을 경우
---
## 회원 가입
### HTTP Request

```jsx
POST /api/members HTTP/1.1
Content-Type: application/json

{
    "email" : "email@email.com",
    "nickname" : "닉네임",
    "password" : "password123!"
}
```

### HTTP Response - 회원 가입 성공 시

```jsx
HTTP/1.1 201 Created
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
```

### HTTP Response - 회원 가입 실패 시

```jsx
HTTP/1.1 400 Bad Request

{
    message: ~~
}
```

- 누락된 항목이 존재할 경우
- 중복되는 이메일이 존재할 경우
- 이메일 형식이 잘못되었을 경우

    ```java
    "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
    ```

- 닉네임 형식이 잘못되었을 경우

    ```java
    한글로만 최소 1자 최대 5자
    "^[ㄱ-ㅎ가-힣]{1,5}$"
    ```

- 비밀번호 형식이 잘못되었을 경우

    ```java
    최소 8 자, 최대 20 자 최소 하나의 문자, 하나의 숫자 및 하나의 특수 문자
    "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$"
    ```
---
## 로그인
### HTTP Request

```jsx
POST /api/login HTTP/1.1
Content-Type: application/json

{
    "email" : "email@email.com",
    "password" : "password123!"
}
```

### HTTP Response - 로그인 성공 시

```jsx
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json

{
    "nickname" : "닉네임",
    "token" : "accessToken"
}
```

### HTTP Response - 로그인 실패 시

```jsx
HTTP/1.1 400 Bad Request

{
    message: ~~
}
```

- 잘못된 이메일 혹은 비밀번호를 입력 하였을 경우
- 이메일 형식이 잘못되었을 경우
- 닉네임 형식이 잘못되었을 경우
- 비밀번호 형식이 잘못되었을 경우
---
## 비밀번호 확인
### HTTP Request

```jsx
POST /api/members/password-check HTTP/1.1
Content-Type: application/json
Authorization: Bearer accessToken

{
    "password" : "password123!"
}
```

### HTTP Response - 비빌번호 확인 성공 시

```jsx
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json

{
    "success": "true"
}
```
---
## 회원 정보 조회
### HTTP Request

```jsx
GET /api/members/me HTTP/1.1
Authorization: Bearer accessToken
```

### HTTP Response - 성공 시

```jsx
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json

{
    "email" : "email@email.com",
    "nickname" : "닉네임"
}
```
---
## 회원 정보 수정
### HTTP Request

```jsx
PATCH /api/members/me HTTP/1.1
Content-Type: application/json
Authorization: Bearer accessToken

{
  "nickname" : "다른닉네임"
}
```

### HTTP Response - 회원 정보 수정 성공 시

```jsx
HTTP/1.1 204 No Content
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
```

### HTTP Response - 회원 정보 수정 실패 시

```jsx
HTTP/1.1 400 Bad Request

{
    message: ~~
}
```
- 잘못된 형식의 닉네임으로 수정하려는 경우
---
## 회원 비밀번호 수정
### HTTP Request

```jsx
PATCH /api/members/password HTTP/1.1
Content-Type: application/json
Authorization: Bearer accessToken

{
    "password" : "otherpassword123!"
}
```
### HTTP Response - 비밀번호 수정 성공 시

```jsx
HTTP/1.1 204 No Content
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
```
### HTTP Response - 비밀번호 수정 실패 시

```jsx
HTTP/1.1 400 Bad Request

{
    message: ~~
}
```
- 잘못된 형식의 비밀번호로 수정하려는 경우
---
# 회원 탈퇴
### HTTP Request

```jsx
DELETE /api/members/me HTTP/1.1
Content-Type: application/json
Authorization: Bearer accessToken
```

### HTTP Response - 성공 시

```jsx
HTTP/1.1 204 No Content
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
```