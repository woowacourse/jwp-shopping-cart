# 장바구니
장바구니 미션 저장소

## ✏️ Code Review Process
[텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 1단계 기능 목록
- [ ] 회원 가입
    - [x] [ERROR] email 중복 예외 처리
    - [x] [ERROR] 전화번호 형식 확인
    - [x] [ERROR] password 형식 확인
    - [x] [ERROR] 이름 형식 확인
- [ ] 로그인 (토큰 발급)
  - [ ] [ERROR] 존재하지 않는 email 일 경우 예외 처리 (custom exception)
  - [ ] [ERROR] 비밀번호가 일치하지 않을 경우 예외 처리 (custom exception)
- [ ] 회원 정보 조회 (토큰 활용)
  - [ ] [ERROR] 유효하지 않은 토큰일 경우 예외 처리
  - [ ] [ERROR] 회원이 존재하지 않을 경우 예외 처리
- [ ] 회원 정보 수정 (토큰 활용)
    - [ ] [ERROR] 유효하지 않은 토큰일 경우 예외 처리
    - [ ] [ERROR] 전화번호 형식 확인
    - [ ] [ERROR] password 형식 확인
- [ ] 회원 탈퇴 (토큰 활용)
    - [ ] [ERROR] 유효하지 않은 토큰일 경우 예외 처리
