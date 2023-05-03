# jwp-shopping-cart

## 상품 목록 페이지 연동
- index.html 파일을 이용하여 상품 목록이 노출되는 페이지를 완성합니다. 
- `/` url로 접근할 경우 상품 목록 페이지를 조회할 수 있어야 합니다.
- 상품이 가지고 있는 정보는 다음과 같습니다. 필요한 경우 상품 정보의 종류를 추가할 수 있습니다.(ex. 상품 설명, 상품 카테고리)
  - 상품 ID
  - 상품 이름
  - 상품 이미지
  - 상품 가격

## 상품 관리 CRUD API 작성
- 상품 생성, 상품 목록 조회, 상품 수정, 상품 삭제 API를 작성합니다. 
- API 스펙은 정해진것이 없으므로 API 설계를 직접 진행 한 후 기능을 구현 합니다.

### API 설계
- 상품 생성: 이름,이미지,가격 필요
- 상품 목록 조회: 전체 조회
- 상품 수정: 모든 정보 수정 가능
- 상품 삭제: 단건 삭제

## 관리자 도구 페이지 연동
- admin.html 파일과 상품 관리 CRUD API를 이용하여 상품 관리 페이지를 완성합니다.
- `/admin` url로 접근할 경우 관리자 도구 페이지를 조회할 수 있어야 합니다
- 상품 추가, 수정, 삭제 기능이 동작하게 만듭니다.

## 요구사항 외 추가 적용 요소
- [x] 예외처리
- [x] 테스트 작성
  - [x] 컨트롤러
    - [x] 조회: 상태 코드, 응답 Body 검증
    - [x] 뷰 반환: 상태 코드, 모델 attribute 검증
    - [x] 삽입: 상태 코드, 실패 케이스 검증
    - [x] 수정: 상태 코드, 실패 케이스 검증
    - [x] 삭제: 상태 코드, 실패 케이스 검증
  - [x] Dao
    - [x] 삽입: 삽입이 정상적으로 이루어졌는지
    - [x] 조회: 삽입한 데이터가 잘 들어갔는지
    - [x] 수정: 수정 전후 데이터 비교
    - [x] 삭제: 실제로 데이터가 삭제됐는지
- [x] Spring Validation


### 궁금한 내용
- 가지고 있는 데이터는 같지만, 사용되는 계층이 다른 Dto 처리 방법
- DTO <-> Entity 변환 방법 (toEntity(), Mapper 클래스, ...)
- 현재 단계에서는 서비스가 가지는 역할이 Dao 호출뿐인 것 같다. 이러한 경우에는 서비스 계층을 생략해도 좋을까?
- 처음에 생각한 API 설계는 CRUD를 다루는 것이라고 생각해 따로 CartApiController로 분리해주었는데 View 반환은 Api가 아닌가 하는 의문점
- 현재 컨트롤러 테스트는 상태 코드와 응답값만 검증하고 있고, DB에 실제로 변경사항(삽입,수정,삭제)이 반영되는지는 검증하지 않고 있다. 실제 변경사항 검증은 Dao 테스트에 맡기고, 컨트롤러는 이정도의 테스트만 진행해도 괜찮을지?
- 실제로 없는 id의 행을 update 할 때, 변경사항이 전혀 없고 예외도 발생하지 않는 것을 확인함. 클라이언트는 수정이 되었을 거라고 예상할텐데 이를 정상적인 흐름으로 봐도 될지? 또는 예외처리를 통해 잘못된 요청이 들어오고 있다고 알려야될지?


## 2단계

### 기능 구현 전 리팩토링
- [x] 삽입/수정/삭제가 반환값을 가지도록 하고, 해당 반환값이 컨트롤러까지 전달되도록 수정
- [x] 단건 조회 API 생성
- [x] 변경된 반환값에 따른 테스트 실행
- [x] DTO - Entity Mapper 클래스 생성
- [x] 불필요한 @Autowired 생략
- [x] @Valid를 활용한 유효성 검증
- [x] API URL 설계 다시 하기

## 장바구니 기능

### 사용자 기능 구현
- [x] 사용자 기본 정보: email, password
  - [x] DB 테이블 설계
    - ```sql
      CREATE TABLE MEMBERS
      (
        id          INT     NOT NULL AUTO_INCREMENT,
        email       VARCHAR NOT NULL UNIQUE,
        password    VARCHAR NOT NULL,
        PRIMARY KEY(id)
      );
      ```

### 사용자 설정 페이지 연동
- [x] `settings.html` 파일을 이용해서 사용자를 선택하는 기능을 구현합니다.
- [x] `/settings` url로 접근할 경우 모든 사용자의 정보를 확인하고 사용자를 선택할 수 있습니다.
  - DB에 저장된 사용자 정보 모두 가져오기
- [x] 사용자 설정 페이지에서 사용자를 선택하면, 이후 요청에 선택한 사용자의 인증 정보가 포함됩니다.
  - DB에서 특정 사용자 정보 가져오기

### 장바구니 기능 구현
- [x] 장바구니와 관련된 아래 기능을 구현합니다.
  - [x] 장바구니에 상품 추가 (CREATE)
    - `insert into member_product(member_id, product_id) values(?, ?)`
  - [x] 장바구니에 담긴 상품 제거 (DELETE)
    - `delete from member_product where id = ?`
  - [x] 장바구니 목록 조회 (READ)
    - `select * from member_product`
  - [x] DB 테이블 설계
    - 사용자:상품 = M:N 관계이므로, 중간 테이블 생성 
    - ```sql
      CREATE TABLE CART
      (
        id          INT NOT NULL AUTO_INCREMENT,
        member_id     INT NOT NULL,
        product_id  INT NOT NULL,
        PRIMARY KEY(id),
        FOREIGN KEY(member_id)  REFERENCES MEMBERS(id)  ON DELETE CASCADE,
        FOREIGN KEY(product_id) REFERENCES PRODUCTS(id) ON DELETE CASCADE
      )
      ```
- [x] 사용자 정보는 요청 Header의 Authorization 필드를 사용해 인증 처리를 하여 얻습니다. 
  - [x] 인증 방식은 Basic 인증을 사용합니다
    - type: Basic
    - credentials : email:password를 base64로 인코딩한 문자열
    - ex) email@email.com:password -> ZW1haWxAZW1haWwuY29tOnBhc3N3b3Jk
  - [x] Header에서 Authorization 필드 값 가져오기
  - [x] Request로 넘어온 credentials 디코딩해서 사용자 알아내기
    - 뷰에서 인코딩한 값이 넘어오는지 확인

### 장바구니 페이지 연동
- [x] 장바구니 상품 추가
  - [x] 1단계에서 구현한 상품 목록 페이지(/)에서 담기 버튼을 통해 상품을 장바구니에 추가할 수 있습니다.
- [x] 장바구니 목록 조회 및 제거
  - [x] cart.html 파일과 장바구니 관련 API를 이용하여 장바구니 페이지를 완성합니다.
  - [x] `/cart` url로 접근할 경우 장바구니 페이지를 조회할 수 있어야 합니다.
  - [x] 장바구니 목록을 확인하고 상품을 제거하는 기능을 동작하게 만듭니다.

### 리팩토링
- [x] 깨진 테스트 복구
- [ ] 테스트 작성
- [ ] 코드 일관성
- [ ] AOP?..

  ### 궁금한 내용
- [ ] 컨트롤러에서 응답으로 엔티티 객체를 반환해도 될까?
- [ ] 상품 추가 요청에 대한 컨트롤러의 응답으로 엔티티를 반환하도록 했다. 이때 상태 코드는 CREATED로 설정하며, location 헤더에 단건 조회 API URL을 담았다. 프론트 쪽에서는 이 응답을 보고 단건 조회 API를 담았다는 것을 바로 알기 힘들 것 같다.
  - 왜냐하면, 어떤 HTTP 요청을 보내야 되는지에 대한 정보 없이, URI만 있기 때문이다. URI를 이렇게 담아서 보내는 것이 맞는지, 올바르게 사용한 것인지 궁금하다.
  - 지금처럼 단건 조회 API 경로를 담아서 응답할 경우, 해당 객체 자체를 응답할 필요는 없을까?
- [ ] 상품 추가의 결과로 엔티티를 반환하고 있다. 하지만 업데이트와 삭제의 결과로는 변경된 row 수를 반환하고 있다. 통일성이 없는 것일까? 아니면, 프론트엔드에서 필요로 하는 정보를 주는 게 중요한 것이지, 통일성은 크게 중요하지 않을까?
- [ ] 상품에 대한 RequestDto에 입력값 검증을 추가하였다. 이때, image는 요구사항에 맞게 URL로만 입력 가능하게 @Pattern을 사용했다. update,insert 요청 시에 공통적으로 검증할 필요가 있기 때문에 Validator 클래스를 따로 빼주었다. 처음엔 ProductRequestDto와 같은 부모 클래스를 만들까 고민했는데, 괜찮은 선택이었을까?
- [ ] @WebMvcTest에서 JdbcTemplate, Dao를 통해 DB 변경사항을 검증하려 했더니 의존성 주입이 안 되는 것을 확인함. 이럴 때는 @SpringBootTest로 전환을 해야 될지? 다른 방법은 무엇?
- [ ] 왜 필드가 하나인 DTO는 빈 생성자 없이 데이터 바인딩이 안 될까? (Reqeust의 Body로 넘어온 값)
- [ ] data.sql 파일을 전체 주석 처리했더니, Bean 생성 과정에서 오류가 발생
- [ ] @WebMvcTest에서 @Controller, @RestController의 의존성을 모두 추가해줘야 하는가?
- 
