CREATE TABLE IF NOT EXISTS products
(
    id        BIGINT        NOT NULL AUTO_INCREMENT,
    name      VARCHAR(30)   NOT NULL,
    image_url VARCHAR(1000) NOT NULL,
    price     INT           NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS members
(
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    email        VARCHAR(100) NOT NULL UNIQUE,
    password     VARCHAR(40)  NOT NULL,
    phone_number VARCHAR(20)  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS carts
(
    member_id  BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (member_id, product_id),
    FOREIGN KEY (member_id) REFERENCES members (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);
