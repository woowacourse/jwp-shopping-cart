DROP TABLE IF EXISTS PRODUCT;

CREATE TABLE PRODUCT
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    name      VARCHAR(50)  NOT NULL,
    price     INT          NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);


