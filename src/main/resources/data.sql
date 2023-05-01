CREATE TABLE IF NOT EXISTS product
(
    `id`        BIGINT UNIQUE NOT NULL AUTO_INCREMENT,
    `name`      VARCHAR(25)   NOT NULL,
    `image_url` VARCHAR(500),
    `price`     INT           NOT NULL,
    `category`  VARCHAR(10)   NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS _user
(
    `id`       BIGINT UNIQUE NOT NULL AUTO_INCREMENT,
    `email`    VARCHAR(30)   NOT NULL,
    `password` VARCHAR(500),
    PRIMARY KEY (`id`)
);

INSERT INTO _user(`email`, `password`) VALUES('a@a.com', 'password1');
INSERT INTO _user(`email`, `password`) VALUES('b@b.com', 'password2');