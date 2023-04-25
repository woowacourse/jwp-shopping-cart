CREATE TABLE product
(
    product_id BIGINT UNSIGNED AUTO_INCREMENT,
    name       VARCHAR(20) NOT NULL,
    price      INT         NOT NULL,
    category   VARCHAR(20) DEFAULT 'ETC',
    image_url  TEXT,

    PRIMARY KEY (product_id)
);
