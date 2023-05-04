CREATE TABLE IF NOT EXISTS PRODUCT
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    image_url VARCHAR(max) NOT NULL,
    price INT NOT NUll
);

CREATE TABLE IF NOT EXISTS MEMBER
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS CART
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL
);

INSERT INTO member (email, password) VALUES ('email1@email2','password1');
INSERT INTO member (email, password) VALUES ('email2@email3','password2');
insert into product (name, image_url, price) VALUES ('피자', 'url1', 20000 );
insert into product (name, image_url, price) VALUES ('치킨', 'url2', 30000 );
