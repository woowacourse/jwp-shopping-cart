# 기능목록

## 페이지 연동

### Content-Type : test/Html

- [x] "/" : 현재 상품들의 목록을 가지고 있는 html 파일을 보낸다.
- [x] "/admin" : 현재 상품들을 관리할 수 있는 페이지를 보낸다.
    - [x] 상품 추가 요청을 보낼 수 있다.
    - [x] 상품 삭제 요청을 보낼 수 있다.
    - [x] 상품 수정 요청을 보낼 수 있다.
- [x] "/settings" : 현재 등록된 유저들을 목록을 보여준다.
    - [x] 유저를 선택해서 로그인할 수 있다.
        - [x] 로그인은 localStorage 에 Base64 형태로 인코딩된 유저 정보를 저장함으로써 이뤄진다.
- [x] "/cart" : 현재 로그인한 유저의 카트 상품들을 보여준다
    - [예외] 만약 유저가 로그인하지 않았다면 카트 페이지를 얻을 수 없다.
        - [x] Basic Authentication 으로 인증을 진행한다.
        - [x] Authorization 헤더에 Basic 형태의 이메일, 비밀번호를 보낸다
        - [x] 해당 인증 정보는 Base64 형태로 인코딩하여서 보내야 한다.
    - [x] 로그인한 유저의 상품 제거 요청을 보낼 수 있다.

## API

### 상품 관련 API

#### 상품 추가

- URI : "/products"
- Request
    - method : "POST"
    - require
        - 상품 정보를 body 에 json 형태로 담아 보내야한다.
            - 상품 이름을 보내야한다
            - 상품 이미지 URI 를 보내야한다
            - 상품 가격을 보내야 한다.

- Response
    - fail(400 response)
        - 이미 등록된 상품명을 등록하려 한다면 예외가 발생한다
        - Request 의 require 조건을 만족하지 않으면 예외가 발생한다
    - success(201 response)
        - header : 추가된 상품을 볼 수 있는 위치인 "/admin" 이 포함되어 있다.
        - body : none

#### 상품 수정

- URI : "/products/{id}"
- Request
    - method : "PATCH"
    - require
        - 상품 정보를 body 에 json 형태로 담아 보내야한다.
            - 상품 이름을 보내야한다
            - 상품 이미지 URI 를 보내야한다
            - 상품 가격을 보내야 한다.

- Response
    - fail(400 response)
        - 존재하지 않는 상품에 대한 수정을 보낸다면 예외가 발생한다
            - 상품의 존재 여부는 id 를 통해서 결정된다
        - Request 의 require 조건을 만족하지 않으면 예외가 발생한다

    - success(201 response)
        - header : 추가된 상품을 볼 수 있는 위치인 "/admin" 이 포함되어 있다.
        - body : none

#### 상품 삭제

- URI : "/products/{id}"
- Request
    - method : "DELETE"
    - require
        - 삭제하고자 하는 상품 id 를 uri 에 포함시켜야한다.

- Response
    - fail(400 response)
        - 존재하지 않는 상품에 대한 수정을 보낸다면 예외가 발생한다
            - 상품의 존재 여부는 id 를 통해서 결정된다
        - Request 의 require 조건을 만족하지 않으면 예외가 발생한다

    - success(201 response)
        - header : none
        - body : none

### 카트 관련 API

#### 카트 아이템 조회

- URI : "/cart"
- Request
    - method : "GET"
    - require
        - 로그인 한 상태에서 자신의 인증 정보를 함께 보내야한다
            - Basic Authentication 으로 인증을 진행한다.
            - Authorization 헤더에 Basic 형태의 이메일, 비밀번호를 보낸다
            - 해당 인증 정보는 Base64 형태로 인코딩하여서 보내야 한다.

- Response
    - fail
        - 401 response
            - Authorization 에 값이 없다면 실패한다
            - Authorization 에 해당하는 유저 정보가 존재하지 않으면 실패한다
    - success(200 response)
        - header : none
        - body : json 형태로 상품들의 id, name, image, price 를 보낸다

#### 카트 아이템 추가

- URI : "/cart/{id}"
- Request
    - method : "POST"
    - require
        - 상품 정보를 URI 에 ID 로 담아야한다
        - 로그인 한 상태에서 자신의 인증 정보를 함께 보내야한다
            - Basic Authentication 으로 인증을 진행한다.
            - Authorization 헤더에 Basic 형태의 이메일, 비밀번호를 보낸다
            - 해당 인증 정보는 Base64 형태로 인코딩하여서 보내야 한다.

- Response
    - fail
        - 401 response
            - Authorization 에 값이 없다면 실패한다
            - Authorization 에 해당하는 유저 정보가 존재하지 않으면 실패한다
        - 400 response
            - 존재하지 않는 상품의 아이디를 보내면 실패한다
            - ID를 URI 에 담지 않았다면 예외가 발생한다
    - success(201 response)
        - header : 추가된 상품을 볼 수 있는 위치인 "/cart" 가 포함되어 있다.
        - body : none

#### 카트 아이템 삭제

- URI : "/cart/{id}"
- Request
    - method : "DELETE"
    - require
        - 삭제하고자 하는 상품의 id 를 URI 에 포함시켜야한다.
        - 로그인 한 상태에서 자신의 인증 정보를 함께 보내야한다
            - Basic Authentication 으로 인증을 진행한다.
            - Authorization 헤더에 Basic 형태의 이메일, 비밀번호를 보낸다
            - 해당 인증 정보는 Base64 형태로 인코딩하여서 보내야 한다.

- Response
    - fail
        - 401 response
            - Authorization 에 값이 없다면 실패한다
            - Authorization 에 해당하는 유저 정보가 존재하지 않으면 실패한다
        - 400 response
            - 존재하지 않는 상품의 아이디를 보내면 실패한다
            - ID를 URI
    - success(204 response)
        - header : none
        - body : none
