CREATE TABLE IF NOT EXISTS PRODUCT
(
    product_id BIGINT        NOT NULL AUTO_INCREMENT,
    name       VARCHAR(255)  NOT NULL,
    image      VARCHAR(2048) NOT NULL,
    price      INT           NOT NULL,
    PRIMARY KEY (product_id)
);

CREATE TABLE IF NOT EXISTS MEMBER
(
    user_id  BIGINT       NOT NULL AUTO_INCREMENT,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    UNIQUE (email),
    PRIMARY KEY (user_id)
);
