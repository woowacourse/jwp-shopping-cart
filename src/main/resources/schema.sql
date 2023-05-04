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
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (email)
);

CREATE TABLE IF NOT EXISTS CART
(
    cart_id    BIGINT       NOT NULL AUTO_INCREMENT,
    email      VARCHAR(255) NOT NULL,
    product_id BIGINT       NOT NULL,
    PRIMARY KEY (cart_id),
    FOREIGN KEY (email) REFERENCES MEMBER (email) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES PRODUCT (product_id) ON DELETE CASCADE
);
