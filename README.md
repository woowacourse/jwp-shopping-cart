# jwp-shopping-cart

## 2단계 시작 전, 1단계 코드 리팩터링
#### 1. Dao만 있는 구조에서는 domain을 반환한다. 
   repository가 있다면, dao에서 엔티티를 반환하고, repository에서 서비스로, 도메인 반환한다
   - [x] 도메인 객체의 원시 타입 필드들을 값 객체로 변환함
   - [x] 도메인 객체를 저장,관리하는 레포지토리를 생성하고
   - [x] 레포지토리는 도메인 객체를 db와 1:1매핑되는 엔티티로 변환
   - [x] dao를 이용하여, 엔티티(도메인 정보를 테이블 형식으로 담은 객체)를 저장 및 조회한다.
   </br></br>
   (변경 이유)</br>
   도메인과 레포지토리는 도메인 계층/ db 엔티티와 dao는 영속성 계층에 속한다
     - 도메인과 엔티티 객체로 분리한 이유 : 도메인 계층과 영속성계층을 명확히 구분하기 위해
     - 도메인 계층과 영속성 계층 구분 이유:
         - 시스템 안정성을 위해서 비즈니스 로직의 대부분의 개념은 불변성을 지켜야하한다 => Product 객체를 불변하게 만듦(식별자로 구분되는 ddd 개념의 엔티티)
         - 도메인(비즈니스 로직) 어떤 데이터 베이스(테이블)을 이용하는지와 상관없이 작동해야한다

       따라서, 도메인 객체- 레포지토리(객체들의 관리 저장소) <-> entity - dao(테이블과 일치)로 작동하도록 변경하였다

#### 2. productDao
   - [X] 의 rowMapper static변수로 만들기
    
#### 3. 도메인 product
   - [X] productId 변수명 id로 변경하기
   - [X] price 필드의 자료유형을 big decimal로 변경하기
   - [x] id의 양수 유효성검사
   - [x] name의 길이 검사, blank여부 검사
   - [x] image의 blank여부 검사
   - [x] price 변수가 음수인 경우 체크하기
     
   (변경 전 생긴 질문에 대한 해답)
   - [X] 질문1 : 왜 id는 big decimal 사용안하나요?
     - 답: big decimal은 실수이기 때문이다. big int나 long을 사용하는 것이 맞다.
   - [X] 질문2 : 필드를 값객체로 만들어야 할까요?
     - 답: 비즈니스 로직의 시스템 안정성을 위해, 도메인 객체를 값객체 혹은 ddd개념의 entity(식별자로 객체 구분)로 변경하였다
   - [X] 질문3 : 현재 product 도메인 객체는 id값이 null인 불완전한 객체입니다. + 현재 비즈니스 로직에서 id를 사용하지 않습니다.
     이 경우, 도메인 객체에서 id 필드를 제거하고, db에 저장/조회/수정/삭제할 때는 entity를 사용하는 것은 어떤가요?
     - 답: 도메인 계층 객체 저장소인 repository와 영속성 계층인 dao로 분리함으로써, 
        repository에 id가 null인 도메인 객체 저장 및 db에는 entity로 저장했다.

#### 4. 데이터 베이스 product테이블
   - [x] pk 수정
     - 하지 않은 이유 : id 줄에 여러 속성 옵션이 붙여져 있어서, 가독성을 위해 맨 밑줄에 따로 분리하였다
   - [x] 다른 sql 변경 가능성 고려하여, id의 long 타입을 big int 타입으로 변경하기
   - [x] 다른 sql 변경 가능성 고려하여, price의 long 타입을 decimal 타입으로 변경하기
   - [x] id와 price가 양수만 가능하도록, unsigned 속성 옵션 추가하기


#### 5. productRequestDto, productDto등 dto 네이밍 다시하기
   - [x] ProductRequest와 ProductResponse로 수정하기

#### 6. 예외처리 수정하기
  - [x] 예상치 못한 에러 발생 경우의 대비해 예외처리 추가하기
    </br>: Exception인 경우도 handler를 만들고 메세지만들기
  - [x] DB에서 발생하는 IllegalArgumentException => 커스텀 예외로 수정하기
    </br>이유 : 비즈니스 로직에서 발생하는 IllegalArgumentException과 구분하기 위해서


#### 질문
1. 질문: requestDto를 어디서 만드는 것이 맞을까요?
   - 현재는, 서비스에서 productRequestDto(이하 : ProductDto)를 만들고, 컨트롤러에서 그대로 뷰에 적용합니다
   - 서비스에서 product 객체를 반환하고, 컨트롤러에서 dto를 만드는 게 맞을까요?
2. request dto(decimal) 에서는 @valid를 통해, 10억까지만 가능하도록 추가함
   - 도메인 Price(decimal)에서 최대값 설정 안함
   - 데이터 베이스테이블에서 price(decimal) 최대값 설정안함
   - 뷰에서만 모두 통일해야 하나?

---

# 2단계
1. 사용자 기능 구현
   - [x] 사용자 member 도메인 만들기
     - [x] name, email, password 저장
     - [x] 이메일 유효성 검사(정규식)
   - [x] 사용자 테이블 만들기
     - [x] id, name, email, password 저장
   - [x] 사용자 dao 만들기
     - [x] 등록 
     - [x] 모든 사용자 조회
     - [x] 아이디로 사용자 조회
     - 이메일로 사용자 조회**********************************(데이터 베이스에 중복 이메일 허용 안됨!)
     - [x] 수정
     - [x] 삭제 기능 구현하기
   - [x] 사용자 service만들기
     - [x] add/ findAll/ update/ remove 기능 구현하기
   
2. 사용자 설정 페이지 연동
   - [x] settings.html 수정하기
   - [x] settings.js 수정하기
   - [x] settings.css 추가하기
   - [x] 페이지 컨트롤러에서 "/setting" get 요청 시, 모든 사용자 조회 결과 반환하기
   - [x] member api 구현하기
     - [x] 멤버 추가
     - [x] 멤버 삭제
     - [x] 멤버 정보 수정 (이메일은 수정할 수 없음)
     - [x] 멤버 정보 조회
       - [x] 모든 멤버 정보 조회
       - 선택한 멤버 정보 조회(by member Id 또는 email)?? => 이메일로 조회하는 게 맞는 것 같다
    
3. 장바구니 기능 구현
   1. 장바구니 Cart도메인 만들기
      (초기 도메인 설계)
      - Cart (MemberId, CartItems)
      - CartItems (List<CartItem>) 일급 컬렉션
      - CartItem (Product, Amount)
      (레포지토리를 고려한 도메인 설계)
      - [x] CartItem (ProductId) 
      - **수정 예정: (ProductId, Amount)**
   
   2. 장바구니 테이블 만들기
      - [x] (memberId, productId) = 기본키
      - [x] memberId 외래키이고, 원본 memberId 삭제되면, 삭제된다
      - [x] productId 외래키이고, 원본 productId 삭제되면, 삭제된다
      - [x] amount
   
   3. 장바구니 Dao 만들기
      - [x] CartProductEntity 만들기
        :테이블 필드인 memberId, productId, amount 가짐
      - [x] 장바구니 상품 추가 기능
        :memberId와 productId가 같은 데이터가 있다면, 해당 데이터의 amount를 +1한다
      - [x] 장바구니 조회 기능
        :product테이블과 join하여, cart가 가지고 있는 product를 조회한다.
      - [x] 장바구니 상품 삭제 기능
   
   4. 장바구니 Repository만들기
      - [x] 장바구니 상품 추가
        :멤버Id, 상품Id, amount 담은 CartProductEntity 생성 및 dao 통해 추가하기
      - [x] 장바구니 상품들 조회
        :멤버Id에 해당하는 장바구니 상품들 조회 및 Product 객체로 변환
      - [x] 장바구니 상품 삭제
        :멤버Id, 상품Id, amount 담은 CartProductEntity 생성 및 dao 통해 삭제하기
      
   5. 장바구니 Service 만들기
       - [x] 장바구니 상품 추가 : CartProduct 객체(상품id, 개수) 추가
       - [x] 장바구니 상품들 조회 : memberId로 조회
       - [x] 장바구니 상품 삭제 : CartProduct 객체 삭제
    
      
4. 장바구니 페이지 연동
   - [x] 장바구니 API 컨트롤러 만들기
       - [x] CartProductRequest만들기 (productId , ~~amount 담기~~)
       - [x] 장바구니 상품 추가 : 매개변수 = (검증된 memberId, CartProductRequest(현재는 productId만 있음))
       - [x] 장바구니 상품들 조회 : 매개변수 = (검증된 memberId)
       - [x] 장바구니 상품 삭제 : 매개변수 = (검증된 memberId, CartProductRequest)
     
   - [x] 이메일, 비밀번호로 회원 검증하기
     - [x] BasicAuthorizationExtractor 만들기 : base64로 인코드된 코드 파싱 및 인코드하기
     - [x] 인터셉터 만들기 : 
       - [x] httpRequest의 authorization 헤더 정보로부터 credentialEntity 추출(BasicAuthorizationExtractor사용)
       - [x] credential 객체(memberId, email, password) 만들기
       - [x] credentialEntity 만들기 : memberId이 null 이어도 됨
       - [x] credentialDao 만들기 :를 통해, 검증된 memberId 얻기
     - [x] 메소드리졸버 만들기 :
       - [x] 어노테이션 만들기(인터셉터에서 httpRequest 속성으로 정의한 memberId => 컨트롤러의 매개변수로 가져오기 위한 )
       - [x] HandlerMethodArgumentResolver 오버라이딩하기
     - [x] WebMvcConfiguration에 인터셉터와 메소드리졸버 등록하기
   - [x] cart.html 파일 수정하기
   - [x] cart.js 파일 수정하기
   
5. 기타
   - 이메일로 사용자 조회**********************************(데이터 베이스에 중복 이메일 허용 안됨!)
     - 이메일 같은경우 중복 안됨으로 처리하기
   - 선택한 멤버 정보 조회(by member Id 또는 email)?? => 이메일로 조회하는 게 맞는 것 같다
   - cartProductController의 post get delete 매핑 모두 매개변수 받기
   - 질문!!!!!! : html 문서에서 왜 data: {id : id }로 감싸야 하느ㄴ지>???
   - WebMvcConfiguration할때, @EnableWebMvc 붙이면, 다른 js, css 파일들 인식을 못함. 왜????
    - 질문!!! 서버 데이터베이스의 사용자 정보를 초기화시켜도 localStorage에는 사용자정보가 남아있는 것 같아.
      localStorage를 어떻게 함꼐 초기화 할 수 있을까요?
