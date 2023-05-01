DROP TABLE IF EXISTS product;

DROP TABLE IF EXISTS member;

CREATE TABLE product
(
    id        BIGINT      NOT NULL AUTO_INCREMENT,
    name      VARCHAR(64) NOT NULL,
    price     INT         NOT NULL,
    image_url TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE member
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name     VARCHAR(10)  NOT NULL,
    PRIMARY KEY (id)
);
