# jwp-shopping-cart

## 회원
- [x] Basic Auth 사용
- [x] 사용자 정보 email, password
- [ ] 회원 추가
- [x] 회원 단건 조회
  - 회원의 이메일과 패스워드를 통해서 식별
- [x] 회원 목록 조회

## 장바구니
- [x] 장바구니 상품 추가
- [ ] 장바구니 상품 제거
- [x] 장바구니 상품 목록 조회
- [x] 장바구니 생성
  - account 와 생성될 때 같이 생성(account와 생명주기가 같음)

## API 문서

|             URL              |Method|      설명       | 기능 구현 여부 |
|:----------------------------:|:---:|:-------------:|:--------:|
|          /accounts           |GET|   회원 목록 조회    |    O     |
| /carts/products/{product-id} |POST|  장바구니 상품 추가   |    O     |
| /carts/products/{product-id} |DELETE|  장바구니 상품 제거   |
|            /carts            |GET| 장바구니 상품 목록 조회 |    O     |
|          /settings           |GET| 모든 사용자 정보 조회  |    O     |
|    /settings/{setting-id}    |GET|   해당 사용자 정보   |
