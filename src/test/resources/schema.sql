drop table if EXISTS product;
drop table if EXISTS member;

CREATE TABLE product
(
    id        BIGINT       NOT NULL AUTO_INCREMENT primary key,
    name      varchar(255) NOT NULL,
    price     int          NOT NULL,
    image_url varchar(max) NOT NULL
);

CREATE TABLE member
(
    id       BIGINT       NOT NULL AUTO_INCREMENT primary key,
    email    varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
);
