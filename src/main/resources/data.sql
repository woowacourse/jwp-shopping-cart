CREATE TABLE IF NOT EXISTS PRODUCT
(
    id          INT        UNSIGNED NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255)        NOT NULL,
    image_url   VARCHAR(255)        NOT NULL,
    price       INT                 NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS MEMBER
(
    id          INT        UNSIGNED NOT NULL AUTO_INCREMENT,
    email       VARCHAR(255)        NOT NULL,
    password    VARCHAR(255)        NOT NULL,
    PRIMARY KEY (id)
);
