CREATE TABLE IF NOT EXISTS `product`
(
    `id`         BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name`       VARCHAR(20) NOT NULL,
    `price`      INTEGER     NOT NULL,
    `img_url`    TEXT        NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP
);
