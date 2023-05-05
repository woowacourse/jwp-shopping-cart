DROP TABLE cart;
DROP TABLE product;
DROP TABLE user_list;

CREATE TABLE IF NOT EXISTS product
(
    id    BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(64) NOT NULL,
    price INT         NOT NULL,
    image TEXT
    );

CREATE TABLE IF NOT EXISTS user_list
(
    id       BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(32) NOT NULL,
    password VARCHAR(32) NOT NULL
    );

CREATE TABLE IF NOT EXISTS cart
(
    id         BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user_list (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE
);
