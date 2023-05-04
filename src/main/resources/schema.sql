CREATE TABLE IF NOT EXISTS item
(
    id       bigint PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name     varchar(30)                       NOT NULL,
    item_url text                              NOT NULL,
    price    int                               NOT NULL
);

CREATE TABLE IF NOT EXISTS member
(
    email        varchar(30) PRIMARY KEY NOT NULL,
    name         varchar(30)             NOT NULL,
    phone_number varchar(30)             NOT NULL,
    password     varchar(30)             NOT NULL,
    cart         text
);
