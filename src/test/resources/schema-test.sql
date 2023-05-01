CREATE TABLE product
(
    product_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name       VARCHAR(50)   NOT NULL,
    price      INT           NOT NULL,
    image_url  VARCHAR(2048) NOT NULL,

    PRIMARY KEY (product_id)
);
