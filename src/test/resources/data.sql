DROP TABLE IF EXISTS cart_item;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS member;

CREATE TABLE product
(
    id      BIGINT AUTO_INCREMENT,
    name    VARCHAR(45) NOT NULL,
    price   INT         NOT NULL,
    img_url CLOB(10 K)  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE member
(
    id       BIGINT AUTO_INCREMENT,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE cart
(
    id        BIGINT AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES member (id)
);

CREATE TABLE cart_item
(
    id         BIGINT AUTO_INCREMENT,
    cart_id    BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (cart_id) REFERENCES cart (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);
