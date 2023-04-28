# jwp-shopping-cart

## [1단계]

### API 명세서

| Method |         URI         | Description |
|:------:|:-------------------:|:-----------:|
|  GET   |      /products      |    상품 목록    |
|  GET   |       /admin        |   어드민 페이지   |
|  POST  |   /admin/product    |    상품 추가    |
|  GET   | /admin/product/{id} |  상품 수정 페이지  |
|  PUT   | /admin/product/{id} |    상품 수정    |
| DELETE | /admin/product/{id} |    상품 삭제    |

### 기능 요구사항

- [x] 상품 목록 페이지 연동
    - [x] index.html 파일을 이용하여 상품 목록이 노출되는 페이지를 완성한다.
- [x] 상품 관리 CRUD API 작성
- [x] 관리자 도구 페이지 연동

## 질문

- 현재는 `Product` 라는 도메인 객체로 사용함과 동시에, 테이블을 매핑하는 '엔티티'의 역할도 가집니다.
  물론 현재 상황에서는 이 둘을 나눴을 때 무엇이 다를까 생각해보면, id 필드를 가지고 있고 없고의 차이밖에 없을 것 같아요.
  상황에 따라 Domain과 Entity를 나누고, 이에 따라 추가로 DTO, Response 객체를 만드는 것이 현재 프로젝트 규모를 고려하면 불필요한 비용이지 않을까 생각합니다.
  만약 제이온이라면 현재 상황에서 도메인 객체와 엔티티 객체로 분리를 할 것인가요?

- 현재 `ProductService`에서 `findAll()`의 반환 타입으로 `List<Product>`를 반환합니다.
  즉, 도메인이 컨트롤러에 노출이 되어도 상관없나요?
  관련 글들을 찾아 보면, '민감한 정보가 노출될 수 있다는 점'과 '컨트롤러에서 도메인 객체 내의 로직을 수행할 수 있다는 가능성'이 도메인 객체를 컨트롤러에 노출하면 안된다고 하는데요.
  현재 `Product`의 경우는 민감한 정보나 로직을 포함하지 않기 때문에, 굳이 DTO를 만들 필요는 없겠다고 판단합니다.
  제이온은 어떻게 생각하시나요?
