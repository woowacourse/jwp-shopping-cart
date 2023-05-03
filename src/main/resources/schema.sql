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
