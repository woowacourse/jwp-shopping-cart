CREATE TABLE IF NOT EXISTS ITEMS
(
    id        BIGINT      NOT NULL AUTO_INCREMENT,
    name      VARCHAR(50) NOT NULL,
    image_url TEXT,
    price     INT         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS USERS
(
    id       BIGINT      NOT NULL AUTO_INCREMENT,
    email    VARCHAR(50) NOT NULL,
    password VARCHAR(15) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO items (name, image_url, price)
values ('위키드', 'https://image.yes24.com/themusical/upFiles/Themusical/Play/post_2013wicked.jpg',
        150000);
INSERT INTO items (name, image_url, price)
values ('마틸다', 'https://ticketimage.interpark.com/Play/image/large/22/22009226_p.gif', 100000);
INSERT INTO items (name, image_url, price)
values ('빌리 엘리어트', 'https://t1.daumcdn.net/cfile/226F4D4C544F42CF34', 200000);

INSERT INTO users (email, password)
values ('test1@gmail.com', 'test1pw1234');
INSERT INTO users (email, password)
values ('test2@naver.com', 'test2pw5678');
INSERT INTO users (email, password)
values ('test3@gmail.com', 'test3pw9090');
