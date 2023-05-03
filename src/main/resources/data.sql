CREATE TABLE IF NOT EXISTS PRODUCT (
    id          BIGINT UNSIGNED     NOT NULL AUTO_INCREMENT,
    name        VARCHAR(50)         NOT NULL,
    url         VARCHAR(50)         NOT NULL,
    price       INT UNSIGNED        NOT NULL,
    PRIMARY KEY (id)
);
