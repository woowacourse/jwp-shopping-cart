DROP TABLE product IF EXISTS;
DROP TABLE member IF EXISTS;
DROP TABLE cart IF EXISTS;

CREATE TABLE product(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(20) NOT NULL,
    `price` INT NOT NULL,
    `image_url` TEXT NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE member(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(32) NOT NULL,
    `password` VARCHAR(32) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE cart(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `member_id` BIGINT NOT NULL,
    `product_id` BIGINT NOT NULL,
    FOREIGN KEY(member_id) REFERENCES `member`(id),
    FOREIGN KEY(product_id) REFERENCES `product`(id),
    PRIMARY KEY(id)
);