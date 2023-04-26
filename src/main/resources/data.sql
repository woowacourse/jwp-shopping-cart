create table product
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    name      VARCHAR(255) NOT NULL,
    image_url varchar(255) NOT NULL,
    price     INT          NOT NULL,
    primary key (id)
);
