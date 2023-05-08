CREATE TABLE IF NOT EXISTS `product`
(
    `id`    long PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name`  varchar(255)     NOT NULL,
    `image` varchar(1024)    NOT NULL,
    `price` long             NOT NULL
);

CREATE TABLE IF NOT EXISTS `member`
(
    `id`       long PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `email`    varchar(255)     NOT NULL UNIQUE,
    `password` varchar(255)     NOT NULL
);

CREATE TABLE IF NOT EXISTS `cart_product`
(
    `id`         long PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `member_id`  long             NOT NULL,
    `product_id` long             NOT NULL,
    CONSTRAINT CART_UNIQUE UNIQUE (`member_id`, `product_id`),
    FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
    FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE
);


INSERT INTO member (email, password)
VALUES ('herb@teco.com', 'herbPassword');
INSERT INTO member (email, password)
VALUES ('blackcat@teco.com', 'blackcatPassword');
