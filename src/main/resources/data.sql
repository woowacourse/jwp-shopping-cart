DROP TABLE products if exists;

CREATE TABLE products (
    id          BIGINT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(10)     NOT NULL,
    image       VARCHAR(255)    NOT NULL,
    price       INT             NOT NULL
);

INSERT INTO products (name,image,price) VALUES('치킨','https://images.unsplash.com/photo-1626074353765-517a681e40be?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=987&q=80',15000);
INSERT INTO products (name,image,price) VALUES('샐러드','https://images.unsplash.com/photo-1546793665-c74683f339c1?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=987&q=80',9000);
INSERT INTO products (name,image,price) VALUES('피자','https://images.unsplash.com/photo-1593560708920-61dd98c46a4e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1035&q=80',23000);

DROP TABLE users if exists;

CREATE TABLE users (
    email       VARCHAR(255)    NOT NULL PRIMARY KEY,
    password    VARCHAR(255)    NOT NULL
);

INSERT INTO users (email,password) VALUES('echo@wtc.com','#abcd1234');
INSERT INTO users (email,password) VALUES('hello@test.com','#abcd1234');

CREATE TABLE cart_items (
    id          BIGINT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    product_id   BIGINT          NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE TABLE carts (
    id         BIGINT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_email VARCHAR(255)    NOT NULL,
    cart_id   BIGINT          NOT NULL,
    FOREIGN KEY (user_email) REFERENCES users (email),
    FOREIGN KEY (cart_id) REFERENCES cart_items (id)
);