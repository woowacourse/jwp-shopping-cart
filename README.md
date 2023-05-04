# jwp-shopping-cart

## 2단계 시작 전, 1단계 코드 리팩터링
1. Dao만 있는 구조에서는 domain을 반환한다. 
   - dao와 repository의 차이점?
   - repository가 있다면, dao에서 엔티티를 반환하고, repository에서 서비스로, 도메인 반환한다
2. productDao의 rowMapper static변수로 만들기
3. 도메인 product
   - productId를 id로 변경하기
   - price 변수는 big demical 사용하기
   - price 변수가 음수인 경우 체크하기
   - id의 크기 유효성검사/ name의 길이-blank여부 검사/ image의 길이- blank여부 검사 
   - 질문1 : 왜 id는 big demical 사용안하나요?
   - 질문2 : 필드를 값객체로 만들어야 할까요?
     -> 값 객체로 만들면, dao에 객체 넣어줄 때, entity를 만들어야 한다
   - 질문3 : 현재 product 도메인 객체는 id값이 null인 불완전한 객체입니다. + 비즈니스 로직에서 id를 사용하지 않습니다.
      이경우, 도메인 객체에서 id 필드를 제거하고, db에 저장/조회/수정/삭제할 때는 entity를 사용하는 것은 어떤가요?
     뷰에 id를 가진 도메인 객체를 반환하는 경우(조회, 수정, 저장), dto를 반환하는데 entity의 id값을 이용해 dto로 만들면 되지 않을까요?

4. 데이터 베이스 product테이블
   - pk 수정
   - 다른 sql 변경 가능성 고려하여, long 타입을 big int 타입으로 변경하기
   - price의 경우 양수만 가능(unsigned, positive)하도록 속성 추가하기
5. productRequestDto, productDto등 dto 네이밍 다시하기/productRequestDto로?
   - 질문: requestDto를 어디서 만드는 것이 맞을까요? 
     - 현재는, 서비스에서 productRequestDto(이하 : ProductDto)를 만들고, 컨트롤러에서 그대로 뷰에 적용합니다
     - 서비스에서 product 객체를 반환하고, 컨트롤러에서 dto를 만드는 게 맞을까요?
6. 그냥 Exception인 경우도 handler를 만들고 메세지만들기
   지금은 IllegalArgumentException 과 methodArgumentNotValidException 만 원하는 에러 메세지가 나올것 같은데요
   예상치 못한 에러가 발생할 경우를 대비해 예외처리 추가하기


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
