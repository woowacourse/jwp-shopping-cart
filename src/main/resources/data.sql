CREATE TABLE IF NOT EXISTS products
(
    id    BIGINT      NOT NULL AUTO_INCREMENT,
    name  VARCHAR(10) NOT NULL,
    price DOUBLE      NOT NULL,
    image TEXT        NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS members
(
    id       BIGINT      NOT NULL AUTO_INCREMENT,
    name     VARCHAR(30) NOT NULL,
    email    VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS carts
(
    id        BIGINT NOT NULL AUTO_INCREMENT,
    member_id BIGINT NOT NULL UNIQUE,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES members (id)
);

CREATE TABLE  IF NOT EXISTS cart_product
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    cart_id BIGINT NOT NULL ,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (cart_id) REFERENCES carts(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
