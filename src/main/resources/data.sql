INSERT INTO Product (id, name, price, image_url) VALUES (2, '치킨', 10000, 'https://pelicana.co.kr/resources/images/menu/original_menu01_200529.png');
INSERT INTO Product (id, name, price, image_url) VALUES (3, '햄버거', 10000, 'https://pelicana.co.kr/resources/images/menu/original_menu01_200529.png');
INSERT INTO Product (id, name, price, image_url) VALUES (4, '피자', 10000, 'https://pelicana.co.kr/resources/images/menu/original_menu01_200529.png');

INSERT INTO Member (email, password) VALUES ('miseongk@gmail.com', 'miseongk');
INSERT INTO Member (email, password) VALUES ('miseongk2@gmail.com', 'miseongk2');

INSERT INTO Cart (member_email, product_id) VALUES ('miseongk@gmail.com', 2);
