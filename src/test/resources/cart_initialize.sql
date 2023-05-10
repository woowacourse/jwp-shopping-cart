DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS customer;

CREATE TABLE IF NOT EXISTS product (
    product_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    img_url VARCHAR(255),
    price INT NOT NULL,
    PRIMARY KEY (product_id)
    );

CREATE TABLE IF NOT EXISTS customer (
    customer_id BIGINT NOT NULL AUTO_INCREMENT,
    email VARCHAR(25) NOT NULL,
    password VARCHAR(20) NOT NULL,
    PRIMARY KEY (customer_id)
    );

CREATE TABLE IF NOT EXISTS cart (
    cart_id BIGINT NOT NULL AUTO_INCREMENT,
    product_id BIGINT,
    customer_id BIGINT,
    PRIMARY KEY(cart_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE CASCADE
    );

INSERT INTO product (name, img_url, price) VALUES ('baron', 'tempUrl', 2000);
INSERT INTO customer (email, password) VALUES ('baron@gmail.com', 'password');