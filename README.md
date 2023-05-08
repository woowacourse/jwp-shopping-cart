# jwp-shopping-cart

# 1단계

## 🎯 기능 목록

- [x]  상품 목록 페이지 연동
    - [x]  상품 목록 표시
- [x]  상품 관리 CRUD API
    - [x]  Create
    - [x]  Read
    - [x]  Update
    - [x]  Delete
- [x]  관리자 도구 페이지 연동
    - [x]  `/admin` url로 관리자 도구 페이지 조회
    - [x]  상품 관리 CRUD API 연동
- [x] 상품
    - [x] 상품의 이름의 길이는 1자 이상 64자 이하이다.
    - [x] 상품은 최대 10_000_000원 이다.
    - [x] 상품의 이미지는 null일 수 있다.
    - [x] 상품 이미지 주소의 최대 길이는 2048이다.
    - [x] 이미지가 null일 경우 default image를 보여준다.

---

# 2단계

## 🎯 기능 목록

- [x]  테이블 생성
    - [x]  사용자 테이블
    - [x]  장바구니 테이블
- [x]  사용자 기능 구현
    - [x]  사용자 목록 표시
- [x]  사용자 설정 페이지 연동
    - [x]  API 연동
- [x]  장바구니 기능 구현
    - [x]  인증 기능
    - [x]  물건 추가
        - [x] 상품, 사용자 ID 존재 여부 검증
    - [x]  물건 삭제
    - [x]  사용자별 장바구니 물건 표시
- [x]  장바구니 페이지 연동
    - [x]  CRD API 연동

- [x] 사용자 검증
    - [x] 이메일의 길이는 1자 이상 32자 이하이다.
    - [x] 이메일의 형식을 만족해야 한다. (xx@xxx.xx)
    - [x] 비밀번호는 1자 이상 32자 이하이다.

---

# 🛠️ 설계

## DB

Product

| column | type        |                    |
|--------|-------------|--------------------|
| id     | BIGINT      | PK, AUTO_INCREMENT |
| name   | VARCHAR(64) |                    |
| price  | INT         |                    |
| image  | TEXT        | NULLABLE           |

User

| column   | type        |                    |
|----------|-------------|--------------------|
| id       | BIGINT      | PK, AUTO_INCREMENT |
| email    | VARCHAR(32) |                    |
| password | VARCHAR(32) |                    |

Cart

| column     | type   |                                   |
|------------|--------|-----------------------------------|
| id         | BIGINT | PK, AUTO_INCREMENT                |
| user_id    | BIGINT | FOREIGN KEY(user_list:id) CASCADE |
| product_id | BIGINT | FOREIGN KEY(product:id) CASCADE   |

## API

Header, ResponseBody를 포함한 세부 내용은 http 패키지에서 확인할 수 있습니다.

- ProductController
    - Create: POST /product
    - Update: PUT /product/{id}
    - Delete: DELETE/product/{id}
- HomeController
    - Read: GET /
- AdminController
    - Read: GET /admin
- SettingController
    - Read: GET /settings
- CartController
    - Create: POST /cart/items
    - Read: GET /cart/items
    - Delete: DELETE /cart/items/{id}

---

### ✔️ 실행 시

실행 테스트의 편의를 위해 data.sql 하단에 Dummy Data를 삽입하는 쿼리를 추가하였습니다.
데이터가 완전히 없는 상태에서의 테스트를 원할 경우 해당 부분을 주석처리해 주세요.



