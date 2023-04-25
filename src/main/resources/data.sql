CREATE TABLE IF NOT EXISTS product (
    `id` BIGINT UNIQUE NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(25) NOT NULL,
    `image_url` VARCHAR(500),
    `price` INT NOT NULL,
    `category` VARCHAR(10) NOT NULL,
    PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
