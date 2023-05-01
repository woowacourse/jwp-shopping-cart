drop table if EXISTS PRODUCT;
drop table if EXISTS ACCOUNT;

CREATE TABLE PRODUCT
(
    id        BIGINT        NOT NULL AUTO_INCREMENT,
    name      varchar(255)  NOT NULL,
    price     int           NOT NULL,
    image_url varchar(1000) NOT NULL,

    primary key (id)
);

CREATE TABLE ACCOUNT
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    email    varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    cart_id  BIGINT       NOT NULL,
    primary key (id)
);

CREATE TABLE CART
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    primary key (id)
);

CREATE TABLE CART_IN_PRODUCT
(
    id         BIGINT NOT NULL AUTO_INCREMENT,
    cart_id    BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    primary key (id)
);
