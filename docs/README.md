# 기능 목록

- [x] 상품 관리 CRUD API 작성
    - [x] 상품 정보를 담는 객체를 생성한다
        - [x] (검증) 같은 이름, 같은 가격은 가질 수 없다.
        - [x] (검증) 이름은 10자 이상일 수 없다.
        - [x] (검증) 이름은 Blank일 수 없다.
        - [x] (검증) 이미지 `URL`은 `http`혹은 `https`로 시작해야 한다.
        - [x] (검증) 가격은 0 혹은 양수여야 한다.
        - [x] (검증) 가격은 1,000,000이하 여야한다.
    - [x] 해당 객체에 대해서, 아래와 같은 오퍼레이션을 수행할 수 있다.
        - [x] CREATE
        - [x] READ
        - [x] UPDATE
        - [x] DELETE
          <br><br>
- [x] 상품 목록 페이지 연동
    - [x] index.html 파일을 이용해 상품 목록이 노출되는 페이지를 완성한다.
    - [x] / url로 접근할 경우 상품 목록 페이지를 조회할 수 있다.
    - [x] 상품 기본 정보의 종류를 추가할 수 있다.(상품 설명, 상품 카테고리)
      <br><br>
- [x] 관리자 도구 페이지 연동
    - [x] admin.html 파일과 CRUD API를 이용해 상품 관리 페이지를 구현한다.
    - [x] /admin url로 접근할 경우 관리자 도구 페이지를 조회할 수 있다.
    - [x] 상품 추가, 수정, 삭제 기능이 동작한다.
    <br><br>
- [x] 사용자 기능 구현
    - [x] 사용자는 기본적으로 email, password 를 가진다.
    - [x] 필요한 경우, 사용자 정보의 종류를 추가할 수 있다.
    <br><br>
- [x] 사용자 설정 페이지 연동
    - [x] `settings.html`에서 모든 사용자의 정보를 확인할 수 있다.
    - [x] `settings.html`에서 사용자를 선택할 수 있다.
    - [x] 사용자를 선택하면, 이후 요청에 선택한 사용자의 인증 정보가 포함된다.
    <br><br>
- [x] 장바구니 기능 구현
    - [x] 장바구니에 상품을 추가할 수 있다.
    - [x] 장바구니에 담긴 상품을 제거할 수 있다.
    - [x] 장바구니 목록을 조회할 수 있다.
    - [x] 사용자 정보는 `Header`의 `Authorization` 필드를 사용해 인증 처리한다(Basic 인증)


# 기록 📕

<details>
<summary> step 1 리팩토링 기록 및 고민 </summary>
<div>

- [x] Service에 DTO 개념을 붙이고 싶지 않다. 반면 Controller에서도 도메인에 대한 결합도를 높이고 싶지는 않다. 두 방식은 모두 극단적이라 생각. 따라서 새로운 레이어 생성.
    - [x] Application Layer를 중간에 생성함으로써 DTO <-> 도메인 변환작업을 전담.
    - [x] `Open Layer`로 둠으로써 표현 계층에서 선택적으로 접근할 수 있게 하면 어떨까.
    - [x] 이것이 좋은 방향인지는 카프카와 논의해봐야 할 듯!
- [x] `IdSequencer`를 통해 DB 아이디와 Product ID 두 개념을 분리
    - [x] 이제 도메인 엔티티와 DB 엔티티는 완전히 독립적임
- [x] `DELETE` 시 전체 product를 통해 삭제하는 것이 아닌, id를 통해 삭제하도록 변경
- [x] Repository에 `findByName` 메소드 추가, 쿼리 최적화
    - [x] Repository에 이런 기능이 들어가는 것을 두려워하지 말자! 성능 최적화를 위해. Repo에는 보편적인 기능만 들어갈 필요는 없다.
- [x] CRUD service 통합
    - [x] 자원이 아닌 비즈니스 중심으로 서비스를 구성해야 한다는 생각은 있지만, CRUD 분리는 너무 과하다는 생각이 들음.
    - [x] 따라서 이런 철학은 유지하되, 상황에 따라 trade-off를 계산해 자원 중심으로도 service를 만들 수 있어야 할듯.
- [x] request DTO 검증로직 추가
    - [x] 어디까지 검증할 것인지? 는 카프카와 함께 더 이야기 해 보아야 할 듯.
    - [x] 일단은 데이터 자체에 대한 검증 (notNull, Positive 등)만 수행함.
- [x] Application 계층을 위한 커스텀 어노테이션 `@Application` 생성
    - [x] `@Component`와 동일한 역할을 하지만 명시적인 효과
- [x] SpringBootTest -> WebMvcTest로 개선
    - [x] Mockito, MockMVC 사용
- [x] CRUD -> GET, POST, PUT, DELETE에 각각 매핑
    - [x] URL에서 method 관련 정보가 사라짐, 자원만 명시(`/product`)
- [x] Optional을 stream처럼 다루기
    - [x] 기본 null check 스타일에서 벗어나 깔끔한 코드 구성 가능
    - [x] 구현한 사람에 의도에 맞는 사용법
    - [x] https://www.daleseo.com/java8-optional-effective/

</div>
</details>

<details>
<summary> step 2 리팩토링 기록 및 고민 </summary>
<div>

- [x] Controller 에서 Service로 요청을 보낼 때 DomainConverter를 사용하고 있는데 고민이 듬.
  - [x] 차라리 Service - Controller 간 DTO 사용이 낫지 않을까?
  - [x] 하지만 DTO를 사용하는 경우 Service -> Controller DTO, Controller -> Service DTO.. 양방향 비용이 듬.
    - [x] 추가로 각 레이어는 Mapper 까지 가지고 있어야 한다.
  - [x] 결국 각 service마다 converter를 두는 쪽으로 리팩토링. 
    - [x] 도메인 엔티티를 presentation Layer로 반환했을 때의 문제점은 뭘까? 추후 고민해보기.
- [x] service 에서 정상 수행에 대한 테스트도 정의! 예외 케이스만 테스트하지 말자.
- [x] 컨트롤러 테스트에서 jsonPath를 사용해봄.
  - [x] service를 모킹 해 JSON 내용까지 검증. 
    - [x] service의 순서를 조율하고 DTO로 매핑하는 작업도 엄연히 로직이므로 내용까지 검증되어야 함!


</div>
</details>
