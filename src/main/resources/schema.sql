create table products
(
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    product_name  VARCHAR(100) NOT NULL,
    product_price INT          NOT NULL,
    product_image VARCHAR(max) NOT NULL,
    PRIMARY KEY (id)
);

create table users
(
    email    VARCHAR(20) NOT NULL,
    password VARCHAR(10) NOT NULL,
    PRIMARY KEY (email)
);

create table cart_added_product
(
    id         BIGINT      NOT NULL AUTO_INCREMENT,
    user_email VARCHAR(20) NOT NULL,
    product_id BIGINT      NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_email) REFERENCES users (email) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE ON UPDATE CASCADE
);
