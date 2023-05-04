CREATE TABLE IF NOT EXISTS product
(
    product_id BIGINT UNSIGNED AUTO_INCREMENT,
    name       VARCHAR(20) NOT NULL,
    price      INT         NOT NULL,
    category   VARCHAR(20) DEFAULT 'ETC',
    image_url  TEXT,

    PRIMARY KEY (product_id)
);

CREATE TABLE IF NOT EXISTS cart_user
(
    cart_user_id  BIGINT UNSIGNED AUTO_INCREMENT,
    email         VARCHAR(40)  NOT NULL,
    cart_password VARCHAR(100) NOT NULL,

    PRIMARY KEY (cart_user_id)
);

CREATE TABLE IF NOT EXISTS cart_user_product
(
    cart_user_product_id BIGINT UNSIGNED AUTO_INCREMENT,
    cart_user_id         BIGINT NOT NULL,
    product_id           BIGINT NOT NULL,

    PRIMARY KEY (cart_user_product_id),
    FOREIGN KEY (cart_user_id) REFERENCES cart_user (cart_user_id),
    FOREIGN KEY (product_id) REFERENCES product (product_id)
);

-- ALTER TABLE cart_user_product
--     ADD CONSTRAINT cart_user_id
--         FOREIGN KEY (cart_user_id)
--             REFERENCES cart_user (cart_user_id);
--
-- ALTER TABLE cart_user_product
--     ADD CONSTRAINT product_id
--         FOREIGN KEY (product_id)
--             REFERENCES product (product_id);
