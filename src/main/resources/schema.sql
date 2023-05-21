CREATE TABLE IF NOT EXISTS product
(
    product_id BIGINT      NOT NULL AUTO_INCREMENT,
    name       VARCHAR(50) NOT NULL,
    image_url  VARCHAR(255),
    price      INT         NOT NULL,
    PRIMARY KEY (product_id)
);

CREATE TABLE IF NOT EXISTS member
(
    member_id BIGINT       NOT NULL AUTO_INCREMENT,
    name      VARCHAR(50)  NOT NULL,
    email     VARCHAR(255) NOT NULL,
    password  VARCHAR(50)  NOT NULL,
    PRIMARY KEY (member_id)
);

CREATE TABLE IF NOT EXISTS cart
(
    cart_id    BIGINT NOT NULL AUTO_INCREMENT,
    member_id  BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (cart_id)
);