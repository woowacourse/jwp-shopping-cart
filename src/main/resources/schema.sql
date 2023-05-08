CREATE TABLE IF NOT EXISTS product
(
    product_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name       VARCHAR(50)  NOT NULL,
    price      INT          NOT NULL,
    image_url  VARCHAR(2048) NOT NULL,

    PRIMARY KEY (product_id),
    UNIQUE(name)
);

CREATE TABLE IF NOT EXISTS user_info
(
    user_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,

    PRIMARY KEY (user_id),
    UNIQUE(email)
);

CREATE TABLE IF NOT EXISTS cart
(
    cart_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id BIGINT UNSIGNED NOT NULL,
    product_id BIGINT UNSIGNED NOT NULL,

    PRIMARY KEY (cart_id),
    FOREIGN KEY (user_id) REFERENCES user_info(user_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id)
)
