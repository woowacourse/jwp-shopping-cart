## 회원 가입

### Request

POST /members

Content-Type: application/json
```json
{
	"email" : "123@gmail.com",
	"password" : "1234"
}
```

### Response (정상)
201 Created

### Response (예외, 중복 이메일?)
400 Bad Request

---

## 회원 탈퇴

### Request
DELETE /members

Authorization : ????

### Response
204 no-content

### Response (예외)
401 Unauthorized

---

## 회원 정보 수정

### Request
PUT /members

Content-Type: application/json

Authorization : ????
```json
{
	"password" : "1234",
	"new-password" : "2345"
}
```

### Response(정상)
200 OK`

### Response (예외)
401 Unauthorized

---

## 로그인

### Request
POST /auth/login

Content-Type: application/json
```json
{
	"email" : "123@gmail.com",
	"password" : "1234"
}
```

### Response (정상)
200 Ok

Content-Type: application/json
```json
{
	"accessToken" : "?????"
}
```

### Response (예외)
401 Unauthorized

---

## 로그아웃

### Request
POST /auth/logout

Authorization : ????

### Response
204 no-content
