CREATE TABLE product
(
    id             INT          NOT NULL AUTO_INCREMENT,
    product_name   VARCHAR(255) NOT NULL,
    product_price  INT          NOT NULL,
    product_imgUrl VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
