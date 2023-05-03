CREATE TABLE IF NOT EXISTS product
(
    product_id BIGINT UNSIGNED AUTO_INCREMENT,
    name       VARCHAR(20) NOT NULL,
    price      INT         NOT NULL,
    category   VARCHAR(20) DEFAULT 'ETC',
    image_url  TEXT,

    PRIMARY KEY (product_id)
);

CREATE TABLE IF NOT EXISTS user
(
    user_id BIGINT UNSIGNED AUTO_INCREMENT,
    email VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(20) NOT NULL,

    PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS cart
(
    cart_id BIGINT UNSIGNED AUTO_INCREMENT,
    product_id BIGINT UNSIGNED NOT NULL,
    user_id BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (cart_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON UPDATE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON UPDATE CASCADE
);
