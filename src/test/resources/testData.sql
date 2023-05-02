DROP TABLE IF EXISTS cart_item;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS member;

CREATE TABLE product
(
    id bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    image text NOT NULL,
    price int NOT NULL
);

INSERT INTO product(name, image, price) VALUES ('mouse', 'https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg', 100000);
INSERT INTO product(name, image, price) VALUES ('keyboard', 'https://i1.wp.com/blog.peoplefund.co.kr/wp-content/uploads/2020/01/진혁.jpg?fit=770%2C418&ssl=1', 250000);

CREATE TABLE member
(
    id bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL
);

CREATE TABLE cart_item
(
    id bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    member_id bigint NOT NULL,
    product_id bigint NOT NULL
);

ALTER TABLE cart_item ADD CONSTRAINT fk_member_id FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE CASCADE;
ALTER TABLE cart_item ADD CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE;
