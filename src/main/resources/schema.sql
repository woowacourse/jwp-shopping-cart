CREATE TABLE IF NOT EXISTS product
(
    product_id BIGINT      NOT NULL AUTO_INCREMENT,
    name       VARCHAR(50) NOT NULL,
    image_url  VARCHAR(255),
    price      INT         NOT NULL,
    PRIMARY KEY (product_id)
);