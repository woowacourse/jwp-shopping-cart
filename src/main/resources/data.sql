CREATE TABLE IF NOT EXISTS item
(
    id       bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name     varchar(30)        NOT NULL,
    item_url text               NOT NULL,
    price    int                NOT NULL
    );

CREATE TABLE IF NOT EXISTS member
(
    id       BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    email    VARCHAR(320)       NOT NULL,
    password VARCHAR(20)        NOT NULL
    );

CREATE TABLE IF NOT EXISTS basket
(
    id      BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL
);
--
-- INSERT INTO MEMBER values (1, 'gksqlsl11@khu.ac.kr', 'qlalfqjsgh');
