# 기능목록
## 1단계

- [x] database 세팅
    - [x] data.sql(product table) 생성

- [x] 상품 관리 CRUD API 작성
    - [x] Create
        - [x] repository 구현
        - [x] service 구현
        - [x] controller 구현
    - [x] Read
        - [x] repository 구현
        - [x] service 구현
        - [x] controller 구현
    - [x] Update
        - [x] repository 구현
        - [x] service 구현
        - [x] controller 구현
    - [x] Delete
        - [x] repository 구현
        - [x] service 구현
        - [x] controller 구현


- [x] 상품 목록 페이지 연동
    - [x] "/" : index.html 를 보낸다

- [x] 관리자 도구 페이지 연동
    - [x] "/admin" : admin.html 를 보낸다

---
## 2단계

- [ ] database 세팅
  - [ ] user table 생성
  - [ ] cart table 생성 및 연관 관계 설정
  - [ ] data.sql 수정

- [ ] 사용자 설정
  - [ ] basic authorization 구현
  - [ ] 로그인 된 사용자 정보 조회 기능 구현
  - [ ] 사용자 설정 페이지 연동

- [ ] 장바구니 설정
  - [ ] 장바구니에 상품 추가 api 구현
    - [ ] repository 구현
    - [ ] service 구현
    - [ ] controller 구현
  - [ ] 장바구니 조회 api 구현
    - [ ] repository 구현
    - [ ] service 구현
    - [ ] controller 구현
  - [ ] 장바구니에서 상품 삭제 api 구현
    - [ ] repository 구현
    - [ ] service 구현
    - [ ] controller 구현
  - [ ] 장바구니 페이지 연동

- [ ] 상품 설정
  - [ ] 상품 삭제할 때 모든 장바구니에서 해당 상품이 삭제되도록 수정
