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
    member_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    CONSTRAINT member_fk FOREIGN KEY (member_id) REFERENCES member (id),
    CONSTRAINT item_fk FOREIGN KEY (item_id) REFERENCES item (id)
);
--
-- INSERT INTO member values (1, 'kong@gmail.com', 'qlalfqjsgh');
-- INSERT INTO member values (2, 'kong@khu.ac.kr', 'qlalf!!');
-- INSERT INTO ITEM values (1, '치킨', 'https://i.namu.wiki/i/pTVoWDp5G09PGTRUTbCy8raXo9CB47uF2wcuzdUYTlPwRjU6zjl0Reoih4MIXXRTnfxVl-yKlPjTQSVhAbfSxA.webp', 20000);
