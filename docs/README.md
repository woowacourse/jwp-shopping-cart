## 🚀 1단계 - 회원 기능

### 장바구니 서비스의 회원 기능 구현하기
+ [x] 회원가입
  + [ ] 비밀번호 규칙이 맞는지 확인 - (추후적용)
    + 대문자 하나 이상, 소문자 하나 이상, 특수문자, 숫자, 8-15글자 
  + [x] 아이디 중복이면 예외
+ [x] 로그인
  + id, 비밀번호 잘못되었을 때 예외
  + 정상적으로 로그인 될 경우 token과 name 넘긴다.
+ [x] 수정
  + 비밀번호가 일치하면 수정 할 수 있다.
  + name 수정 가능하다.
  + 비밀번호가 일치하지 않을 때 예외
+ [x] 탈퇴
  + 비밀번호가 일치해야 탈퇴할 수 있다.
  + 비밀번호가 틀릴경우 예외
  
## API 명세

- 백엔드
    - 1단계
        - 회원가입
        - 로그인
        - 수정
        - 탈퇴

### 회원가입

`POST` /customers

```json
// request
{
  "loginId": string,
  "name": string,
  "password": string
}
```

```json
// response

// HEADER
// Location: "/customers/me"

// 201
{
	"loginId": string,
	"name": string
}

// 400 Bad Request
```


### 로그인

`POST` /login

```json
// request
{
 	"loginId": string,
	"password": string
}
```

```json
// response 

// 200
{
 	"accessToken": string,
	"name": string
}

// 401 Unauthorized (로그인 실패)
```

- id, 비밀번호 잘못되었을 때


### 정보 조회

`GET` /customers/me

```tsx
headers: {
      Authorization: `Bearer ${accessToken}`,
},
```

```json
// 200 OK response

{
	"loginId": string, 
	"name": string
}

//401 Unauthorized
//토큰이 유효하지 않은 경우

//404 Not Found
//존재하지 않는 회원일 경우

// 현재는 body에 String으로 오류 메세지가 response 됨
{
	"조회 실패!" // 2차에서는 JSON객체로 보내자
}
```


### 수정

`PUT` /customers/me

```json
// response

// 200
{
	"name": string
}

// 400 Bad Request
// 비밀번호 일치하지 않을때
// 아이디가 이메일 형식이 아닐 때

// 404 Not Found
// 존재하지 않는 회원일 때
```

1. **들어가서 수정하고 비밀번호 쳐야 확정**

```json
headers: {
      Authorization: `Bearer ${accessToken}`,
},
```

```json
// request

{
	"loginId": string, 
	"name": string,
	"password": string // 수정 확정용 비밀번호 
}
```
### 탈퇴

`DELETE` /customers/me

- 탈퇴를 비밀번호 한번 더 치고 탈퇴시키기

```json
headers: {
      Authorization: `Bearer ${accessToken}`,
},
```

```json
// request

{
	"password": string,
}
```

```json
// response 

**// 204 no content**

// 400 Bad Request
// 비밀번호 일치하지 않을때

// 404 Not Found
// 존재하지 않는 회원일 경우

//토큰이 존재하지 않는 경우
```