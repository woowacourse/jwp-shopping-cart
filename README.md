# 장바구니
장바구니 미션 저장소

## ✏️ Code Review Process
[텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## API 명세
[API 명세](https://www.notion.so/1-055b3cdd5c3c410ea4a64c36ee9bca8c)

## 1단계 기능 목록
- [x] 회원 가입
    - [x] [ERROR] email 중복 예외 처리
    - [x] [ERROR] 전화번호 형식 확인
    - [x] [ERROR] password 형식 확인
    - [x] [ERROR] 이름 형식 확인
- [x] 로그인 (토큰 발급)
  - [x] [ERROR] email 이 존재하지 않거나, password 가 일치하지 않는 경우 예외 처리 (custom exception)
- [x] 회원 정보 조회 (토큰 활용)
  - [x] [ERROR] 유효하지 않은 토큰일 경우 예외 처리
  - [x] [ERROR] 회원이 존재하지 않을 경우 예외 처리
- [x] 회원 정보 수정 (토큰 활용)
    - [x] [ERROR] 유효하지 않은 토큰일 경우 예외 처리
    - [x] [ERROR] 회원이 존재하지 않을 경우 예외 처리
    - [x] [ERROR] 전화번호 형식 확인
    - [x] [ERROR] password 형식 확인
- [x] 회원 탈퇴 (토큰 활용)
    - [x] [ERROR] 회원이 존재하지 않을 경우 예외 처리
    - [x] [ERROR] 유효하지 않은 토큰일 경우 예외 처리

## 2단계 기능 목록
- [x] 이름 조회
  - [x] [ERROR] 유효하지 않은 토큰일 경우 예외 처리
  - [x] [ERROR] 회원이 존재하지 않을 경우 예외 처리
- [ ] 사용자 인가처리 interceptor 이용하여 구현
- [x] 상품 목록 조회
  - [x] 저장되어 있는지 확인 기능
- [x] 장바구니 담기
  - [x] [ERROR] 회원이 존재하지 않을 경우 예외 처리
  - [x] [ERROR] 수량이 양수가 아닐 경우 예외 처리
- [x] 장바구니 삭제
  - [x] [ERROR] 회원이 존재하지 않을 경우 예외 처리
  - [x] [ERROR] 상품이 존재하지 않을 경우 예외 처리
- [x] 장바구니에 담긴 상품 목록 조회
  - [x] [ERROR] 회원이 존재하지 않을 경우 예외 처리
- [x] 장바구니 상품의 수량 수정
  - [x] [ERROR] 회원이 존재하지 않을 경우 예외 처리
  - [x] [ERROR] 상품이 존재하지 않을 경우 예외 처리
  - [x] [ERROR] 수량이 양수가 아닐 경우 예외 처리
