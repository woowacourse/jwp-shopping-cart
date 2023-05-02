DROP TABLE IF EXISTS product;

CREATE TABLE IF NOT EXISTS product (
     id          INT         NOT NULL AUTO_INCREMENT,
     name       VARCHAR(11)         NOT NULL,
     image_url      TEXT(500)     NOT NULL,
     price      DECIMAL(10,2)         NOT NULL,
     created_at  DATETIME    NOT NULL default current_timestamp,
     PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS member (
    member_id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY(member_id)
);

CREATE TABLE IF NOT EXISTS cart (
    cart_id INT NOT NULL AUTO_INCREMENT,
    member_id INT NOT NULL,
    PRIMARY KEY(cart_id)
);

CREATE TABLE IF NOT EXISTS cart_item (
    cart_item_id INT NOT NULL AUTO_INCREMENT,
    cart_id INT NOT NULL,
    product_id INT NOT NULL,
    PRIMARY KEY(cart_item_id)
);

INSERT INTO member(email, password) VALUES('test1', 'pass1');
INSERT INTO member(email, password) VALUES('test2', 'pass2');

INSERT INTO product(name, image_url, price) VALUES('dummy1', 'test-url-dummy1', '5000');
INSERT INTO product(name, image_url, price) VALUES('dummy2', 'test-url-dummy2', '10000');
INSERT INTO product(name, image_url, price) VALUES('dummy3', 'test-url-dummy3', '15000');
INSERT INTO product(name, image_url, price) VALUES('dummy4', 'test-url-dummy4', '20000');
INSERT INTO product(name, image_url, price) VALUES('dummy5', 'test-url-dummy5', '25000');
