CREATE TABLE IF NOT EXISTS product (
    `id` BIGINT UNIQUE NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(25) NOT NULL,
    `image_url` VARCHAR(500),
    `price` INT NOT NULL,
    `category` VARCHAR(10) NOT NULL,
    PRIMARY KEY(`id`)
);

CREATE TABLE IF NOT EXISTS users (
    `id` BIGINT UNIQUE NOT NULL AUTO_INCREMENT,
    `nickname` VARCHAR(30) NOT NULL,
    `email` VARCHAR(50) NOT NULL,
    `password` VARCHAR(70) NOT NULL,
    `telephone` VARCHAR(13) NOT NULL,
    PRIMARY KEY(`id`)
);
