CREATE TABLE IF NOT EXISTS item
(
    id       BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name     VARCHAR(30)        NOT NULL,
    item_url TEXT               NOT NULL,
    price    INT                NOT NULL
    );

CREATE TABLE IF NOT EXISTS member
(
    id       BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    email    VARCHAR(320)       NOT NULL,
    password VARCHAR(20)        NOT NULL
    );

CREATE TABLE IF NOT EXISTS cart
(
    id      BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL
);

INSERT INTO MEMBER values (1, 'gksqlsl11@khu.ac.kr', 'qlalfqjsgh');
INSERT INTO MEMBER values (2, 'kong@khu.ac.kr', 'qlalf!!');
