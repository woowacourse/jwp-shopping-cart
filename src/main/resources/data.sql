CREATE TABLE product(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(20) NOT NULL,
    `image_url` VARCHAR(255) NOT NULL,
    `price` INT NOT NULL,
    PRIMARY KEY(id)
);
