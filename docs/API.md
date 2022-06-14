# API 명세

# 1. 고객(Customer)

---

## 회원가입

**HTTP request**

```
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

```
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

```
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

```
    HTTP/1.1 200Ok
    Vary:Origin
    Vary:Access-Control-Request-Method
    Vary:Access-Control-Request-Headers
    Location:/
    Content-Type:application/json
    Content-Length:47

    {
    "accessToken":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImdqZHVkZG5zQGdtYWlsLmNvbSJ9.XsJFHuJAdFPEto1WF__QGZoAOIIX2eJSn6xu0iJCGIQ"
    }
```

## 회원 정보 조회

**HTTP request**

```
    GET/api/customers/me HTTP/1.1
    Content-Type:application/json
    Accept:application/json
    Content-Length:35
    Host:localhost:8080
    **Authorization:Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImdqZHVkZG5zQGdtYWlsLmNvbSJ9.XsJFHuJAdFPEto1WF__QGZoAOIIX2eJSn6xu0iJCGIQ**
```

**HTTP response**

```
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

```
    PATCH/api/customers/me?target=password HTTP/1.1
    Content-Type:application/json
    Accept:application/json
    Content-Length:35
    Host:localhost:8080
    **Authorization:Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImdqZHVkZG5zQGdtYWlsLmNvbSJ9.XsJFHuJAdFPEto1WF__QGZoAOIIX2eJSn6xu0iJCGIQ**

    {
    "oldPassword":"oldPassword0!"
    "newPassword":"newPassword0!"
    }
```

**HTTP response**

```
    HTTP/1.1 200Ok
    Vary:Origin
    Vary:Access-Control-Request-Method
    Vary:Access-Control-Request-Headers
    Location:/login
```

### 일반 정보 수정

**HTTP request**

```
    PATCH/api/customers/me?target=generalInfo HTTP/1.1
    Content-Type:application/json
    Accept:application/json
    Content-Length:35
    Host:localhost:8080
    **Authorization:Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImdqZHVkZG5zQGdtYWlsLmNvbSJ9.XsJFHuJAdFPEto1WF__QGZoAOIIX2eJSn6xu0iJCGIQ**

    {
    "username":"다오"
    }
```

**HTTP response**

```
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

```
    DELETE/api/customers/me HTTP/1.1
    Content-Type:application/json
    Accept:application/json
    Content-Length:35
    Host:localhost:8080
    **Authorization:Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImdqZHVkZG5zQGdtYWlsLmNvbSJ9.XsJFHuJAdFPEto1WF__QGZoAOIIX2eJSn6xu0iJCGIQ**

    {
    "password":"newPassword0!"
    }
```

**HTTP response**

```
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
```
    {
        "errorCode”: 1001,
        "message": "No message available"
    }
```

## 에러코드 정의

| 에러코드 | 에러메시지 |
|:------:|:-------:|
| 1001 | 이메일 중복 |
| 2001 | 로그인 실패 |
| 3001 | 기존 패스워드 불일치 |
| 3002 | 인증토큰 에러 |
| 4001 | 이메일 형식 에러 |
| 4002 | 패스워드 형식 에러 |
| 4003 | 사용자이름 형식 에러 |
