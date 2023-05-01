# 🛒 장바구니 미션

## 📝 개요

- Spring Web MVC를 이용하여 쇼핑몰의 상품 관리 기능 구현
    - 상품 생성
    - 상품 목록 조회
    - 상품 수정
    - 상품 삭제

- 장바구니 기능은 인증 기반으로 구현한다.
    - 상품 추가
    - 상품 제거
    - 목록 조회

- 상품 관리 페이지를 Thymeleaf를 이용하여 랜더링
    - `/` : 상품 목록 페이지
    - `/admin` : 관리자 도구 페이지

- 설정 페이지에서 사용자 설정을 한다.
    - 어떤 사용자의 장바구니에 상품을 추가하거나 제거할 것 인지는 Basic Auth 를 이용하여 인증한다
    - Request Header의 Authorization 필드를 사용하여 인증 처리를 한다.

## ⚙️ 기능 목록

### Controller

#### ViewController

- [x]  관리자 도구 페이지를 반환한다. ( GET “/admin” )
    - Response Body : 모든 상품의 정보 ( ID, 이름, 가격, 이미지URL, 카테고리 이름 목록)
- [x]  상품 목록 페이지를 반환한다. ( GET “/” )
    - Response Body : 모든 상품의 정보 ( ID, 이름, 가격, 이미지URL, 카테고리 이름 목록)
- [x] 모든 사용자의 정보를 확인한다. ( GET "/settings" )
    - Request Body : 없음
    - Response Body : 모든 사용자들의 정보
- [ ] 장바구니 목록을 조회한다. ( GET "/cart" )
    - Request Body : 없음
    - Response Body : 장바구니에 담긴 모든 상품의 정보

#### ProductApiController

- [x]  상품을 생성한다. ( POST "/products" )
    - Request Body :  상품의 정보 ( 이름, 가격, 이미지URL, 카테고리 아이디 목록 )
    - Response Header Location  : /products/{상품 ID}
- [x]  상품을 수정한다. ( PUT "/products/{id}" )
    - Request Body  : 상품의 정보 ( 이름, 가격, 이미지URL, 카테고리 아이디 목록 )
    - Response Body : 없음
- [x]  상품을 삭제한다. ( DELETE "/products/{id}" )
    - Request Body  : 없음
    - Response Body : 없음 ( Status Code : 201 (CREATED) )

#### CartApiController

- [ ] 상품 목록 페이지에서 상품을 장바구니에 추가한다. ( POST "/cart/{productId}" )
    - Request Body : 상품 Id
    - Response Body : 없음 ( Status Code : 204(CREATED) )
- [ ] 장바구니에 담긴 상품을 제거한다. ( DELETE "/cart/{productId}" )
    - Request Body : 없음
    - Response Body : 없음 ( Status Code : 204 (NO_CONTENT) )

### Service

- 상품 관련
    - [x]  상품 생성
    - [x]  상품 목록 조회
    - [x]  상품 수정
    - [x]  상품 삭제

- 장바구니 관련
    - [ ] 장바구니에 상품 추가
    - [ ] 장바구니의 상품 제거
    - [ ] 장바구니 목록 조회

- 사용자 관련
    - [x] 사용자 목록 조회

### DB

- PRODUCT
    - [x]  ID  ( INT NOT NULL AUTO_INCREMENT )
    - [x]  NAME ( VARCHAR(50) NOT NULL )
    - [x]  IMAGE_URL ( TEXT NOT NULL )
    - [x]  PRICE ( INT NOT NULL )
    - [x]  DESCRIPTION ( VARCHAR(255) )
    - [x]  PRIMARY KEY ( ID )

- CATEGORY
    - [x]  ID  ( INT NOT NULL AUTO_INCREMENT PK )
    - [x]  NAME ( VARCHAR(50) NOT NULL )
    - [x]  PRIMARY KEY ( ID )

- PRODUCT_CATEGORY
    - [x]  ID  ( INT NOT NULL AUTO_INCREMENT PK )
    - [x]  PRODUCT_ID
    - [x]  CATEGORY_ID
    - [x]  PRIMARY KEY (ID)
    - [x]  FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT(ID)
    - [x]  FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORY(ID)

- CUSTOMER
    - [x]  ID  ( INT NOT NULL AUTO_INCREMENT PK )
    - [x]  PASSWORD ( VARCHAR(255) NOT NULL )
    - [x]  EMAIL ( VARCHAR(255) NOT NULL UNIQUE )
    - [x]  PRIMARY KEY ( ID )

- CART
    - [x]  ID  ( INT NOT NULL AUTO_INCREMENT PK )
    - [x]  CUSTOMER_ID
    - [x]  PRODUCT_ID
    - [x]  PRIMARY KEY (ID)
    - [x]  FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMER(ID)
    - [x]  FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT(ID)

### DAO

- 상품 DAO
    - [x]  조회
    - [x]  생성
    - [x]  수정
    - [x]  삭제
- 카테고리 DAO
    - [x]  조회
- 상품카테고리 DAO
    - [x]  조회
    - [x]  생성
    - [x]  삭제
- 고객 DAO
    - [x] 생성
    - [x] 조회
- 장바구니 DAO
    - [x] 상품 추가
    - [x] 상품 제거
    - [x] 고객이 담은 상품 ID 목록 조회
