# 🛒 장바구니

> 장바구니 미션 저장소

## 🛠 1단계 기능 목록

### 회원 기능

- [X] 회원 가입
- [X] 내 정보 조회
- [X] 내 정보 수정
- [X] 회원 탈퇴
- [X] 로그인

### 리팩토링
- [X] POST /api/customers/duplication를 GET /api/customers/exists?userName=ellie로 변경

<br>

## 🛠 2단계 기능 목록

- [X] 장바구니, 주문 요청 URL에서 {customerName} 제거
  - Access Token을 사용해 사용자 정보를 확인할 수 있도록 한다. 
- [X] 장바구니, 주문 관리 Controller에서 DTO 사용하도록 변경
- [X] CartItem에 quantity 추가
  - quantity는 1~99개로 제한한다. 
- [X] CartItem의 quantity 변경 API 추가
  - PATCH /api/customers/me/carts
- [X] Product 전체 목록 조회 시 cartId, cartQuantity도 보내도록 변경
  - 토큰의 존재 여부에 따라 다르게 동작하게 해야한다. 

### 리팩토링

- [X] 이미 장바구니에 존재하는 상품을 또 장바구니에 담는 경우, 하나로 합치도록 변경
- [ ] 장바구니, 주문 DAO에서 SimpleInsert, NamedParameterJdbc 사용하도록 변경

<br>

## 📟 API 명세

### 회원 기능 API 명세

| Method | Url                   | Description |
|--------|-----------------------|-------------|
| POST   | /api/customers        | 회원 가입       |
| GET    | /api/customers/me     | 내 정보 조회     |
| PUT    | /api/customers/me     | 내 정보 수정     |
| DELETE | /api/customers/me     | 회원 탈퇴       |
| POST   | /api/login            | 로그인         |
| GET    | /api/customers/exists | 회원 이름 중복 검사 |

### 제품 기능 API 명세

| Method | Url                        | Description |
|--------|----------------------------|-------------|
| POST   | /api/products              | 제품 추가       |
| GET    | /api/products            | 제품 목록 조회    |
| GET    | /api/products/{productId}          | 제품 정보 조회    |
| DELETE | /api/products/{productId}          | 제품 삭제       |

### 장바구니 기능 API 명세

| Method | Url                                       | Description    |
|--------|-------------------------------------------|----------------|
| POST   | /api/customers/me/carts                   | 장바구니에 제품 추가    |
| GET    | /api/customers/me/carts                   | 장바구니 제품 목록 조회  |
| DELETE | /api/customers/me/carts/{cartId}          | 장바구니의 제품 삭제    |
| PATCH  | /api/customers/me/carts/{cartId}/quantity | 장바구니의 제품 수량 변경 |

### 주문 기능 API 명세

| Method | Url                                       | Description    |
|--------|-------------------------------------------|----------------|
| POST   | /api/customers/me/orders                   | 주문 추가          |
| GET    | /api/customers/me/orders                   | 주문 목록 조회       |
| GET    | /api/customers/me/orders/{orderId}        | 주문 정보 조회       |

<br>

## 🔐 협업 기록

<details>
<summary>2022.05.27</summary>

> 프론트 : `코이`, `티거` / 백 : `엘리`, `판다`, `라쿤`, `기론`, `티키`

- 같이 기존 도메인을 살펴봤다.
- 간단히 API 명세에 대해 이야기 나눴다.

</details>

<details>
<summary>2022.05.30</summary>

> 백 : `엘리`, `판다`, `라쿤`, `기론`, `티키`

- '로그아웃을 어떻게 구현할 것인가?'에 대해 이야기 나눴다.
    - 프론트에서? 서버에서 블랙리스트를 만들어서?

</details>

<details>
<summary>2022.05.31</summary>

> 프론트 : `코이`, `티거` / 백 : `엘리`, `판다`, `라쿤`, `기론`, `티키`

- 회원 관리 기능 API 명세를 작성했다.
- '로그아웃 처리를 프론트/백 어디에서 해야할까?'에 대해 이야기 나눴다.
    - 프론트에서 토큰을 드랍한다.
- '관리자 기능을 만들것인가?'에 대해 이야기 나눴다.
    - 프로덕트 관리 기능을 관리자 기능으로..? 근데 프론트 미션이 아니다.
    - **데모에 필요한 상품 데이터는 백에서 넣는걸로..**😋
- 'HTTPS 이슈'에 대해 이야기 나눴다.
    - 추후에 알아보는 걸로..😅
- '회원 정보에 어떤 것을 포함시킬지?'에 대해 이야기 나눴다.
    - 사용자 이름과 비밀번호만을 포함시킨다.
- '토큰 만료 시간'에 대해 이야기 나눴다.
    - 1시간
- '이름, 비밀번호 제약'에 대해 이야기 나눴다.
    - 이름은 소문자, 숫자, 언더바(_)만 사용한다.
    - 비밀번호는 8자 이상

</details>

<details>
<summary>2022.06.03</summary>

> 프론트 : `코이`, `티거` / 백 : `엘리`, `판다`, `라쿤`, `기론`, `티키`

### 2단계 진행 관련 회의

- 백엔드와의 연결
  - 연결 이슈 발생💥 → 해결😋
  - DTO에서 회원 이름 변수명 통일해야함!! (userName)
- 회원 이름, 비밀번호 제약 다시 회의
  - 이름 : 5-20자, 소문자, 숫자, 언더바
  - 비밀번호 : 8-16자 , 소문자, 대문자, 숫자, 특수문자(!, @, #, $, %, ^, &, *, -, _), 전부 다 하나 이상!!
- 2단계 기능 및 API 명세 회의
  - CartItem에 quantity 추가, 수량 변경 API 추가
    - patch : /api/customers/me/carts/
  - Products를 보낼 때 장바구니에 담겨있는 수량, 담겨있는 카트 ID(default : 0)를 보낸다.
    - 토큰이 있는지, 없는지에 따라 로직이 달라진다.
  - 장바구니에 물품 수량은 1~99개로 제한한다.
- 에러 형식
  - DTO 사용해서 에러 메시지 보냄(message)
</details>

<br>

## ❤️ 일일 회고

<details>
<summary>2022.05.27</summary>

### 느낀점

`엘리`: 돌아가고 싶다. 엄마가 보고싶은 날이다🥺

`판다`: 좋은 사람들과 좋은 시간 ())))))))

ㄴ어떤게 좋았죠?

ㄴ 아무튼 좋았습니다.

ㄴ ..도망쳐

`킹규철`: 좋았다.

`찐기론`: 좋은 사람들을 만나서 좋았다.

`귀론`: 느꼈다.

`티키`: 여기 좋은건가..?너무좋아 - 도망쳐

`라쿤`: 판다가 너무 잘한다. 앞으로도 잘했으면 좋겠다.

ㄴ 무지개 반사

### 좋았던점

`킹규철`: 너무 좋았다.

`엘리`: 새로운 분들을 만나서 좋다.

`라쿤`: 재미있다.

`판다`: 뭔가 폭풍이 지나간거 같고 좋네요ㅎㅎ

`찐기론`: 다 좋았다.

`티키`: 판다가 재밌다. 꿈에 나올 듯

### 아쉬웠던점

`판다`: 다들 아무것도 안한다.

`킹규철`: 좋아서 아쉬웠다.

`찐기론`: 아쉬움

`엘리`: 옆에서 계속 한숨을 그만 쉬라고 한다. 이렇게 비난하기 전에 왜 한숨을 쉬게 되었는지 한번 생각해봤으면 좋겠다💥💥🔫

`라쿤`: 옆에서 계속 한숨을 쉰다. 비난하지 않았으면 좋겠다.

`티키`: 자꾸 누가 도망가네요
</details>

<details>
<summary>2022.05.30</summary>

### 느낀점

`엘리`: 오늘도 정신이 없다. 내 페이스를 찾고 싶다.

ㄴ 잘 하고 있습니다

`라쿤`: 페어 프로그래밍의 정수를 느낀 것 같다.

`판다`: 이게 옳게 된 페어 코딩인가…?

### 좋았던점

`엘리`: 그래도 라쿤과 판다가 실패 케이스에 대한 테스트를 작성해줘서 고마웠다. 판다가 먼저 드라이버를 하고 싶다고 말해줘서 고마웠다.

ㄴ 엘리가 드디어 독심술을 익히려 한다

`라쿤`: 판다가 많이 알려주면서 해줘서 도움이 많이 되었다. 판다는 베스트 드라이버이다.

`판다`: 엘리가 억지로라도 테스트를 짜게 해 줬다

### 아쉬웠던점

`엘리`: 라쿤이 계속 일일회고를 뒷담 시트라고 매도한다. 슬프다..

`라쿤`: 페어 전날~전전날에는 술을 안마셨으면 좋겠다

ㄴ억울해..😤

`판다`: 엘리가 회고에서 유언비어를 퍼트린다
</details>

<details>
<summary>2022.05.31</summary>

### 느낀점

`라쿤` : 협업다운 협업을 처음 해봐서 좋았다

`판다`: 프론트하고 API 맞춰보니까 뭘 좀 하는거 같기도 하고 좋네요^^

`기론`: 프론트하고 다 같이 얘기해보면서 하니깐 재밌는 것 같습니다^^

`티키`: 프론트하고 이야기 해보니 할게 많아진 느낌?

`엘리`: 앞으로 포스트맨과 안녕을 할 수 있을 것 같아 기대가 되네요..ㅎㅎ

`티거`: 백엔드,, 멋져요,,~~~! ㅋㅋㅋㅋㅋㅋㅋㅋㅋ 🥺

`코이`: 티거와 백엔드 분들이 있어서 든든하다. 재미있었다.

### 좋았던점

`라쿤` : 내가 이상하게 말해도 옆에서 판다가 커버를 해줘서 좋았다. 엘리가 술을 안마셔서 좋았다

`판다`: 시간이 빨리가는거 같아요. 퇴근이 다가와요. 엘리가 숙취가 없어보여서 좋네요

`기론`: 좋은 점은 많았습니다. 그 중 다들 열려있는게 좋았습니다.

`티키`: 분위기가 즐거워서 좋았고, 회의실 연장이 되어 좋았습니다.

`엘리`: 우리 페어 친구들이 평소보다 정상적이어서 좀 다행이었다. 오늘 꽤나 많은 이야기를 한 것 같아 좋다.

`티거`: 회의가 물 흐르듯 진행됐다 생각했던 것보다 빨리 끝났네요~!

`코이`: 회의가 잘끝나서 좋고 티거가 회의실을 지켜줘서 좋았다. (😉)

### 아쉬웠던점

`라쿤` : 판다가 아쉽다 ㅋㅋㅋㅋㅋ 뭐가 ㅋㅋㅋㅋ

`판다`: 아쉬운 점이 하나도 없어서 아쉽네요

`기론`: 위에서 재촉해서 아쉬웠다.

`티키`: 같이 밥을 한 번 먹지 못해 아쉽다. → 먹어요~~~!

`엘리`: 프론트 미션이 저희보다 늦게 시작해 백에서 만드는 API를 다 사용하는게 아니라 좀 아쉽습니다.. 준!!

`티거`: 딱히… 없긴 한데… 컨디션이 최상이 아니라서 개인적으로 아쉬움

`코이`: 흠,,, 딱히없어요
</details>

<br>

## 📝 License

This project is [MIT](https://github.com/woowacourse/jwp-shopping-cart/blob/master/LICENSE) licensed.
