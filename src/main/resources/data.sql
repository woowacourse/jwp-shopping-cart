DROP TABLE IF EXISTS PRODUCT;
DROP TABLE IF EXISTS MEMBER;

CREATE TABLE PRODUCT
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    name      VARCHAR(10)  NOT NULL,
    price     INT          NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE MEMBER
(
    id       BIGINT      NOT NULL AUTO_INCREMENT,
    email    VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

