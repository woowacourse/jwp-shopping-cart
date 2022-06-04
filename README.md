# 장바구니

장바구니 미션 저장소

## 1단계 api 목록

- **가입**
    - HTTP request

        ```http
        ###### HTTP Request ######
        POST /customers/signUp HTTP/1.1
        Content-Type: application/json
        Host: localhost:8080
        
        {
        	 "userId" : "email@naver.com",
        	 "nickname" : "compi",
        	 "password" : "1234asdf!"
        }
        ```

    - HTTP response

        ```http
        ###### HTTP Response ######
        HTTP/1.1 201 CREATED
        Location: "/customers/{id}"
        ```

- **로그인**
    - HTTP request

        ```http
        ##### HTTP Request ######
        POST /customers/login HTTP/1.1
        Content-Type: application/json
        Host: localhost:8080
        
        {
        		"userId" : "email@email.com",
        		"password": "1234"
        }
        ```

    - HTTP response

        ```http
        ###### HTTP Response ######
        HTTP/1.1 200 OK
        {
         	 "accessToken" : {Bearer token},
           "id" : 1,
        	 "userId" : "email@naver.com",
        	 "nickname" : "compi"
        }
        ```

- **내** **정보 조회**
    - HTTP request

        ```http
        ###### HTTP Request ######
        GET /auth/customers/profile HTTP/1.1
        Authorization: {Bearer token}
        Host: localhost:8080
        ```

    - HTTP response

        ```http
        ###### HTTP Response ######
        HTTP/1.1 200 OK
        
        {
            "id" : 1,
        	  "userId" : "email@naver.com",
        	  "nickname" : "compi"
        }
        ```

- **정보 수정**
    - HTTP request

        ```http
        ###### HTTP Request ######
        PATCH /auth/customers/profile HTTP/1.1
        Authorization: {Bearer token}
        Content-Type: application/json
        Host: localhost:8080
        
        {
           "nickname" : "hunch"
        }
        ```

    - HTTP response

        ```http
        ###### HTTP Response ######
        HTTP/1.1 200 OK
        ```

- **비밀번호 변경**
    - HTTP request

        ```http
        ###### HTTP Request ######
        PATCH /auth/customers/profile/password HTTP/1.1
        Authorization: {Bearer token}
        Content-Type: application/json
        Host: localhost:8080
        
        {
        	 "oldPassword" : 1234	
           "newPassword": 2345
        }
        ```

    - HTTP response

        ```http
        ###### HTTP Response ######
        HTTP/1.1 200 OK
        ```

- **탈퇴**
    - HTTP request

        ```http
        ###### HTTP Request ######
        DELETE /auth/customers/profile HTTP/1.1
        Authorization: {Bearer token}
        Host: localhost:8080
        ```

    - HTTP response

        ```http
        ###### HTTP Response ######
        HTTP/1.1 204 No Content
        ```

- **userId(DB 에서는 username) 중복체크**
    - HTTP request

        ```http
        ###### HTTP Request ######
        GET /customers/check/?userId={userId} HTTP/1.1
        Host: localhost:8080
        ```

    - HTTP response

        ```http
        ###### HTTP Response ######
        HTTP/1.1 200 OK
        ```

- **nickname 중복체크**
    - HTTP request

        ```http
        ###### HTTP Request ######
        GET /customers/check?nickname={nickname} HTTP/1.1
        Host: localhost:8080
        ```

    - HTTP response

        ```http
        ###### HTTP Response ######
        HTTP/1.1 200 OK
        ```

- **password 확인(2단계..)**
    - HTTP request

        ```http
        ###### HTTP Request ######
        POST /auth/customers/match/password HTTP/1.1
        Authorization: {Bearer token}
        Host: localhost:8080
        
        {
        	 "password" : 1234
        }
        ```

    - HTTP response

        ```http
        ###### HTTP Response ######
        HTTP/1.1 200 OK
        ```

### 에러

- 유효하지 않은 토큰

    ```http
    ###### HTTP Response ######
    HTTP/1.1 403 Forbidden
    
    {
        "message" : // 에러메시지
    }
    ```

- 로그인 실패

    ```http
    ###### HTTP Response ######
    HTTP/1.1 401 Unauthorized
    
    {
        "message" : // 에러메시지
    }
    ```

- 리소스가 존재하지 않을 때

    ```http
    ###### HTTP Response ######
    HTTP/1.1 404 Not Found
    
    {
        "message" : // 에러메시지
    }
    ```

- 잘못된 입력

    ```http
    ###### HTTP Response ######
    HTTP/1.1 400 Bad Request
    
    {
        "message" : // 에러메시지
    }
    ```

- 예상되지 않은 예외

    ```
  ###### HTTP Response ######
    HTTP/1.1 400 Bad Request
    {
        "message" : "예상치못한 에러가 발생했습니다."
    }
    ```

## 1단계 기능 구현 목록

**회원가입 기능**

- [x]  회원 가입 API 기능
- [x]  회원 도메인 유효성 검증, 생성 기능
    - username : 이메일 형태
    - password : 영문 필수, 숫자 필수, 특수문자 선택 조합, 8 ~ 16자
    - nickname : 영어, 한글, 숫자만 포함가능, 2 ~ 10자
- [x]  회원을 DB 에 저장하는 기능
    - customer table
- [x]  생성된 회원 리소스의 id 를 반환하는 기능

**로그인 기능**

- [x]  로그인 API 기능
- [x]  아이디에 대한 비밀번호가 일치하는지 검증하는 기능
    - customer table
- [x]  토큰을 발급하는 기능
- [x]  로그인한 회원 정보를 반환하는 기능

**내 정보 조회 기능**

- [x]  내 정보 조회 API 기능
- [x]  DB에서 아이디로 회원을 찾는 기능
    - customer table
- [x]  조회된 회원 정보를 반환하는 기능

**정보 수정 기능**

- [x]  정보를 수정하는 API 기능
- [x]  id 로 회원 리소스 정보를 변경 기능
    - customer table

**비밀번호 변경 기능**

- [x]  비밀변호를 변경하는 API 기능
- [x]  id 로 회원 비밀번호를 변경하는 기능
    - customer table

**탈퇴 기능**

- [x]  탈퇴하는 API 기능
- [x]  id 로 회원을 탈퇴하는 기능
    - cutomer table
    - withdrawal 을 true 로 변경하는 soft delete 방식

**username 중복체크 기능**

- [x]  username 중복체크를 하는 API 기능
- [x]  입력으로 들어온 username 이 기존 username 에 존재하는지 확인하는 기능
    - 이미 존재한다면 예외를 발생시킨다.

**nickname 중복체크 기능**

- [x]  nickname 중복체크를 하는 API 기능
- [x]  입력으로 들어온 nickname 이 기존 nickname 에 존재하는지 확인하는 기능
    - 이미 존재한다면 예외를 발생시킨다.

**password 일치 확인 기능**

- [x]  password 가 일치하는지 확인하는 API 기능
- [x]  입력된 password 가 내 password 와 일치하는지 확인하는 기능
    - 일치하지 않으면 예외를 발생시킨다.

## TODO

- [ ] Dao Optional 처리 고려하기

### 1단계 피드백

- [x] JwtTokenProvider -> 공통 로직은 메서드로 분리할 수 있겠네요!
- [x] dao와 repository에서의 메서드명이 login일 필요가 있을까요?
    - -> 각각 역할에 맞게 메소드명 고민
- [x] 토큰을 생성하는것까지 Service에서 하는것이 맞지 않을까요~??
    - -> 서비스로 옮기자
- [x] TokenRequest -> 어떤 id를 의미하는지 명확하게 나타내면 좋을 것 같아요~
- [x] checkDuplicateUsername -> 파라미터 설정 값이 중복된게 아닌가 해요!
- [ ] CustomerRepository -> 예외 메시지를 넣지 않고 비워둔 이유가 있을까요?
