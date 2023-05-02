CREATE TABLE member
(
    id       BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(50) NOT NULL,
    password VARCHAR(30) NOT NULL
);

CREATE TABLE product
(
    id        BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`    VARCHAR(255) NOT NULL,
    image_url TEXT         NOT NULL,
    price     INT          NOT NULL
);

CREATE TABLE cart_product
(
    id         BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id  BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member (id),
    FOREIGN KEY (product_id) REFERENCES product (id),
    UNIQUE (member_id, product_id)
);
