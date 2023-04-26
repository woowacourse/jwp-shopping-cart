DROP TABLE IF EXISTS product;

CREATE TABLE PRODUCT
(
    id      BIGINT      NOT NULL AUTO_INCREMENT,
    name    VARCHAR(20) NOT NULL,
    img_url VARCHAR(255),
    price   INT         NOT NULL,
    PRIMARY KEY (id)
);
