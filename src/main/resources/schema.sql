CREATE TABLE IF NOT EXISTS ITEMS
(
    item_id     BIGINT    AUTO_INCREMENT    NOT NULL,
    name        VARCHAR(255)                NOT NULL,
    image_url   CLOB                        NOT NULL,
    price       INT                         NOT NULL,

    PRIMARY KEY (item_id)
);

CREATE TABLE IF NOT EXISTS USERS
(
    user_id     BIGINT          AUTO_INCREMENT    NOT NULL,
    email       VARCHAR(255)                      NOT NULL,
    password    VARCHAR(255)                      NOT NULL,

    PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS CARTS
(
    cart_id     BIGINT          AUTO_INCREMENT    NOT NULL,
    user_email  VARCHAR(255)                      NOT NULL,
    item_id     VARCHAR(255)                      NOT NULL,

    PRIMARY KEY (cart_id)
);
