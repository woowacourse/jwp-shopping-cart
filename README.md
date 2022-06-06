# 장바구니
장바구니 미션 저장소


## 🧺 기능 요구사항
### 회원
- [x] 회원가입 기능
  - [x] username
    - [x] 영어와 숫자로 이루어져야 한다.
    - [x] 길이는 3자 이상 15자 이하여야 한다.
  - [x] password
    - [x] 영어와 숫자로 이루어져야 한다.
    - [x] 길이는 8자 이상 20자 이하여야 한다.
  - [x] phoneNumber
    - [x] ('-'가 없이) 11자리 숫자이어야 한다.
  - [x] address
- [x] 로그인
  - [x] JWT 기반 토큰인증
- [x] 회원 정보 조회
- [x] 회원 정보 수정
  - [x] password 
  - [x] phoneNumber
  - [x] address
- [x] 회원 탈퇴

### 상품
- [x] 상품 추가
  - [x] name
  - [x] price
    - [x] 0보다 큰 정수여야 한다.
  - [x] stock
    - [x] 0보다 큰 정수여야 한다.
  - [x] imageURL
- [x] 상품 전체 조회
- [x] 상품 단일 조회
- [x] 상품 삭제

### 카트
- [x] 카트 아이템 추가
  - [x] productId
  - [x] quantity
- [x] 카트 아이템 전체 조회
- [x] 카트 아이템 수량 수정
  - [x] quantity
- [x] 카트 아이템 삭제

### 주문
- [x] 주문 추가
- [x] 주문 전체 확인
- [x] 주문 상세 확인

### API 스펙
- [x] API 스펙은 [API 문서](https://www.notion.so/a00bc92443f04c52a852ce16501e981a) 참고

## ✏️ Code Review Process
[텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)