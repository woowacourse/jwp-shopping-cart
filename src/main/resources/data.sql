CREATE TABLE IF NOT EXISTS `product`
(
    `id`    long PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name`  varchar(255)     NOT NULL,
    `image` varchar(1024)    NOT NULL,
    `price` long             NOT NULL
);

CREATE TABLE IF NOT EXISTS `member`
(
    `id`       long PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `email`    varchar(255)     NOT NULL UNIQUE,
    `password` varchar(255)     NOT NULL
);

INSERT INTO member (email, password)
VALUES ('herb@teco.com', 'herbPassword');
INSERT INTO member (email, password)
VALUES ('blackcat@teco.com', 'blackcatPassword');
