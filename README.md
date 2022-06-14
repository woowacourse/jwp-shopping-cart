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

#2단계 요구사항 정리

- [x] 페이지별 상품 목록 조회
  - [x] 요청으로 페이지별 상품 갯수, 해당 페이지를 파라미터로 받는다.
  - [x] 응답으로 상품들의 id, name, price, imageUrl을 반환한다. 200
- [x] 상품 조회
  - [x] 요청으로 상품ID를 받는다.
  - [x] 응답으로 상품의 id, name, price, imageUrl을 반환한다. 200
- [x] 장바구니 상품 추가
  - [x] 요청으로 상품의 ID, quantity, checked를 받는다.
  - [x] 201
- [x] 장바구니 상품 조회 
  - [x] 요청으로 토큰을 받는다.
  - [x] 응답으로 cartId, name, price, imageUrl, quantity, checked를 반환한다. 200
- [x] 장바구니 선택 상품 제거
  - [x] 요청으로 카트ID들과 토큰을 받는다.
  - [x] 204
- [x] 장바구니 전체 상품 제거
  - [x] 요청으로 토큰을 받는다.
  - [x] 204
- [x] 장바구니 상품 정보 수정
  - [x] 요청으로 cartId, quantity, checked와 토큰을 받는다.
  - [x] 응답으로 cartId, name, price, imageUrl, quantity, checked를 반환한다. 200

[API 문서](https://www.notion.so/brorae/1-API-c10e17f6fdc940bbb2379ec7e07b1cb4)
## ✏️ Code Review Process
[텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)
