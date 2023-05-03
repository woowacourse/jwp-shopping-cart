CREATE TABLE IF NOT EXISTS PRODUCT (
    id          BIGINT UNSIGNED     NOT NULL AUTO_INCREMENT,
    name        VARCHAR(50)         NOT NULL,
    url         VARCHAR(50)         NOT NULL,
    price       INT UNSIGNED        NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS MEMBER(
    id          BIGINT UNSIGNED     NOT NULL AUTO_INCREMENT,
    email        VARCHAR(50)         NOT NULL,
    password       VARCHAR(50)         NOT NULL,
    PRIMARY KEY (id)
);
