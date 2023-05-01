DROP TABLE IF EXISTS PRODUCT;

CREATE TABLE PRODUCT
(
    id        INT           NOT NULL AUTO_INCREMENT,
    name      VARCHAR(20)   NOT NULL,
    price     INT           NOT NULL,
    image_url VARCHAR(2083) NOT NULL,
    PRIMARY KEY (id)
);
