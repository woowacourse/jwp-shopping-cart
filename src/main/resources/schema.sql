CREATE TABLE IF NOT EXISTS PRODUCT
(
    product_id BIGINT        NOT NULL AUTO_INCREMENT,
    name       VARCHAR(255)  NOT NULL,
    image      VARCHAR(2048) NOT NULL,
    price      INT           NOT NULL,
    PRIMARY KEY (product_id)
);

CREATE TABLE IF NOT EXISTS MEMBER
(
    member_id BIGINT       NOT NULL AUTO_INCREMENT,
    email     VARCHAR(255) NOT NULL,
    password  VARCHAR(255) NOT NULL,
    UNIQUE (email),
    PRIMARY KEY (member_id)
);

CREATE TABLE IF NOT EXISTS CART
(
    cart_id BIGINT NOT NULL AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    PRIMARY KEY (cart_id),
    FOREIGN KEY (member_id) REFERENCES MEMBER (member_id)
);

CREATE TABLE IF NOT EXISTS CART_PRODUCT
(
    cart_product_id BIGINT NOT NULL AUTO_INCREMENT,
    cart_id         BIGINT NOT NULL,
    product_id      BIGINT NOT NULL,
    PRIMARY KEY (cart_product_id),
    FOREIGN KEY (cart_id) REFERENCES CART (cart_id),
    FOREIGN KEY (product_id) REFERENCES PRODUCT (product_id)
);
