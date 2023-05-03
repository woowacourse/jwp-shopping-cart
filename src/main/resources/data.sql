CREATE TABLE IF NOT EXISTS product (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    img_url VARCHAR(255),
    price INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS customer (
    id BIGINT NOT NULL AUTO_INCREMENT,
    email VARCHAR(25) NOT NULL,
    password VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS cart (
    id BIGINT NOT NULL AUTO_INCREMENT,
    product_id BIGINT,
    customer_id BIGINT,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE
);

INSERT INTO customer (email, password) VALUES ('admin@gmail.com', 'adminpwd');