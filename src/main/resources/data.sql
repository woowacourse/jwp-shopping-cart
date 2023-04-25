CREATE TABLE product
(
    id    BIGINT       NOT NULL AUTO_INCREMENT,
    name  VARCHAR(255) NOT NULL,
    image VARCHAR(255) NOT NULL,
    price INT          NOT NULL,
    PRIMARY KEY (id)
);