CREATE TABLE IF NOT EXISTS ITEM
(
    item_id     BIGINT              AUTO_INCREMENT  NOT NULL,
    name        VARCHAR(255)        NOT NULL,
    image_url   VARCHAR(255)        NOT NULL,
    price       INT                 NOT NULL,

    primary key (item_id)
);

CREATE TABLE IF NOT EXISTS MEMBER
(
    member_id   BIGINT              AUTO_INCREMENT  NOT NULL,
    email       VARCHAR(255)        UNIQUE NOT NULL,
    password    VARCHAR(255)        NOT NULL,
    name        VARCHAR(255)        NOT NULL,

    primary key (member_id)
);

CREATE TABLE IF NOT EXISTS CART
(
    cart_id       BIGINT    AUTO_INCREMENT  NOT NULL,
    member_id     BIGINT    NOT NULL,
    item_id       BIGINT    NOT NULL,

    primary key (cart_id),
    foreign key (item_id) references item (item_id),
    foreign key (member_id) references member (member_id)
);
