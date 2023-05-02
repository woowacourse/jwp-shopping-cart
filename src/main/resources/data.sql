CREATE TABLE IF NOT EXISTS products
(
    id    BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(21) NOT NULL,
    image TEXT        NOT NULL,
    price INT         NOT NULL
);

CREATE TABLE IF NOT EXISTS members
(
    id       BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(21) NOT NULL,
    password VARCHAR(21) NOT NULL
);

CREATE TABLE IF NOT EXISTS carts
(
    product_id BIGINT NOT NULL,
    member_id  BIGINT NOT NULL,
    primary key (product_id, member_id),
    FOREIGN KEY (product_id) REFERENCES products (id),
    FOREIGN KEY (member_id) REFERENCES members (id)
);

