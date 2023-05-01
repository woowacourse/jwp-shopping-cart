CREATE TABLE product
(
    id    BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(50) NOT NULL,
    price INT         NOT NULL,
    image_url TEXT        NOT NULL
);
