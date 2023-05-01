DROP TABLE items;

CREATE TABLE IF NOT EXISTS ITEMS
(
    id        BIGINT      NOT NULL AUTO_INCREMENT,
    name      VARCHAR(50) NOT NULL,
    image_url TEXT,
    price     INT         NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO items (name, image_url, price) values ('위키드', 'https://image.yes24.com/themusical/upFiles/Themusical/Play/post_2013wicked.jpg', 150000);
INSERT INTO items (name, image_url, price) values ('마틸다', 'https://ticketimage.interpark.com/Play/image/large/22/22009226_p.gif', 100000);
INSERT INTO items (name, image_url, price) values ('빌리 엘리어트', 'https://t1.daumcdn.net/cfile/226F4D4C544F42CF34', 200000);
