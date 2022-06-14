# API 명세

# 1. 고객(Customer)

---

## 회원가입

**HTTP request**

```text
    POST/api/customers HTTP/1.1
    Content-Type:application/json
    Accept:application/json
    Content-Length:35
    Host:localhost:8080

    {
      "email":"test@gmail.com"
      "password":"password0!"
      "username":"루나"
    }
```

**HTTP response**

```text
    HTTP/1.1 201Created
    Vary:Origin
    Vary:Access-Control-Request-Method
    Vary:Access-Control-Request-Headers
    Location:**/login**
    Content-Type:application/json
    Content-Length:47
```

## 로그인

**HTTP request**

```text
    POST/api/auth/login HTTP/1.1
    Content-Type:application/json
    Accept:application/json
    Content-Length:35
    Host:localhost:8080

    {
      "email":"test@gmail.com"
      "password":"password0!"
    }
```

**HTTP response**

```text
    HTTP/1.1 200Ok
    Vary:Origin
    Vary:Access-Control-Request-Method
    Vary:Access-Control-Request-Headers
    Location:/
    Content-Type:application/json
    Content-Length:47

    {
        "accessToken":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjE2NTQzMTEzMDIsImV4cCI6MTY1NDMxNDkwMn0.pvn__FNuQWXlyzImVDpGIIQ5-A8e7QS6f0dKiggk8cw"
    }
```

## 회원 정보 조회

**HTTP request**

```text
    GET/api/customers/me HTTP/1.1
    Content-Type:application/json
    Accept:application/json
    Content-Length:35
    Host:localhost:8080
    Authorization:Bearer XXXXXXXXXXXXXX
```

**HTTP response**

```text
    HTTP/1.1 200Ok
    Vary:Origin
    Vary:Access-Control-Request-Method
    Vary:Access-Control-Request-Headers
    Content-Type:application/json
    Content-Length:47

    {
      "email":"test@gmail.com"
      "username":"루나"
    }
```

## 회원 정보 수정

### 비밀번호 수정

**HTTP request**

```text
    PATCH/api/customers/me?target=password HTTP/1.1
    Content-Type:application/json
    Accept:application/json
    Content-Length:35
    Host:localhost:8080
    Authorization:Bearer XXXXXXXXXXXXXX

    {
      "oldPassword":"oldPassword0!"
      "newPassword":"newPassword0!"
    }
```

**HTTP response**

```text
    HTTP/1.1 200Ok
    Vary:Origin
    Vary:Access-Control-Request-Method
    Vary:Access-Control-Request-Headers
    Location:/login
```

### 일반 정보 수정

**HTTP request**

```text
    PATCH/api/customers/me?target=generalInfo HTTP/1.1
    Content-Type:application/json
    Accept:application/json
    Content-Length:35
    Host:localhost:8080
    Authorization:Bearer XXXXXXXXXXXXXX

    {
        "username":"다오"
    }
```

**HTTP response**

```text
    HTTP/1.1 200Ok
    Vary:Origin
    Vary:Access-Control-Request-Method
    Vary:Access-Control-Request-Headers

    {
      "email":"test@gmail.com"
      "username":"다오"
    }
```

## 회원 탈퇴

**HTTP request**

```text
    DELETE/api/customers/me HTTP/1.1
    Content-Type:application/json
    Accept:application/json
    Content-Length:35
    Host:localhost:8080
    Authorization:Bearer XXXXXXXXXXXXXX

    {
        "password":"newPassword0!"
    }
```

**HTTP response**

```text
    HTTP/1.1 204No Content
    Vary:Origin
    Vary:Access-Control-Request-Method
    Vary:Access-Control-Request-Headers
    Location:/
```

---

# 에러

**에러 상태 코드는 헤더에, 예외 메시지는 body에**

- **에러 response(JSON)**
    ```text
    {
        "errorCode”: 1001,
        "message": "No message available"
    }
    ```

  <br>
- **회원가입** `[400, Bad Request]`

  **ErrorCode 1000번대는 `회원가입`**

  | 에러 상황 | 에러 코드 | 에러 메세지 |
                | :------------: | :-------------: |:------------: |
  | 이메일 중복 | 1001  | Duplicated Email |

<br>

- **로그인** `[400, Bad Reqeust]`

  **ErrorCode 2000번대는 `로그인`**

  | 에러 상황 | 에러 코드 | 에러 메세지 |
                | :------------: | :-------------: |:------------: |
  | 이메일 또는 패드워드가 맞지 않음 | 2001  | Login Fail |
  | 이메일 형식만 잘못됨 | 4001  | Invalid Email |

<br>

- **회원 정보 조회 / 수정 / 탈퇴`[401, Unauthorized]`**
    - 비밀번호 수정 페이지는 따로

  | 에러 상황 | 에러 코드 | 에러 메세지 |
                | :------------: | :-------------: |:------------: |
  | 기존 패스워드 불일치 | 3001 | Incorrect Password |
  | 새 패스워드 형식이 맞지 않음 | 4002 | Invalid Password |
  | 토큰 만료 또는 없음 | 3002 | Invalid Token |

    - camelCase로 작성하기

<br>

- **입력 형식** `[400, Bad Request]`

  | 에러 상황 | 에러 코드 | 에러 메세지 |
                | :------------: | :-------------: |:------------: |
  | 이메일 형식이 맞지 않음 | 4001 | Invalid Email |
  | 패스워드 형식이 맞지 않음 | 4002 | Invalid Password |
  | 유저 네임 글자 수가 맞지 않음 | 4003 | Invalid Username |

<br>

- **정보조회** `[404, Not Found]`

  | 에러 상황 | 에러 코드 | 에러 메세지 |
                  | :------------: | :-------------: |:------------: |
  | 회원 조회시 없는 경우 | 6004 | Not Exist Customer |
