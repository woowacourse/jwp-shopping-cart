# 장바구니

장바구니 미션 저장소

## ✏️ Code Review Process

[텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 페어 규칙

- 쉬는 시간 적절히 가지기
    - 한 자리에서 최대 2시간
    - 쉬는시간은 컨디션 따라 짧게는 10분 길게는 20분
- 토론이 과열되면 끝까지 가려고 하지 말고 적절히 멈추기 (기한 내 구현을 위해)
    - 토론한 과정을 노션에 잘 기록
    - 정답이 없다고 느껴지면 적절한 타협점 찾기
    - 시간이 너무 없다 → 각자 고민후 리팩토링때 반영
    - 시간 여유가 괜찮다 → 다른 크루들에게 물어보거나 같이 제 3의 대안 도출
- 최대한 매일 22시에 끝내는 것을 목표로 하고, 개인 일정 최대한 미루지 않기
    - 무조건 시간 많이 투자한다고 좋은 것이 아니다
    - 일정 관리 능력도 중요한 업무 스킬이다!
- 구현하다가 너무 막힌다 싶으면 각자 생각을 정리할 시간을 가진 후 다시 이야기 해보기
- 충분히 논의도 좋지만, 요구 사항을 최소 규모로 만족하는데에 우선적으로 집중하자

## 목표

### 공통

- 스프링을 이용하여 인증 및 권한을 부여하는 방법에 대한 지식 얻기
- 프론트엔드와 협업하면서 api 명세 소통을 원활하게 하는 방법에 대해 배우고 싶음.
    - 프론트엔드의 어휘를 배우고 싶다
- `Interceptor`, `MethodArgumentHandler`, `DispatcherServlet`의 흐름에 대해 잘 이해하고 설명하기.
- 기회가 된다면 mockito를 다뤄보면서 mock test 이용.
    - Domain은 inside-out, Web쪽은 Outside-in으로 구현해보기

### 케이

- 지난 미션에서는, 서비스에서 dao를 의존해봤는데, 생각해보니까 서비스에서 서비스를 의존하는 것도 좋아보임. 따라서 역시 기회가 된다면 서비스를 의존해보는 경험을 해보는 것도 좋을 듯. (다만, 현재규모기준,
  크게 다른 점은 없다고 생각해서 dao 의존해도 될듯)
- 지난 미션에서 도메인을 Entity처럼 사용한 점이 있었는데, 기회가 된다면 이번에는 한번 섞어서 써보지 않아보기!
- repository에 대해 얘기해보기 (도입은 고민됨.)

### 포키

- 이번 미션은 새로운 지식이 많이 등장하는 만큼 즐거운 마음으로 탐구하고 싶네여~~
    - 미션이 끝났을 때 세션과 토큰의 차이를 느낄 수 있으면 좋겠다
    - Iterceptor라는 개념이 새롭게 등장했는데, 어디까지 Interceptor가 하고 어디까지 Domain에서 할지에 대한 고민도 새롭게 하게 되지 않을까?
- 알고리즘 공부를 거의 안해봐서, 알고리즘 다이아 케이의 관점들을 많이 배워보고 싶다
    - 항상 구현 속도가 그리 빠른 편은 아니었어서 케이의 빠른 구현의 비결을 쏙쏙 흡수하고 싶음

---

# 기능 목록


## 1단계

- 회원 가입 `POST /customers` → `201 CREATED`
- 아이디 중복 확인 `GET /customers/username/uniqueness` -> `200 OK`
  - `{isUnique}`
- 로그인 `POST /login` → `200 OK`
    - 아이디와 비밀번호 확인
        - [예외] 아이디나 비밀번호가 잘못 입력되면 `400 Bad Request`
    - 토큰 발급 `JwtTokenProvider`
    - body를 통해 발급된 토큰 응답 `TokenResponse`
- 내 정보 조회 `GET /customers/me` → `200 OK`
    - 해당하는 회원 정보 body로 응답
        - `{id, userName, nickName, age}`
    - [예외] 로그인 되지 않은 상태로 접근하면 `401 Unauthorized`
- 내 정보 수정 `PUT /customers/me` → `200 OK`
    - 수정할 회원 정보 reqeust body
        - `{id, userName, nickName, age, password}`
    - 비밀번호 일치 여부 확인 후 DB의 정보 update
    - [예외] 로그인 되지 않은 상태로 접근하면 `401 Unauthorized`
    - [예외] password가 기존 password와 일치하지 않을 시 `400 Bad Request`
- 패스워드 수정 `PUT /customers/me/password` → `200 OK`
    - 수정할 비밀번호 request body
        - `{oldPassword, newPassword}`
    - 비밀번호 일치 여부 확인 후 DB 정보 update
    - [예외] 로그인 되지 않은 상태로 접근하면 `401 Unauthorized`
    - [예외] oldPassword가 기존 password와 일치하지 않을 시 `400 Bad Request`
- 회원 탈퇴 `DELETE /customers/me` → `204 OK`
    - 해당하는 회원 정보 DB에서 삭제
    - [예외] 로그인 되지 않은 상태로 접근하면 `401 Unauthorized`

### 입력값 예외처리 `400 Bad Request`

- userName
    - [예외] 공백
    - [예외] 4자 미만이거나 20자 초과
- nickName
    - [예외] 공백
    - [예외] 10자 초과
- password
    - [예외] 공백
    - [예외] 8글자 미만 20글자 초과
    - [예외] 영문 / 숫자 / `!@#$%^*`특수문자를 제외한 글자 포함
    - [예외] 공백 포함
- age
    - [예외] 음수


## 2단계

- 상품 목록 조회 `GET /products` -> `200 OK`
  - 모든 상품 정보 json array로 응답
    - {products: [{id, name, price, thumbnail}]}
- 상품 세부 정보 조회 `GET /products/{productId}` -> `200 OK`
  - 해당하는 상품 정보 응답
    - {id, name, price, thumbnail}
  - [예외] 존재하지 않는 상품에 대해 요청할 경우 `404 Not Found`
- 장바구니에 상품 추가 `POST /cart/{productId}` -> `200 OK`
  - [예외] 이미 담은 상품일 때 `400 Bad Request`
    - {message, redirect(boolean)}
- 장바구니 상품 목록 조회 `GET /cart` -> `200 OK`
  - 장바구니에 담긴 상품 정보와 수량 json array로 응답
    - {cartItems: [{product: {id, name, price, thumbnail}, quantity}]}
- 장바구니 상품 수량 변경 `PUT /cart/{productId}/quantity` -> `200 OK`
  - request body {quantity}
  - [예외] 입력값이 0 이하일 때 `400 Bad Request`
- 장바구니 상품 제거 `DELETE /cart/products` -> `204 No Content`
  - request body {productIds: []}
- 장바구니 비우기 `DELETE /cart` -> `204 No Content`
- 장바구니 상품 구매 `POST /orders` -> `201 Created`
  - request body {productIds: []}
  - 구매한 후에는 장바구니 비워짐
  - Location `/orders/{orderId}`
