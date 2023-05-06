DROP TABLE IF EXISTS product, member, cart;

CREATE TABLE product
(
    id        BIGINT         NOT NULL AUTO_INCREMENT,
    name      VARCHAR(50)    NOT NULL,
    price     INT            NOT NULL,
    image_url VARCHAR(255)   NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE member
(
    id        BIGINT         NOT NULL AUTO_INCREMENT,
    email      VARCHAR(100)  NOT NULL UNIQUE,
    password VARCHAR(100)    NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE cart
(
    id            BIGINT     NOT NULL AUTO_INCREMENT,
    product_id    BIGINT     NOT NULL,
    member_id     BIGINT     NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO member (id, email, password) VALUES (1, 'test@email.com', '122345678');
INSERT INTO product (id, name, price, image_url) VALUES (1, '오감자', 1000, 'https://contents.lotteon.com/itemimage/_v031708/LM/88/01/11/77/52/80/4_/00/1/LM8801117752804_001_1.jpg/dims/optimize/dims/resizemc/400x400');
INSERT INTO cart (id, product_id, member_id) VALUES (1, 1, 1);
