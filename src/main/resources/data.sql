CREATE TABLE product (
    product_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    image BLOB NOT NULL,
    price INT NOT NULL,

    PRIMARY KEY (product_id)
);
