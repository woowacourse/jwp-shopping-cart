CREATE TABLE if not exists PRODUCT (
    id          INT           NOT NULL AUTO_INCREMENT,
    `name`      VARCHAR(50)   NOT NULL,
    image       VARCHAR(255)  NOT NULL,
    price       INT           NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE if not exists MEMBER (
    id          INT           NOT NULL AUTO_INCREMENT,
    email       VARCHAR(255)  NOT NULL UNIQUE,
    password    VARCHAR(255)  NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE if not exists CART (
    id          INT           NOT NULL AUTO_INCREMENT,
    product_id  INT           NOT NULL,
    member_id   INT           NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES PRODUCT (id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES MEMBER (id) ON DELETE CASCADE
    );