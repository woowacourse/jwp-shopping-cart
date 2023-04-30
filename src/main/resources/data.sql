DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS member;

CREATE TABLE product
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    name      VARCHAR(50)  NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    price     INT          NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE member
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE cart_product
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    member_id    VARCHAR(255) NOT NULL,
    product_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (product_id) REFERENCES product(id),
    UNIQUE (member_id, product_id)
);
