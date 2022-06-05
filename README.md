# 장바구니
장바구니 미션 저장소

## ✏️ Code Review Process
[텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

# ReadMe.md
****
# 기능구현 목록
 
## 회원 가입
- [x]  email, password, username 을 받는다.
- [x]  각 요소별로 중복, 공백 및 비어있는 값, 길이를 검증한다.
    - [x]  검증에 실패하면 400 상태코드를 반환한다.
- [x]  데이터베이스에 회원정보를 추가한다.
- [x]  created에 Location 헤더에 만들어진 리소스 URI를 담아서 반환한다.

## 로그인
- [x]  email, password 를 받는다.
- [x]  데이터베이스에 email 이 일치하는 데이터를 가져온다.
    - [x]  데이터가 없으면 401 상태코드를 반환한다.
- [x]  가져온 데이터의 password와 요청된 password를 암호화하여 비교한다.
    - [x]  일치하지 않으면 401 상태코드를 반환한다.
- [x]  일치한다면 토큰을 만든다.
- [x]  토큰과 유효시간, 및 유저정보를 반환한다.

## 회원정보 수정
- [x]  헤더에서 authorization 을 추출하여 토큰을 얻는다.
- [x]  토큰 내의 id 와 Path 내의 id가 일치하는지 확인한다.
    - [x]  일치하지 않으면 403 상태코드를 반환한다,
- [x]  username을 받는다.
- [x]  중복, 공백 및 비어있는 값, 길이를 검증한다.
    - [x]  검증에 실패하면 400 상태코드를 반환한다.
- [x]  데이터베이스 내의 username을 수정한다.

## 회원 탈퇴
- [x]  헤더에서 authorization 을 추출하여 토큰을 얻는다.
- [x]  토큰 내의 id 와 Path 내의 id가 일치하는지 확인한다.
    - [x]  일치하지 않으면 403 상태코드를 반환한다,
- [x]  데이터베이스 내의 회원정보를 삭제한다.

# 코드 컨벤션
- 전체 변수에 final
- 테스트 메소드 이름(메소드명_상황)은 영어로, displayName은 한글로 서술형
- 코드 포멧터는 우테코 포멧터
- 메소드 순서는 실행 순서대로 연관된 것들끼리
- 에러 메세지는 일반 문자열로
- jdbc RowMapper는 변수로 추출
