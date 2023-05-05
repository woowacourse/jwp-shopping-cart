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
    email    varchar(255) NOT NULL UNIQUE,
    password varchar(255) NOT NULL
);

CREATE TABLE cart
(
    id         BIGINT NOT NULL AUTO_INCREMENT primary key,
    member_id  BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);


