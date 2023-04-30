DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS members;

CREATE TABLE IF NOT EXISTS products
(
    id        BIGINT        NOT NULL AUTO_INCREMENT,
    name      VARCHAR(30)   NOT NULL,
    image_url VARCHAR(1000) NOT NULL,
    price     INT           NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS members
(
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    email        VARCHAR(100) NOT NULL UNIQUE,
    password     VARCHAR(40)  NOT NULL,
    phone_number VARCHAR(20)  NOT NULL,
    PRIMARY KEY (id)
);
