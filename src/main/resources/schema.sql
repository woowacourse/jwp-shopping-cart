CREATE TABLE product
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    name      VARCHAR(50)  NOT NULL,
    price     BIGINT       NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    PRIMARY KEY (id)
);
