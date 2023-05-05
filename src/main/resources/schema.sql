DROP TABLE IF EXISTS products CASCADE;

CREATE TABLE products
(
    id        BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name      VARCHAR(30)     NOT NULL,
    image_url VARCHAR(1000),
    price     INT             NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS members CASCADE;

CREATE TABLE members
(
    id       BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    email    VARCHAR(255)    NOT NULL,
    password VARCHAR(30)     NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_email (email)
);

DROP TABLE IF EXISTS cart_items;

CREATE TABLE cart_items
(
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    member_id  BIGINT UNSIGNED NOT NULL,
    product_id BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (member_id) REFERENCES members (id),
    FOREIGN KEY (product_id) REFERENCES products (id),
    UNIQUE KEY uk_member_id_product_id (member_id, product_id)
);
