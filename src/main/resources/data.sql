CREATE TABLE IF NOT EXISTS `product`
(
    `id`         BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name`       VARCHAR(20) NOT NULL,
    `price`      INTEGER     NOT NULL,
    `img_url`    TEXT        NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO `product` (name, price, img_url)
VALUES ('치킨', 10000,
        'https://i.namu.wiki/i/NH4o6m-2L377GTn-Nte1w5TX7h7vs9XdeIKyoKh9y72CNad59DFCCXN-sZkyRllbuM1Ahy74zj5Hayzecd59P8VwkO3cIwnKMAqPXYwKD3bq55Al09UwQ8d6MftSzV-CPgUuxBmxPLx95DRQJnQEZw.webp');

CREATE TABLE IF NOT EXISTS `users`
(
    `id`       BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `email`    VARCHAR(20) NOT NULL,
    `password` VARCHAR(20) NOT NULL
);

INSERT INTO `users` (email, password)
VALUES ('ahdjd5@gmail.com', 'qwer1234');
