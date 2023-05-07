CREATE TABLE IF NOT EXISTS products
(
    id        BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name      VARCHAR(30)     NOT NULL,
    image_url VARCHAR(1000),
    price     INT             NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS members
(
    id       BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    email    VARCHAR(255)    NOT NULL,
    password VARCHAR(30)     NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_email (email)
);

CREATE TABLE IF NOT EXISTS cart_items
(
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    member_id  BIGINT UNSIGNED NOT NULL,
    product_id BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (member_id) REFERENCES members (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
    UNIQUE KEY uk_member_id_product_id (member_id, product_id)
);
