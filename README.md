# jwp-shopping-cart

## 기능 요구사항 목록
- [x] 상품 목록 페이지 연동
  - [x] index.html 파일이 / url을 통해 접근되도록 설정
  - [x] 상품 목록을 담고 있는 Html을 응답한다
    - 상품 기본 정보
      - 상품 ID
      - 상품 이름
      - 상품 이미지
      - 상품 가격
- [x] 상품 관리 CRUD API 작성
  - [x] 상품 생성
    - [x] POST /item 요청과 매핑
    - [x] db에 저장한다
  - [x] 상품 목록 조회
    - [x] GET /item 요청과 매핑
    - [x] db의 item 목록을 전부 조회한다
  - [x] 상품 수정
    - [x] PUT /item 요청과 매핑
    - [x] 특정 item의 정보를 수정한다
  - [x] 상품 삭제
    - [x] DELETE /item 요청과 매핑
    - [x] 특정 item을 삭제한다
- [x] 관리자 도구 페이지 연동
  - [x] /admin 요청
    - [x] 모든 상품 목록을 담고 있는 html을 응답한다
  - [x] 상품 추가 버튼 클릭
    - [x] 추가할 상품 정보를 입력한다
    - [x] submit 버튼을 클릭해 입력한 상품 정보를 저장한다
  - [x] 수정 버튼 클릭
    - [x] 수정할 상품 정보를 입력한다
    - [x] submit 버튼을 클릭해 해당 행의 상품 정보를 수정한다
  - [x] 삭제 버튼 클릭
    - [x] 해당 행의 정보를 삭제한다

---

## 기능 요구사항 목록 (2단계)

- [ ] 사용자 기능 구현
  - [ ] 사용자 정보 관리 CRUD API 작성
    - [ ] 사용자 생성
      - [ ] POST /user 요청과 매핑
      - [ ] db에 저장한다
    - [ ] 사용자 목록 조회
      - [ ] GET /user 요청과 매핑
      - [ ] db의 user 목록을 전부 조회한다
    - [ ] 사용자 수정
      - [ ] PUT /user 요청과 매핑
      - [ ] 특정 user 정보를 수정한다
    - [ ] 사용자 삭제
      - [ ] DELETE /user 요청과 매핑
      - [ ] 특정 user를 삭제한다
- [ ] 사용자 설정 페이지 연동
  - [ ] settings.html 파일이 /settings url을 통해 접근되도록 설정
  - [ ] 사용자 목록을 담고 있는 Html을 응답한다
    - 사용자 기본 정보
      - 사용자 이메일
      - 사용자 비밀번호
- [ ] 장바구니 기능 구현
  - [ ] 장바구니 정보 관리 CRUD API 작성
    - [ ] 장바구니 생성
      - [ ] POST /cart 요청과 매핑
      - [ ] db에 저장한다
    - [ ] 장바구니 목록 조회
      - [ ] GET /cart 요청과 매핑
      - [ ] db의 cart 목록을 전부 조회한다
    - [ ] 장바구니 수정
      - [ ] PUT /cart 요청과 매핑
      - [ ] 특정 cart 정보를 수정한다
    - [ ] 장바구니 삭제
      - [ ] DELETE /cart 요청과 매핑
      - [ ] 특정 cart를 삭제한다
- [ ] 장바구니 페이지 연동
  - [ ] cart.html 파일이 /cart url을 통해 접근되도록 설정
  - [ ] 특정 사용자의 장바구니 목록을 담고 있는 Html을 응답한다
