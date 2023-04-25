CREATE TABLE `product`
(
    `id`    long PRIMARY KEY NOT NULL  AUTO_INCREMENT,
    `name`  varchar(255)     NOT NULL,
    `image` varchar(1024)    NOT NULL,
    `price` long             NOT NULL
);
