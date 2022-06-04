# 장바구니 🛒

장바구니 미션 저장소

## 기능 요구 사항

- JWT 라이브러리를 활용하여 액세스 토큰 발급 기능
- 발급한 토큰을 이용하여 로그인이 필요한 기능(회원 정보 조회/수정, 회원탈퇴) 요청 시 포함하여 보내고 이를 이용하여 기능이 동작하도록 구현

## API 명세

**로컬에서 html 파일로 확인하기**

1. 아래 명령어를 실행해서 서버를 띄우세요 ☁️
```shell
git clone https://github.com/nailseong/jwp-shopping-cart.git
cd jwp-shopping-cart
./gradlew bootJar
java -jar ./build/libs/jwp-shopping-cart-0.0.1-SNAPSHOT.jar --spring.profiles.active=local
```

2. localhost에 접속해서 문서를 확인하세요 📄
- [인증 관련 기능](http://localhost:8080/docs/auth.html)
- [회원 관련 기능](http://localhost:8080/docs/customer.html)

### 회원가입

- 요청

```json
POST /users

{
  email: "email",
  password: "password",
  nickname: "nickname"
}
```

- 응답
    - 정상 케이스

        ```json
        204 NO_CONTENT
        ```

    - 회원정보 양식이 잘못됐을 때

        ```json
        400 BAD REQUEST
        
        {
        	errorCode: "1000",
        	message: "error message"
        }
        ```

    - 이메일이 중복일 때

        ```json
        400 BAD REQUEST 
        
        {
        	errorCode: "1001",
        	message: "error message"
        }
        ```

### 로그인

- 요청

```json
POST /login

{
  email: "email",
  password: "password"
}
```

- 응답
    - 정상 케이스

        ```json
        200 OK
        
        {
        	accessToken: "accessToken"
        }
        ```

    - 회원정보 양식이 잘못됐을 때

        ```json
        400 BAD REQUEST
        
        {
        	errorCode: "1000",
        	message: "어쩌고 저쩌고"
        }
        ```

    - 존재하지 않는 아이디 비밀번호 사용

        ```json
        400 BAD REQUEST
        
        {
        	errorCode: "1002",
        	message: "어쩌고 저쩌고"
        }
        ```

### 회원 정보 수정

- 요청

```json
PUT /users/me
Authorization : accessToken

{
nickname: "nickname",
password: "password"
}
```

- 응답
    - 정상 케이스

    ```java
    204 NO_CONTENT
    ```

    - 회원정보 양식 문제

    ```java
    400 BAD REQUEST
    
    {
    	errorCode: "1000",
    	message: "어쩌고 저쩌고"
    }
    ```

    - 잘못된 토큰일 때

    ```java
    401 UNAUTHORIZED
    ```

### 회원 정보 요청

- 요청

```json
GET /users/me
Authorization : accessToken
```

- 응답
    - 정상 케이스

    ```java
    200 OK
    
    {
    	email: "email",
    	nickname: "nickname"
    }
    ```

    - 잘못된 토큰일 때

    ```java
    401 UNAUTHORIZED
    ```

### 회원 정보 삭제

- 요청

```json
DELETE /users/me
Authorization : accessToken
```

- 응답
    - 정상 케이스

    ```json
    204 NO_CONTENT
    ```

    - 잘못된 토큰의 경우

    ```json
    401 UNAUTHORIZED
    ```

### 에러 코드

**400 Bad Request**

- 1000 : 회원정보 양식이 잘못됐을 때
- 1001 : 이메일이 중복일 때
- 1002 : 존재하지 않는 아이디 또는 잘못된 비밀번호로 로그인 시도

**500 Internal Server Error**

- 500 : 서버가 요청을 처리하지 못할 때
