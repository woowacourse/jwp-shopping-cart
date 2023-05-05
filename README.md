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


---

# 2단계
1. 사용자 기능 구현
   - 사용자 member 도메인 만들기
     - name, email, password 저장
     - 이메일 유효성 검사(정규식)
   - 테이블 만들기
     - id, name, email, password 저장
   - 사용자 dao 만들기
     - 등록 / 모든 사용자 조회/ 이메일로 사용자 / 수정/ 삭제 기능 구현하기
   - 사용자 service만들기
     - add/ findAll/ update/ delete 기능 구현하기
   
2. 사용자 설정 페이지 연동
   - settings.html 수정하기
   - settings.js 수정하기
   - 페이지 컨트롤러에서 "/setting" get 요청 시, 모든 사용자 조회 결과 반환하기
   - member api 구현하기
     - 멤버 추가
     - 멤버 삭제
     - 멤버 정보 수정 (이메일은 수정할 수 없음)
     - 멤버 정보 조회
       - 모든 멤버 정보 조회
       - 선택한 멤버 정보 조회(by member Id 또는 email)?? => 이메일로 조회하는 게 맞는 것 같다

3. 장바구니 기능 구현
4. 장바구니 페이지 연동

이번 단계는 크게 인증과 장바구니 기능 구현으로 나눌 수 있습니다.

어떤 사용자의 장바구니에 상품을 추가하거나 제거할 것인지에 대한 정보는 Basic Auth를 이용하여 인증을 하도록 합니다. 사용자 설정은 설정페이지에서 다룹니다.

장바구니 기능은 인증 기반으로 기능을 구현하고 장바구니에 상품 추가, 제거, 목록 조회가 가능해야 합니다. 이 때 필요한 도메인 설계와 DB 테이블 설계 그리고 이에 맞는 패키지 설계에 유의해주세요.

1. 사용자 기능 구현
   - 사용자 기본 정보
      - email
      - password
      - 필요한 경우 사용자 정보의 종류를 추가할 수 있습니다. (ex. 이름, 전화번호)
   
2. 사용자 설정 페이지 연동
   - settings.html, settings.js 파일 내 TODO 주석을 참고하여 설계한 사용자 정보에 맞게 코드를 변경해주세요. 
     - settings.html 파일을 이용해서 사용자를 선택하는 기능을 구현합니다.
     - 모든 사용자 정보 조회 기능: /settings url로 접근할 경우 모든 사용자의 정보를 확인
     - 사용자 선택기능 :사용자 설정 페이지에서 사용자를 선택하면, **이후 요청(request)에 선택한 사용자의 인증 정보가 포함되도록 한다.** 
     - 이후 요청이란 : 장바구니에 상품추가/ 제거/ 조회 등
       
3. 장바구니 기능 구현
   - 장바구니에 상품 추가 (create)
   - 장바구니에 담긴 상품 제거 (delete)
   - 장바구니 목록 조회 (read)
   
   - 요청 Header의 Authorization 필드로 인증 처리를 해 사용자 정보를 얻는다.
   - 인증 방식은 Basic 인증을 사용합니다.
   
   - 예시)
     - Authorization: Basic ZW1haWxAZW1haWwuY29tOnBhc3N3b3Jk
        type: Basic
        credentials : email:password를  => base64로 인코딩한 문자열
        ex) email@email.com:password -> ZW1haWxAZW1haWwuY29tOnBhc3N3b3Jk
     
   - 그 외 API 스펙은 정해진것이 없으므로 API 설계를 직접 진행 한 후 기능을 구현한다

4. 장바구니 페이지 연동
   - cart.html, cart.js 파일 내 TODO 주석을 참고하여 설계한 장바구니 정보에 맞게 코드를 변경한다
   - 장바구니 상품 추가
     - 1단계에서 구현한 상품 목록 페이지(/)에서 담기 버튼을 통해, 상품을 장바구니에 추가한다.
   - 장바구니 목록 조회 및 제거
     - cart.html 파일과 장바구니 관련 API를 이용하여 장바구니 페이지를 완성합니다.
     - /cart url로 접근할 경우 장바구니 페이지를 조회할 수 있어야 합니다.
     - 장바구니 목록을 확인하고 상품을 제거하는 기능을 동작하게 만듭니다.
