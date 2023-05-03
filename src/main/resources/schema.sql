CREATE TABLE IF NOT EXISTS product
(
    product_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name       VARCHAR(50)  NOT NULL,
    price      INT          NOT NULL,
    image_url  VARCHAR(2048) NOT NULL,

    PRIMARY KEY (product_id)
);

CREATE TABLE IF NOT EXISTS user_info
(
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,

    PRIMARY KEY (email)
);
