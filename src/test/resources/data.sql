-- product 더미 데이터
INSERT INTO product(name, image_url, price)
values ('연필', '이미지', 1000), ('지우개', '이미지', 2000), ('볼펜', '이미지', 3000);

-- member 더미 데이터
INSERT INTO member(email, password)
values ('ako@wooteco.com', 'ako'), ('oz@wooteco.com', 'oz');

-- cart 더미 데이터
INSERT INTO cart(member_id, product_id, count)
values (1,1,2), (1,2,1);

