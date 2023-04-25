CREATE TABLE IF NOT EXISTS product
(
    id    BIGINT      NOT NULL AUTO_INCREMENT,
    name  VARCHAR(64) NOT NULL,
    price INT         NOT NULL,
    image VARCHAR(256),
    PRIMARY KEY (id)
);
