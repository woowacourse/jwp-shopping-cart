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
    password     varchar(30)             NOT NULL
);

CREATE TABLE IF NOT EXISTS cart
(
    email varchar(30) NOT NULL,
    id    bigint      NOT NULL,
    PRIMARY KEY (email, id),
    FOREIGN KEY (id) REFERENCES item (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (email) REFERENCES member (email) ON DELETE CASCADE ON UPDATE CASCADE
);
