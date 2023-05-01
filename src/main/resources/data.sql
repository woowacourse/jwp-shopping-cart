-- 기본 유저
INSERT INTO users (email, password) VALUES ('test@test.com', 'test');
INSERT INTO users (email, password) VALUES ('user@user.com', 'user');

-- 기본 상품
INSERT INTO product (name, price, img_url) VALUES ('치킨', 22000, 'https://img.freepik.com/free-photo/crispy-fried-chicken-on-a-plate-with-salad-and-carrot_1150-20212.jpg');
INSERT INTO product (name, price, img_url) VALUES ('샐러드', 8000, 'https://m.subway.co.kr/upload/menu/%EC%89%AC%EB%A6%BC%ED%94%84_20220413025617416.png');
INSERT INTO product (name, price, img_url) VALUES ('짜장면', 7000, 'https://t3.ftcdn.net/jpg/04/70/03/22/360_F_470032254_ljRRvV1YclnG9WqB4AptM7ACWyyLliMw.jpg');
