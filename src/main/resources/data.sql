CREATE TABLE product
(
    id        bigint       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name      varchar(255) NOT NULL,
    image_url text         NOT NULL,
    price     int          NOT NULL
);
