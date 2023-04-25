CREATE TABLE IF NOT EXISTS item
(
    id       bigint      PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name     varchar(30) NOT NULL,
    item_url text        NOT NULL,
    price    int         NOT NULL
);
