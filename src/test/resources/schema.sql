drop table if EXISTS product;

CREATE TABLE product
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    name      varchar(255) NOT NULL,
    price     int          NOT NULL,
    image_url varchar(1000) NOT NULL,

    primary key (id)
);
