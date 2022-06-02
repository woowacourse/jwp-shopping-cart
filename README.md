# 장바구니
장바구니 미션 저장소

## 1단계 기능 목록

### 회원 기능
- [X] 회원 가입
  - ID
    - 4자리 이상, 12자리 이하이다.
    - 숫자로 시작할 수 없다
    - 소문자, 숫자, _로 구성되어 있다.
  - PW의 경우 8자리 이상을 입력하여야 한다.
- [X] 내 정보 조회
- [X] 내 정보 수정
- [X] 회원 탈퇴
- [X] 로그인

### 회원 기능 API 명세

| Method | Url                           | Description |
|--------|-------------------------------|-------------|
| POST   | /api/customers                | 회원 가입       |
| GET    | /api/customers/{customerName} | 내 정보 조회     |
| PUT    | /api/customers/{customerName} | 내 정보 수정     |
| DELETE | /api/customers/{customerName} | 회원 탈퇴       |
| POST | /api/login/token | 로그인         |

<br>

## 📝 License

This project is [MIT](https://github.com/woowacourse/jwp-shopping-cart/blob/master/LICENSE) licensed.
