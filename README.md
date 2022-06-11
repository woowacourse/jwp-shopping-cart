# 장바구니

장바구니 미션 저장소

[api 명세](https://www.notion.so/mima-o/API-SPEC-d1233b9e6e90489ab19c3dd94c9eb646)

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

## 2단계 기능 구현 목록

**상품 조회 기능**

- [x] 상품 조회 API
- [x] 해당 아이디의 상품을 반환하는 기능
    - [x] product table
    - [x] id, 상품명(name), 상품 가격(price), 상품 이미지(image)

**상품 목록 조회 기능**

- [x] 상품 목록 조회 API
- [x] 해당 페이지, 불러올 상품 갯수 만큼 목록을 반환하는 기능
    - [x] product table
    - [x] id, 상품명(name), 상품 이미지(image), 상품 가격(price)
- [x] JSON 배열 형식으로 아이템을 응답한다

**장바구니 목록 요청 기능**

- [x] 장바구니 목록 요청 API 기능
- [x] 로그인한 회원의 서버에 저장된 장바구니 목록을 반환하는 기능
    - [x] cart_item table
    - [x] id, 상품 아이디(productId), 상품명(name), 상품 이미지(image), 상품 가격(price), 장바구니에 담긴 갯수(quantity)
- [x] JSON 배열 형식으로 아이템을 응답한다
- [x] 액세스 토큰을 통한 유저 정보 확인 필요

**장바구니 추가 요청 기능**

- [x] 장바구니 추가 요청 API 기능
- [x] 상품 목록의 상품을 장바구니에 추가하는 기능
    - [x] cart_item table
    - [x] 상품 아이디(productId), 추가할 수량(quantity)
- [x] 장바구니에 이미 존재하는 물품이라면 개수를 1 증가시킨다.

**장바구니 정보 수정 기능**

- [x] 장바구니 정보 수정 API 기능
- [x] 장바구니에 담긴 아이템의 정보(갯수)를 수정하는 기능
    - [x] 상품 아이디(productId), 변경할 정보...

**장바구니 아이템 제거 기능**

- [x] 장바구니 아이템 제거 API 기능
- [x] 장바구니에 담긴 아이템들을 제거하는 기능
    - [x] 삭제할 장바구니 아이디 리스트
- [x] 제거에 성공한 아이디 목록을 응답하는 기능
- [x] 삭제 시 유저 자신의 장바구니 상품만 제거할 수 있어야 함

**상품 주문하기 기능**

- [x] 상품 주문하기 API 기능
- [x] 주문할 상품들을 주문하는 기능
    - [x] 상품 아이디(productId), 주문 수량(quantity)
- [x] 생성된 주문(Order)의 아이디를 반환

## TODO

- [x] Dao Optional 처리 고려하기
- [x] repository 테스트 추가
- [x] 인수테스트 추가
- [x] 에러메세지 형식 변경
- [x] cart_item 안에 개수 요소 추가되지 않는 이유 듣기 -> quantity 추가
- [ ] 서버 DB 연결하기
- [ ] dao의 어노테이션 변경 -> 무엇으로?? 컴포넌트???
- [ ] 에러메세지 포맷을 dto화

### 1단계 피드백

- [x] JwtTokenProvider -> 공통 로직은 메서드로 분리할 수 있겠네요!
- [x] dao와 repository에서의 메서드명이 login일 필요가 있을까요?
    - -> 각각 역할에 맞게 메소드명 고민
- [x] 토큰을 생성하는것까지 Service에서 하는것이 맞지 않을까요~??
    - -> 서비스로 옮기자
- [x] TokenRequest -> 어떤 id를 의미하는지 명확하게 나타내면 좋을 것 같아요~
- [x] checkDuplicateUsername -> 파라미터 설정 값이 중복된게 아닌가 해요!
- [x] CustomerRepository -> 예외 메시지를 넣지 않고 비워둔 이유가 있을까요? :
    - controllerAdvice에서 해당 예외를 처리.
    - 하지만 예외 메세지를 throw 시점에서 명시적으로 적어주는 편이 좋을 것 같아 수정함.

### 2단계 피드백

- [x] 상품을 전부 조회한 뒤에 끊지않고,
    - 조회할때부터 끊어서 조회하도록 해볼 수 있을까요?
- [x] 장바구니 레포지토리에서는 Cart와 관련된 처리만하고
    - 나머지는 서비스 레이어에서 하는건 어떨까요?
- [x] 지금 로직에서는 모든 회원의 장바구니 수량이 변경되지않을까요~?
- [x] 요청 온 상품id 개수만큼 쿼리가 여러번 실행되겠네요!
    - in 쿼리를 사용해서 쿼리 수행 횟수를 줄여볼 수 있을까요?
    - -> for문을 batchUpdate를 사용해 쿼리 수행 횟수를 줄여봤습니다! 
    - -> 그 과정에서 카트에 해당 물품이 없는 경우와 있는 경우를 나눠 서비스로 비즈니스 로직을 추출했습니다!
- [x] 이 메서드는 readOnly=true 옵션을 설정하는게 좋을 것 같아요!
  - -> 조회 관련 서비스단 메서드에서, 데이터 변경을 막기 위해 붙이는 어노테이션
  - https://junhyunny.github.io/spring-boot/jpa/junit/transactional-readonly/
- [x] 어떤 id를 의미하는지 더 구체적으로 네이밍하면 좋을 것 같습니다
  - -> api 매핑 과정에서 같은 이름을 쓰기 위해 부득이하게 id라고 이름을 짓게됨
  - -> 앞으로는 초기 api 설계에서 이름 정하는 방법을 고민해볼 생각이다.
- [ ] 회원 id가 유효한지를 확인해봐야하지 않을까요~?
    - 존재하지 않는 회원의 id가 요청올 수도 있을 것 같아요.
    - API마다 매번 회원id의 유효성 체크하는 로직이 있어야할텐데, 아규먼트 리졸버에서 처리해서 중복을 제거하는것도 하나의 방법이겠네요!
    - 이건 헌치가 고민해봐주세요!
- [x] 혹시 우테코에서 로그에 대해 아직 학습하지않았을까요??
  - -> print문을 실수로 지우지 않음
  - -> 공부내용 정리하기
- [x] 현업이라고 생각하면 우선은 코드를 지우지않고 둘 것 같아요.
    - 그리고 테이블을 삭제하는건 정말 신중해야해요. 테이블은 무조건 삭제하지않을 것 같아요
    - 미션이긴하지만 굳이 삭제는 안할 것 같아요
    - -> 이전 테이블들 다시 롤백
