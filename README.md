## 1단계 기능 목록
- 테이블과 데이터 추가
    - customer username, password, phoneNumber, address
- customer
    - username
        - 영어와 숫자가 아니면 예외 발생
        - 3자 이상 15자 이하가 아니면 예외 발생
    - password
        - 영어와 숫자가 둘 다 존재하지 않으면 예외 발생
        - 8자 이상 20자 이하가 아니면 예외 발생
    - phoneNumber
        - 열한자의 숫자가 아니면 예외 발생
    - address

## End Point

### 회원 가입

- `POST /api/customers/signup`
- not required authorization
- http request

```json
{
	"username" : "myUsername123",
	"password" : "myPassword1234",
	"phoneNumber" : "01012345678",
	"address" : "성담빌딩"
}
```

- http response

```json
201 created
empty body
```

### 로그인

- `POST /api/customers/login`
- required authorization
- http request

```json
{
	"username" : "myUsername123",
	"password" : "myPassword1234"
}
```

- http response

```json
200 ok
{
	"accessToken" : "jwt.token.here"
}
```

### 회원 정보 조회

- `GET /api/customers`
- required authorization
- http response

```json
200 ok
{
	"customer" : {
		"username" : "myUsername123",
		"phoneNumber" : "01012345678",
		"address" : "성담빌딩"
	}
}
```

### 회원 정보 수정

- `PUT /api/customers`
- required authorization
- http request

```json
{
	"phoneNumber" : "01087654321",
	"address" : "루터회관"
}
```

- http response

```json
204 no-content
empty body
```

### 패스워드 변경

- `PATCH /api/customers/password`
- required authorization
- http request

```json
{
	"password" : "changePassword123"
}
```

- http response

```json
204 no-content
empty body
```

### 회원 탈퇴

- `DELETE /api/customers`
- requried authorization
- http response

```json
204 no-content
empty body
```

## Error Response

```json
{
	"error" : {
		"messages" : [
			"error1", "error2"
		]
}
```

1. 아이디 패스워드 불일치 : 401 unauthorized
2. 회원가입 시 유저 패턴 : 400 bad request
    1. 영어, 숫자만 가능
    2. 3자 이상 15자 이하
3. 회원가입 시 패스워드 패턴 : 400 bad request
    1. 영어, 숫자 항상 포함
    2. 8자 이상 20자 이하
4. 만료 토큰, 잘못된 코튼 : 401 unauthorized
