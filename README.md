# 장바구니 미션

## 개요

- Spring Web MVC를 이용하여 쇼핑몰의 상품 관리 기능 구현
    - 상품 생성
    - 상품 목록 조회
    - 상품 수정
    - 상품 삭제

- 상품 관리 페이지를 Thymeleaf를 이용하여 랜더링
    - `/` : 상품 목록 페이지
    - `/admin` : 관리자 도구 페이지

## 기능 목록

### Controller

- [x]  관리자 도구 페이지를 반환한다. ( GET “/admin” )
    - Response Body : 모든 상품의 정보 ( ID, 이름, 가격, 이미지URL, 카테고리 이름 목록)
- [x]  상품 목록 페이지를 반환한다. ( GET “/” )
    - Response Body : 모든 상품의 정보 ( ID, 이름, 가격, 이미지URL, 카테고리 이름 목록)
- [x]  상품을 생성한다. ( POST "/products" )
    - Request Body :  상품의 정보 ( 이름, 가격, 이미지URL, 카테고리 아이디 목록 )
    - Response Header Location  : /products/{상품 ID}
- [x]  상품을 수정한다. ( PUT "/products/{id}" )
    - Request Body  : 상품의 정보 ( 이름, 가격, 이미지URL, 카테고리 아이디 목록 )
    - Response Body : 없음
- [x]  상품을 삭제한다. ( DELETE "/products/{id}" )
    - Request Body  : 없음
    - Response Body : 없음

### Service

- [x]  상품 생성
- [x]  상품 목록 조회
- [x]  상품 수정
- [x]  상품 삭제

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
