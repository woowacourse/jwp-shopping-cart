CREATE TABLE IF NOT EXISTS product
(
    id bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    image text NOT NULL,
    price int NOT NULL
);
