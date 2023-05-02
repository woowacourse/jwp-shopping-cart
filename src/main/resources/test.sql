DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS _user;

CREATE TABLE IF NOT EXISTS product
(
    `id`        BIGINT UNIQUE NOT NULL AUTO_INCREMENT,
    `name`      VARCHAR(25)   NOT NULL,
    `image_url` VARCHAR(500),
    `price`     INT           NOT NULL,
    `category`  VARCHAR(10)   NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS _user
(
    `id`       BIGINT UNIQUE NOT NULL AUTO_INCREMENT,
    `email`    VARCHAR(30)   NOT NULL,
    `password` VARCHAR(500),
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS cart
(
    `id`         BIGINT UNIQUE NOT NULL AUTO_INCREMENT,
    `product_id` BIGINT        NOT NULL,
    `user_id`    BIGINT        NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`product_id`) REFERENCES product (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES _user (`id`) ON DELETE CASCADE
);