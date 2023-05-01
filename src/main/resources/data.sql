DROP TABLE IF EXISTS PRODUCT_CART;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS member;

CREATE TABLE PRODUCT
(
    id      BIGINT      NOT NULL AUTO_INCREMENT,
    name    VARCHAR(20) NOT NULL,
    img_url VARCHAR(255),
    price   INT         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE PRODUCT_CART
(
    id         BIGINT NOT NULL AUTO_INCREMENT,
    member_id  INT    NOT NULL,
    product_id INT    NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE MEMBER
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    email    varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE PRODUCT_CART
    ADD FOREIGN KEY (member_id) REFERENCES member (id);

ALTER TABLE PRODUCT_CART
    ADD FOREIGN KEY (product_id) REFERENCES product (id);
