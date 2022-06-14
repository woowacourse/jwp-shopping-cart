# 장바구니

장바구니 미션 저장소

### 페어 규칙

- 페어프로그래밍은 10분 간격으로 교대한다.
    - 중간에 생각할 시간이 필요하면 이야기하고 회의한다.
- 1단계는 일요일까지 마감한다.
    - 토요일 오후에 오프라인으로 진행 + 일요일까지 넘어간다면 온라인으로 진행한다.

### 기능 요구 사항

- [x] 회원가입
    - 회원가입 시에는 Body로 email, username, password를 입력받는다.
    - [x] `성공`
        - 201 Created를 반환한다.
        - Location Header에 `/members/{id}` 를 반환한다.
    - [x] `예외`
        - email
            - 중복된 이메일로 가입을 요청
            - 8자 이상 50자 이하가 아닌 경우
            - 이메일 형식이 아닌 경우
            - 이메일 내에 공백이 존재할 경우
        - username
            - 중복된 닉네임으로 가입을 요청
            - 1자 이상 10자 이하가 아닌 경우
            - 닉네임 내에 공백이 존재할 경우
        - password
            - 8자 이상 20자 이하가 아닌 경우
            - 패스워드 내에 공백이 존재할 경우
- [x] 로그인
    - 로그인 시에는 Body로 email, password를 입력받는다.
    - [x] `성공`
        - 200 OK를 반환한다.
        - Body로 accessToken, expirationTime을 반환한다.
    - `예외`
        - 로그인 실패 시 `401 Unauthorized`를 반환한다.
- [x] 회원 정보 조회
    - [x] `성공`
        - 200 OK를 반환한다.
        - Body로 id, email, username을 반환한다.
- [x] 회원 정보 수정
    - 회원정보 수정 시 Body로 username을 입력받는다.
    - [x] `성공`
      - 200 OK를 반환한다. - Body로 id, email, username을 반환한다.
        - `예외`
            - 중복된 닉네임으로 수정을 요청 (이전 닉네임과 같은 경우 예외가 아니다.)
            - 1자 이상 10자 이하가 아닌 경우
            - 닉네임 내에 공백이 존재할 경우
- [x] 회원 탈퇴
    - [x] `성공`
        - 204 No Content를 반환한다.

- [x] ErrorDto 적용
- [x] 예외 사항 적용
- [x] Raw 타입 찾아서 수정

### 인증 관련 요구사항

- 사용자 인증은 JWT 토큰으로 진행한다.
- 토큰 유효 시간은 3시간으로 한다.
    - 만료된 토큰으로 인가 요청 시 401 Unauthorized를 반환한다.
- 권한이 없는 사용자가 접근했을 때 403 Forbidden을 반환한다.
    - 회원가입, 로그인 요청을 제외한 나머지 API에 적용한다.

### 2단계 기능 요구사항

- [x] 1단계 피드백 내용 적용
    - [x] 토큰 확인 할 때 현재 가입된 email 인가?에 대한 검증을 interceptor에서도 해주자.
- [x] 모든 상품 조회 API 구현
    - 상품 조회 시에는 `GET` `/api/products` 로 요청한다.
    - List를 이용해 모든 상품이 조회된다.
    - 인가를 받지 않아도 된다. (인터셉터에서 제외)
- [x] 내 장바구니 상품 조회 구현
    - 장바구니 상품 조회 시 `GET` `api/customers/{customerId}/carts` 로 요청한다.
    - 토큰을 넣어주어야 한다.
    - `예외` customerId와 토큰에서 받은 값이 다르면 `403 Forbidden`
- [x] 내 장바구니에 상품 추가 구현
    - 장바구니 상품 추가 시 `POST` `/api/customers/{customerId}/carts` 로 요청한다.
    - body에 productId, count를 입력받는다.
    - `예외` 존재하지 않는 productId로 요청을 보낼 경우
    - `예외` 이미 담겨있는 productId로 요청을 보낼 경우
    - `예외` count가 물품의 재고를 넘기는 경우
    - `예외` customerId와 입력받은 토큰의 유저와 다를 경우
- [x] 내 장바구니에 상품 제거 구현
    - 장바구니 상품 제거 시 `DELETE` `/api/customers/{customerId}/carts?productId={id}` 로 요청한다.
    - `예외` 존재하지 않는 productId로 요청을 보낼 경우
    - `예외` customerId와 입력받은 토큰의 유저와 다를 경우
- [x] 내 장바구니에 상품 개수 변경 구현
    - 장바구니 상품 제거 시 `PATCH` `/api/customers/{customerId}/carts?productId={id}` 로 요청한다.
    - body에 count를 입력받는다.
    - `예외` count가 물품의 재고를 넘기는 경우
    - `예외` 존재하지 않는 productId로 요청을 보낼 경우
    - `예외` customerId와 입력받은 토큰의 유저와 다를 경우
- [ ] 도메인 단에서도 검증을 이루어질 수 있도록 변경한다 ( 구현까지 시간이 촉박하므로 마지막 이슈로... )
