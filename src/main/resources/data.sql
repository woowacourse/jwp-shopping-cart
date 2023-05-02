CREATE TABLE IF NOT EXISTS USERS
(
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    email       VARCHAR(100)    NOT NULL UNIQUE,
    password    VARCHAR(100)    NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ITEMS
(
    id          BIGINT        NOT NULL AUTO_INCREMENT,
    name        VARCHAR(50)   NOT NULL,
    image_url   VARCHAR(5000),
    price       INT           NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS CARTS
(
    user_id     BIGINT          NOT NULL,
    cart_id     BIGINT          NOT NULL,
    PRIMARY KEY (user_id, cart_id),
    FOREIGN KEY (user_id) REFERENCES USERS(id),
    FOREIGN KEY (cart_id) REFERENCES ITEMS(id)
);

INSERT INTO users (email, password) values ('email1@email.com', '12345678');
INSERT INTO users (email, password) values ('email2@email.com', '12345678');

INSERT INTO items (name, image_url, price) values ('위키드', 'https://image.yes24.com/themusical/upFiles/Themusical/Play/post_2013wicked.jpg', 150000);
INSERT INTO items (name, image_url, price) values ('마틸다', 'https://ticketimage.interpark.com/Play/image/large/22/22009226_p.gif', 100000);
INSERT INTO items (name, image_url, price) values ('빌리 엘리어트', 'https://t1.daumcdn.net/cfile/226F4D4C544F42CF34', 200000);

INSERT INTO carts (user_id, cart_id) values (1, 1);
INSERT INTO carts (user_id, cart_id) values (1, 2);
INSERT INTO carts (user_id, cart_id) values (2, 2);
INSERT INTO carts (user_id, cart_id) values (2, 3);
