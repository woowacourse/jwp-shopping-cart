# 🛍️ 장바구니 Project

# 🎯 기능 요구사항

## 📘 1. 페이지 연동

###  ✅ 상품 목록 페이지 연동
  - [x] `/` url로 접속했을 때, 바로 상품 목록 페이지로 접속하도록 연동한다.
  - [x] `index.html`에 조회된 정보 표시

<br>

### ✅ 관리자 도구 페이지 연동
- [x] `/admin` url로 접속했을 때, 관리자 페이지로 접속하도록 연동한다.
- [x] `admin.html`에 조회된 정보 표시

<br>

### ✅ 사용자 설정 페이지 연동
- [x] `/member/settings` url로 접속했을 때, 사용자 설정 페이지로 접속하도록 연동한다.
- [x] `settings.html`에 조회된 정보 표시

<br>

### ✅ 장바구니 페이지 연동
- [ ] `/cart` url로 접속했을 때, 장바구니 페이지로 접속하도록 연동한다.
- [ ] `cart.html`에 조회된 정보 표시

---

## 📘 2. API 구현

## 🎯 2-1. 상품 API 구현

### ✅ 상품 목록 조회 GET API 
  - [x] GET `/admin` uri 맵핑기
    - 조회될 상품 정보
      - 상품 id
      - 상품 이미지
      - 상품 이름
      - 상품 가격

<br>

### ✅ 상품 등록 API 
  - [x] POST `/admin/product` uri 맵핑 
    - 등록할 상품 정보
      - 상품 이름
      - 상품 가격
      - 상품 이미지 URL
        - [x] [validation] 이미지 URL은 필수이다. (빈 값, 공백 허용 X)

<br>

### ✅ 상품 수정 API 
  - [x] PUT `admin/product/{id}` uri 맵핑
    - 수정할 상품 정보
      - 상품 이름
      - 상품 가격
      - 상품 이미지 URL
        - [x] [validation] 이미지 URL은 필수이다. (빈 값, 공백 허용 X)
<br>

### ✅상품 삭제 API 
  - [x] DELETE `admin/product/{id}` uri 맵핑

<br>

## 🎯 2-2. 사용자 API 구현

### ✅ 사용자 등록 API
  - [x] POST `/member/register` uri 맵핑
    - 사용자 정보로 등록한다.
      - nickname
        - [x] [validation] 닉네임은 필수다. (공백, 빈 값 허용 X)
      - email
        - [x] [validation] 이메일은 필수다. (공백, 빈 값 허용 X)
        - [x] [validation] 이메일 형식이어야한다. (aaa@bb.cc)
      - password
        - [x] [validation] 비밀번호는 필수다. (공백, 빈 값 허용 X)
    
<br>

### ✅ 사용자 목록 조회 API
  - [x] GET `/members` uri 맵핑
    - 사용자 목록의 조회될 사용자 정보
      - nickname
      - email
      - password

## 🎯 2-3. 장바구니 API 구현
### ✅장바구니 목록 조회 API
  - [ ] GET `/carts` uri 맵핑
    - 조회될 상품 목록의 상품 정보
      - 상품 이미지
      - 상품 이름
      - 상품 가격

<br>

### ✅장바구니 담기 API
- [ ] POST  `/carts/{productId}` uri 맵핑 

<br>

### ✅장바구니 상품 삭제 API 
- [ ] DELETE `carts/{productId}` uri 맵핑

---

## 📘 3. 기능 구현

## 🎯 3-1. 상품 기능 구현

### ✅ 상품 목록 전체 조회 기능 
  - [x] 등록된 모든 상품을 조회한다.

<br>

### ✅ 상품 저장 기능 
  - [x] 요청받은 등록 상품 정보로 상품을 생성한다.
    - [x] [validation] 상품 이름은 1자 이상 50자 이하여야한다.
    - [x] [validation] 상품 가격은 양수여야 한다.

<br>


### ✅ 상품 정보 업데이트 기능 
- [x] 요청받은 수정 상품 정보와 상품 ID로 상품을 수정한다.
  - [x] [validation] 상품 이름은 1자 이상 50자 이하여야한다.
  - [x] [validation] 상품 가격은 양수여야한다.
  - [x] [exception] 상품 ID에 해당하는 상품이 없으면 예외가 발생한다.

<br>


### ✅ 상품 삭제 기능 
- [x] 요청받은 상품 ID에 해당하는 상품을 삭제한다.
  - [x] [exception] 상품 ID에 해당하는 상품이 없으면 예외가 발생한다.

<br>

## 🎯 3-2. 사용자 기능 구현

### ✅ 사용자 목록 조회 기능
  - [x] 모든 사용자 목록을 조회한다.

### ✅ 단일 사용자 조회 기능 
  - [ ] 이메일과 비밀번호를 받아서 사용자를 조회한다.
    - [ ] [exception] 이메일과 비밀번호로 조회된 사용자가 없으면 예외가 발생한다.

### ✅ 사용자 등록 기능 
  - [x] 사용자 정보를 받아서 사용자를 등록한다.
    - 등록 시 사용되는 사용자 정보
      - nickname
        - [x] [validation] 닉네임은 한글, 영어, 숫자만 가능하다.
        - [x] [validation] 닉네임은 2자 이상 8자 이하여야한다.
        - [ ] [exception] 이미 있는 닉네임이면 에외가 발생한다.
      - email
        - [ ] [exception] 이미 있는 이메일이면 예외가 발생한다.
      - password
        - [x] [validation] 비밀번호는 4자 이상이어야 한다.

<br>

## 🎯 3-3. 장바구니 기능 구현

### ⚙️ 공통 상황
  - 사용자 정보 추출
    - [ ] 요청 Header에 `Authorization` 필드 값으로 사용자의 정보를 추출하여 파라미터로 담는다.
      - [ ] [exception] 요청 Header에 `Authorization` 필드 값이 존재하지 않으면 예외가 발생한다.
      - 추출한 값은 email:password를 base64로 인코딩한 문자열이다.
      - [ ] 추출 값을 decoding & parsing 하여 이메일과 비밀번호를 추출한다.
      - [ ] 파라미터로 담는 과정에서 커스텀 ArgumentResolver를 구현한다.

<br>

### ✅장바구니 상품 추가 기능
  - [ ] 사용자 정보와 추가할 상품 ID로 해당 사용자 장바구니에 상품을 추가한다.
    - [ ] [exception] 해당 사용자의 장바구니에 추가할 상품 ID에 해당하는 상품이 없으면 예외가 발생한다.

<br>

### ✅장바구니 상품 삭제 기능 
  - [ ] 사용자 정보와 추가할 상품 ID로 해당 사용자 장바구니에 있는 상품을 삭제한다.
      - [ ] [exception] 해당 사용자의 장바구니에 삭제할 상품 ID에 해당하는 상품이 없으면 예외가 발생한다.

<br>

### ✅장바구니 목록 조회 기능
  - [ ] 사용자 정보로 해당 사용자의 장바구니 목록을 조회한다.

---

## 📘 4. DB 설계

<img width="801" alt="장바구니 ERD 설계" src="https://user-images.githubusercontent.com/95729738/235354560-46ba18c6-84e2-4896-8587-4c3dabc75870.png">



