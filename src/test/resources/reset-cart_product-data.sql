DELETE FROM cart_product;
DELETE FROM product;
DELETE FROM member;

ALTER TABLE cart_product ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE product ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE member ALTER COLUMN ID RESTART WITH 1;

INSERT INTO product(name, image_url, price)
VALUES ('mouse', 'https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg', 100000),
       ('keyboard', 'https://i1.wp.com/blog.peoplefund.co.kr/wp-content/uploads/2020/01/진혁.jpg?fit=770%2C418&ssl=1', 250000);

INSERT INTO member(email, password)
VALUES ('test@test.com', 'test'),
       ('woowacourse@woowa.com', 'pobi');

INSERT INTO cart_product(member_id, product_id)
VALUES (1, 2),
       (2, 1),
       (2, 2);
