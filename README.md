# jwp-shopping-cart

## 미션 소개

이 미션은 Spring Web MVC를 이용하여 쇼핑몰의 상품 관리 기능을 구현하는 미션입니다.
상품 관리 페이지를 Thymeleaf를 이용하여 확인할 수 있도록 코드를 제공합니다.
이를 위해 상품 관리 CRUD API를 구현하고 사용자를 위한 상품 목록 페이지와 어드민을 위한 상품 관리 페이지를 연동해야 합니다.

## 미션 요구사항

- [ ] 상품 목록 페이지 연동
    - [ ] 상품 목록 페이지 연결 (/products)
- [ ] 관리자 도구 페이지 연동
    - [ ] 관리자 도구 페이지 연동 (/admin)
- [ ] 상품 관리 CRUD API 작성
    - [ ] 상품 생성
        - [POST] /admin/product
    - [ ] 상품 목록 조회
        - [GET] /admin/products
    - [ ] 상품 수정
        - [UPDATE] /admin
    - [ ] 상품 삭제
        - [DELETE] /admin
