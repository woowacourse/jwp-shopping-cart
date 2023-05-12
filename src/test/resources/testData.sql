
INSERT INTO product(name, image, price)
VALUES ('mouse', 'https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg', 100000);
INSERT INTO product(name, image, price)
VALUES ('keyboard', 'https://i1.wp.com/blog.peoplefund.co.kr/wp-content/uploads/2020/01/진혁.jpg?fit=770%2C418&ssl=1',
        250000);

INSERT INTO member(email, password) VALUES ('irene@email.com', 'password1');
INSERT INTO member(email, password) VALUES ('abcd@email.com', 'password2');


INSERT INTO cart_item(member_id, product_id) VALUES (1L, 2L);
