CREATE TABLE member
(
    id       BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(50) NOT NULL,
    password VARCHAR(30) NOT NULL,
    UNIQUE (email)
);

CREATE TABLE product
(
    id        BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`    VARCHAR(255) NOT NULL,
    image_url TEXT         NOT NULL,
    price     INT          NOT NULL
);

CREATE TABLE cart
(
    id        BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member (id)
);

CREATE TABLE cart_product
(
    id         BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    cart_id    BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    FOREIGN KEY (cart_id) REFERENCES cart (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE,
    UNIQUE (cart_id, product_id)
);
