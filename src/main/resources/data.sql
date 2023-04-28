CREATE TABLE if not exists PRODUCT
(
    id        BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    name      VARCHAR(100)       NOT NULL,
    price     INT                NOT NULL,
    image_url VARCHAR(255)       NOT NULL,
    PRIMARY KEY (id)
);
