create table products
(
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    product_name  VARCHAR(100) NOT NULL,
    product_price INT          NOT NULL,
    product_image VARCHAR(max) NOT NULL,
    PRIMARY KEY (id)
);

create table users
(
    email    VARCHAR(20) NOT NULL,
    password VARCHAR(10) NOT NULL,
    PRIMARY KEY (email)
);
