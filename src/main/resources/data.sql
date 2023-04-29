CREATE TABLE IF NOT EXISTS `product`
(
    `id`         long PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name`       varchar(255)     NOT NULL,
    `image`      varchar(1024)    NOT NULL,
    `price`      long             NOT NULL,
    `created_at` timestamp        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
