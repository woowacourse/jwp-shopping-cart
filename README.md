# jwp-shopping-cart 기능목록

## 상품 목록 페이지 연동

- [x] 상품은 아래와 같은 필드를 가지고 있다.
    - ID(long)
    - Name(String)
    - Image(String)
    - Price(int)

- [x] 컨트롤러는 아래와 같은 요청을 처리한다.
    - [x] '/' 경로로 get 요청이 들어올 경우, 상품 목록 페이지를 반환한다.

- [x] 상품서비스는 아래와 같은 기능을 한다.
    - [x] 전체 상품 목록을 DB에서 가져온다.
    - [x] 전체 상품 목록을 컨트롤러에 보낸다.

## 상품 관리 CRUD API 작성

- [ ] 상품 생성
    - '/api/create' 경로로 post 요청이 들어올 경우, HttpStatus.CREATED 반환한다.
        - [ ] Controller에서는 RequestParam으로 받은 데이터를 DTO로 변환하여 Service에게 전달
        - [ ] Service에서는 DTO를 Product로 변환해서 DAO에 전달
        - [ ] DAO는 데이터베이스에 Product INSERT
- [ ] 상품 목록 조회
    - '/api/product-list' 경로로 get 요청이 들어올 경우, ProductListDto를 반환한다.
- [ ] 상품 수정
    - '/api/product/{name}' 경로로 put 요청이 들어올 경우, 변경된 ProductDto를 반환한다.
        - [ ] Controller에서는 PathVariable로 name과 RequestBody로 변경될 데이터를 받아 Service에게 전달
        - [ ] Service에서는 name으로 DAO.findByName을 호출해 Product를 찾는다.
        - [ ] Service에서는 찾은 Product와 변경된 값으로 새로운 Product 생성후 insert
        - [ ] DAO는 데이터베이스에 새로운 Product INSERT
        - [ ] Controller는 Service에서 받은 새로운 ProductDTO 반환한다.
- [ ] 상품 삭제
    - '/api/product/{name}' 경로로 delete 요청이 들어올 경우, HttpStatus.NO_CONTENT 반환한다.
        - [ ] Controller에서는 PathVariable로 name을 서비스에 전달
        - [ ] Service에서는 name으로 DAO.findIdByName를 호출해 Product의 id를 찾는다.
        - [ ] Service에서는 id로 DAO.deleteByID로 DB에서 Product를 삭제한다.
          록

## 관리자 도구 페이지 연동
