CREATE TABLE IF NOT EXISTS customer
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(255) NOT NULL,
    age      INTEGER      NOT NULL,
    PRIMARY KEY (id)
) engine=InnoDB default charset=utf8mb4;

CREATE TABLE IF NOT EXISTS product
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    name      VARCHAR(255) NOT NULL,
    price     INTEGER      NOT NULL,
    image_url VARCHAR(255),
    PRIMARY KEY (id)
) engine=InnoDB default charset=utf8mb4;

CREATE TABLE IF NOT EXISTS cart_item
(
    id          BIGINT  NOT NULL AUTO_INCREMENT,
    customer_id BIGINT  NOT NULL,
    product_id  BIGINT  NOT NULL,
    quantity    INTEGER NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_product_per_customer (customer_id, product_id)
) engine=InnoDB default charset=utf8mb4;

CREATE TABLE IF NOT EXISTS orders
(
    id          BIGINT NOT NULL AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    PRIMARY KEY (id)
) engine=InnoDB default charset=utf8mb4;

CREATE TABLE IF NOT EXISTS orders_detail
(
    id         BIGINT  NOT NULL AUTO_INCREMENT,
    order_id   BIGINT  NOT NULL,
    product_id BIGINT  NOT NULL,
    quantity   INTEGER NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_product_per_order (order_id, product_id)
) engine=InnoDB default charset=utf8mb4;
