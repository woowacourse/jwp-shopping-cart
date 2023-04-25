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

- [ ]  관리자 도구 페이지를 반환한다. ( “/admin” )
    - Response Body : 모든 상품의 정보 ( ID, 이름, 가격, 이미지URL )
- [ ]  상품 목록 페이지를 반환한다. ( “/” )
    - Response Body : 모든 상품의 정보 ( ID, 이름, 가격, 이미지URL )
- [ ]  상품을 생성하고 해당 URL의 GET 메서드로 리다이렉트한다 .
    - Request Body :  이름, 가격, 이미지URL
    - Response Body  : 상품 ID
- [ ]  상품을 수정하고 해당 URL의 GET 메서드로 리다이렉트한다 .
    - Request Body  : ID, 이름, 가격, 이미지URL
    - Response Body : 없음
- [ ]  상품을 삭제하고 해당 URL의 GET 메서드로 리다이렉트한다 .
    - Request Body  : ID
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
    - [x]  IMAGE_URL ( VARCHAR(255) NOT NULL )
    - [x]  PRICE ( INT NOT NULL )
    - [x]  DESCRIPTION ( TEXT )
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
