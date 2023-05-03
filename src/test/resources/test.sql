DROP TABLE product IF EXISTS;
DROP TABLE member IF EXISTS;
DROP TABLE cart IF EXISTS;

CREATE TABLE product
(
    id        BIGINT      NOT NULL AUTO_INCREMENT,
    name      VARCHAR(50) NOT NULL,
    price     BIGINT      NOT NULL,
    image_url TEXT        NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE member
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    email    VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE cart
(
    id         BIGINT NOT NULL AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    member_id  BIGINT NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO member (email, password)
VALUES ('user1@woowa.com', '123456'),
       ('user2@woowa.com', '1234');

INSERT INTO product (name, price, image_url)
VALUES ('product1', 1000, 'url.com'), ('product2', 100, 'url2.com');

INSERT INTO cart (product_id, member_id)
VALUES (1, 1), (2, 1);
