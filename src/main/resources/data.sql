CREATE TABLE product
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    name      VARCHAR(100) NOT NULL,
    image_url TEXT         NOT NULL,
    price     INT          NOT NULL,
    PRIMARY KEY (id)
);
