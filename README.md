# 🧺 장바구니

장바구니 미션 저장소

## 1단계 - 회원 기능

### 회원 가입

- 회원 가입 `URL: “/api/members”`
    - Method: POST
    - 성공 시 201 Created
    - 실패 시 400 Bad Request
        - 이메일 중복 검증 실패
        - 이메일 규칙 검증 실패
        ```java
        "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
        ```
        - 닉네임 규칙 검증 실패
        ```java
        한글 최대 5자
        ```
        - 비밀번호 규칙 검증 실패
        ```java
        최소 8자, 최대 20자 최소 하나의 문자, 하나의 숫자 및 하나의 특수 문자 :
        "^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,20}$"
        ```
        - 항목 누락
        - 서버가 뻗은 경우 (500 Internal Server Error)
    - 필요한 정보
        - email
        - nickname
        - password

- email 중복 체크 / 규칙 검증 `URL: “/api/members/check-email?email={email}”`
    - Method: GET
    - 성공 시 200 Ok
  ```javascript
    {
        "success": "true"
    }
  ```
    - 실패 시 400 Bad Request

### 로그인

- 로그인 `URL: “/api/login”`
    - MethodL POST
    - 성공 시 200 OK
      ```javascript
      {
        nickname: "...",
        token: "...",
      }
      ```
    - 실패 시 400 Bad Request
    - 필요한 정보
        - email
        - password

### 로그아웃

- 프론트엔드에서 JWT 토큰을 삭제한다.

### 회원 정보

- 회원 권한 인가
    - request: `Authorization: Bearer {token}`
    - 로그인이 안 되어있는 경우 401 Unauthorized
    - 토큰의 시간이 만료된 경우 401 Unauthorized
    - 삭제된 회원의 토큰으로 접근하는 경우 401 Unauthorized

    - 회원 정보 조회 `URL: “/api/members/me” + 토큰 정보로 식별`
        - Method GET
            - 성공 시 200 OK
            ```javascript
            {
              email: ,
              nickname: ,
            }
            ```
    - 회원 정보 수정
        - 성공 시 204 No Content
        - 닉네임 수정 `URL: “/api/members/me” + 토큰 정보로 식별`
        ```javascript
        {
          nickname: ,
        }
        ```
        - 비밀번호 수정 `URL: “/api/members/password” + 토큰 정보로 식별`
        ```javascript
        {
          password: ,
        }
        ```
    - 회원 탈퇴 `URL: “/api/members/me” + 토큰 정보로 식별`
        - Method DELETE
        - 성공 시 204 No Content

## 2단계 - 장바구니/주문 API 변경하기

### 상품 리스트 조회

- 상품 리스트 조회 `URL: “/api/products?page={page}&limit={limit}”`
    - Method: GET
    - 성공 시 200 Ok
  ```javascript
    [ {
        "id" : 1,
        "name" : "그릴",
        "price" : 100,
        "imageUrl" : "https~~",
        "stock" : 1,
    }, {
        "id" : 2,
        "name" : "손전등",
        "price" : 200,
        "imageUrl" : "https~~",
        "stock" : 5,
    } ]
  ```
    - 실패 시 400 Bad Request

### 장바구니 상품 추가

- 장바구니 상품 추가 `URL: "/api/carts/products + 토큰 정보로 식별"`
  - Method: POST
  ```javascript
    {
        "id" : 1,
        "quantity" : 1
    }
  ```
  - 성공 시 200 Ok
  ```javascript
  [ {
    "product" : {
        "id" : 1,
        "name" : "그릴",
        "price" : 100,
        "imageUrl" : "https~~",
        "stock" : 1,
    }, 
    "quantity": 1
  }, {
    "product" : {
        "id" : 2,
        "name" : "손전등",
        "price" : 200,
        "imageUrl" : "https~~",
        "stock" : 5,
    },
    "quantity" : 1,
  } ]
  ```
  - 실패 시 400 Bad Request

### 장바구니 상품 조회

- 장바구니 상품 조회 `URL: "/api/carts + 토큰 정보로 식별"`
    - Method: GET
    - 성공 시 200 Ok
  ```javascript
  [ {
    "product" : {
        "id" : 1,
        "name" : "그릴",
        "price" : 100,
        "imageUrl" : "https~~",
        "stock" : 1,
    }, 
    "quantity": 1
  }, {
    "product" : {
        "id" : 2,
        "name" : "손전등",
        "price" : 200,
        "imageUrl" : "https~~",
        "stock" : 5,
    },
    "quantity" : 1,
  } ]
  ```

- 예외 상황
  - 장바구에 추가된 상품이 품절되었을 때
  - 장바구니에 추가된 상품의 개수가 재고 수량보다 클 때
- 예외 처리
  - 프론트에서 확인한 다음에 재고랑 비교해서 조정해서 보여준다.

### 장바구니 수량 변경

- 장바구니 상품 조회 `URL: "/api/carts/products + 토큰 정보로 식별"`
    - Method: PATCH
  ```javascript
    {
        "id" : 1,
        "quantity" : 1
    }
  ```
    - 성공 시 200 Ok
  ```javascript
  [ {
    "product" : {
        "id" : 1,
        "name" : "그릴",
        "price" : 100,
        "imageUrl" : "https~~",
        "stock" : 1,
    }, 
    "quantity": 1
  }, {
    "product" : {
        "id" : 2,
        "name" : "손전등",
        "price" : 200,
        "imageUrl" : "https~~",
        "stock" : 5,
    },
    "quantity" : 1,
  } ]
  ```
  - 실패 시 400 Bad Request

- 예외 상황
  - 장바구니에 담긴 기존 상품 수량 + 새로 추가하려는 수량이 재고를 초과하는 경우
- 예외 처리
  - 기존 수량 + 추가 수량이 재고를 초과하면 요청을 거부하고 오류를 반환한다.

### 장바구니 상품 제거

- 장바구니 상품 조회 `URL: "/api/carts/products?id=1 + 토큰 정보로 식별"`
    - Method: DELETE
    - 성공 시 200 Ok
  ```javascript
  [ {
    "product" : {
        "id" : 1,
        "name" : "그릴",
        "price" : 100,
        "imageUrl" : "https~~",
        "stock" : 1,
    }, 
    "quantity": 1
  }, {
    "product" : {
        "id" : 2,
        "name" : "손전등",
        "price" : 200,
        "imageUrl" : "https~~",
        "stock" : 5,
    },
    "quantity" : 1,
  } ]
  ```
  - 실패 시 400 Bad Request
