CREATE TABLE PRODUCT (
    id      INT             NOT NULL AUTO_INCREMENT,
    name    VARCHAR(10)     NOT NULL,
    url     VARCHAR(255)    NOT NULL,
    price   INT             NOT NULL,
    PRIMARY KEY (id)
);