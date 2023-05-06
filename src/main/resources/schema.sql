CREATE TABLE IF NOT EXISTS ITEMS
(
    id          BIGINT    AUTO_INCREMENT    NOT NULL,
    name        VARCHAR(255)                NOT NULL,
    image_url   CLOB                        NOT NULL,
    price       INT                         NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS CART_ITEM
(
    id      BIGINT  AUTO_INCREMENT  NOT NULL,
    cart_id BIGINT                  NOT NULL,
    item_id BIGINT                  NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS CARTS
(
    id          BIGINT          AUTO_INCREMENT    NOT NULL,
    user_id     BIGINT                            NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS USERS
(
    id          BIGINT          AUTO_INCREMENT    NOT NULL,
    email       VARCHAR(255)                      NOT NULL,
    password    VARCHAR(255)                      NOT NULL,

    PRIMARY KEY (id)
);
