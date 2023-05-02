CREATE TABLE IF NOT EXISTS product (
    `id` BIGINT UNIQUE NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(25) NOT NULL,
    `image_url` VARCHAR(500),
    `price` INT NOT NULL,
    `category` VARCHAR(10) NOT NULL,
    PRIMARY KEY(`id`)
);

CREATE TABLE IF NOT EXISTS member (
    `id` BIGINT UNIQUE NOT NULL AUTO_INCREMENT,
    `role` VARCHAR(10) NOT NULL,
    `nickname` VARCHAR(30) NOT NULL,
    `email` VARCHAR(50) UNIQUE NOT NULL,
    `password` VARCHAR(70) NOT NULL,
    `telephone` VARCHAR(13) NOT NULL,
    PRIMARY KEY(`id`)
);

CREATE TABLE IF NOT EXISTS cart (
    `id` BIGINT UNIQUE NOT NULL AUTO_INCREMENT,
    `member_id` BIGINT NOT NULL,
    `product_id` BIGINT NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`member_id`) REFERENCES member(`id`),
    FOREIGN KEY(`product_id`) REFERENCES product(`id`)
);
