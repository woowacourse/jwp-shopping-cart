# 장바구니
장바구니 미션 저장소

#1단계 요구사항 정리

- [x] 회원가입
  - [x] 요청으로 이름, 이메일, 비밀번호를 받는다.
  - [x] 응답으로 이름, 이메일을 반환한다. 201
- [x] 로그인
  - [x] 요청으로 이메일, 비밀번호를 받는다.
  - [x] 응답으로 이름, 이메일, 토큰을 반환한다. 200
- [x] 회원 정보 조회
  - [x] 요청으로 토큰을 받는다.
  - [x] 응답으로 이름, 이메일을 반환한다. 200
- [x] 회원 정보 수정 (비밀번호 수정)
  - [x] 요청으로 기존 비밀번호와 바꿀 비밀번호, 토큰을 받는다.
  - [x] 응답으로 이름을 반환하다. 200
- [x] 회원 탈퇴
  - [x] 요청으로 비밀번호와 토큰을 받는다.
  - [x] 204

[API 문서](https://www.notion.so/brorae/1-API-c10e17f6fdc940bbb2379ec7e07b1cb4)
## ✏️ Code Review Process
[텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 1단게 수정 사항 & 피드백 정리

- [ ] Acceptance test를 하면서 실제 사용자가 생각하는 흐름을 정리해보자
  - 위의 두가지 경우처럼 나눌 수도 있고 더 다양하게 나누는 방법이 있을텐데요.
    알린이 생각하는 시나리오를 한번 생각해보고 그것에 맞게 테스트를 작성해 보면 좋을 것 같아요!
    시나리오로 모든 경우를 테스트할 수 없기 때문에 controller로 통합테스트를 진행하는 것이지 않을까요?
- [ ] interceptor가 ui 패키지에 포함되어 있다. ui보다 config에 있는게 맞지 않을까? ui에 포함하게된 기준이 무엇이었을까?
- [ ] webpage를 보며 me라는 표현을 본적이 없지 않나요?
- [x] exception과 runtimeException을 같이 잡은 이유가 있을까요??
  - 둘의 차이점을 크게 생각하지 않고 내가 예상할 수 있는 예외를 제외한 모든 예외를 잡아야겠다는 생각으로 Exception을 핸들링하고 있던 것 같습니다.
  - 프로그램이 실행되면서 발생하는 예외만 잡는다면 runtimeException만 잡아도 될 것 같습니다.
- [x] 로그인 시 `isValidPasswordByEmail()`에 대한 테스트가 없는 것 같다.
- [x] (JwtTokenProvider.getPayload) 한줄에 여러개를 모두 표현하기보다 한줄에 한개씩 표현하는게 더 명확한거 같아요!
- [x] service에서 dto로 변환하여 넘겨주는 것이 역할에 맞을까요??
  - service에서 도메인을 넘겨주고 컨트롤러에서 변환하는 방법과 dto를 넘겨주는 것으로 크루들과 토론한 적이 있습니다. 
  - 도메인 반환 파
    1. 외부의 상황에 따라 컨트롤러가 다를 수 있는데(콘솔/웹 등) 각 컨트롤러에 맞는 DTO를 반환하는 서비스나 메서드를 만들것인가? 도메인 반환이 재사용성이 좋다.
    2. 도메인을 넘기면 앞단에서 수정이 발생해도 서비스의 수정이 생기지 않을 수 있다.
  - DTO 반환 파
    1. 단 하나의 도메인을 반환하는 간단한 경우라면 하나의 도메인 반환으로 해결이 되겠지만 여러 도메인을 반환해야하는 등 로직이 복잡해진다면 모든 도메인을 반환할 것인가?
    2. 도메인의 수정이 생기면 컨트롤러까지 영향을 받는것이 아닐까?
    3. 컨트롤러에서 도메인 로직이 실행되거나 수정될 수 있는 위험을 감수할 필요가 있나?
  - 해당 토론에서는 이런 이야기들이 나왔던 것 같습니다. 저는 DTO 반환파의 1번째, 2번째 이유로 서비스에서 DTO를 반환하는 편입니다.
  - 코드를 다시 보니 서비스에서 로직으로 dto 생성까지 하고 있는데 이부분은 조금 수정할 수 있을 것이라고 생각하여 service가 아닌 dto에서 정적 팩터리 메서드를 이용하도록 수정했습니다.
- [x] Request Dto 의 기본 생성자가 private여도 될 것 같다.
