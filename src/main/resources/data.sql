CREATE TABLE product
(
    id bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    image text NOT NULL,
    price int NOT NULL
);

CREATE TABLE member
(
    id bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL
);
