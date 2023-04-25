CREATE TABLE product
(
    id    bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name  varchar(255)       NOT NULL,
    image text               NOT NULL,
    price int                NOT NULL
);


INSERT INTO product(name, image, price) VALUES ('mouse','https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg', 100000);
INSERT INTO product(name, image, price) VALUES ('keyboard', 'https://i1.wp.com/blog.peoplefund.co.kr/wp-content/uploads/2020/01/진혁.jpg?fit=770%2C418&ssl=1', 250000);
