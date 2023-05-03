CREATE TABLE member
(
    member_id BIGINT      NOT NULL AUTO_INCREMENT,
    email     VARCHAR(50) NOT NULL,
    password  VARCHAR(50) NOT NULL,

    PRIMARY KEY (member_id)
);
INSERT INTO member(email, password)
VALUES ('a@a.com', 'password1');
INSERT INTO member(email, password)
VALUES ('b@b.com', 'password2');

CREATE TABLE cart
(
    cart_id BIGINT NOT NULL AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,

    PRIMARY KEY (cart_id)
);

CREATE TABLE product
(
    product_id BIGINT       NOT NULL AUTO_INCREMENT,
    name       VARCHAR(50)  NOT NULL,
    price      INT          NOT NULL,
    image_url  VARCHAR(512) NOT NULL,

    PRIMARY KEY (product_id)
);
INSERT INTO product(name, price, image_url)
VALUES ('피자', 10000,
        'https://upload.wikimedia.org/wikipedia/commons/thumb/9/91/Pizza-3007395.jpg/2560px-Pizza-3007395.jpg');
INSERT INTO product(name, price, image_url)
VALUES ('치킨', 30000, 'https://barunchicken.com/wp-content/uploads/2022/07/%EA%B3%A8%EB%93%9C%EC%B9%98%ED%82%A8-2.jpg');
