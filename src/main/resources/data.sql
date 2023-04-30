CREATE TABLE IF NOT EXISTS item
(
    id       bigint PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name     varchar(30)                       NOT NULL,
    item_url text                              NOT NULL,
    price    int                               NOT NULL
);

CREATE TABLE IF NOT EXISTS member
(
    email        varchar(30) PRIMARY KEY NOT NULL,
    name         varchar(30)             NOT NULL,
    phone_number varchar(30)             NOT NULL,
    password     varchar(30)             NOT NULL
);

CREATE TABLE IF NOT EXISTS cart
(
    email varchar(30) NOT NULL,
    id    bigint      NOT NULL,
    PRIMARY KEY (email, id),
    FOREIGN KEY (id) REFERENCES item (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (email) REFERENCES member (email) ON DELETE CASCADE ON UPDATE CASCADE
);

insert into item(name, item_url, price) values ('치킨', 'https://image.homeplus.kr/td/f42afe4b-e0a8-4c79-a07c-aaee40c93a57', 10000);
insert into item(name, item_url, price) values ('샐러드', 'https://www.raracost.com/images/sub/salad1.png', 20000);
insert into item(name, item_url, price) values ('피자', 'https://www.ryupizza.com/wp-content/uploads/pizza401_1-1.jpg', 30000);
insert into member(email, name, phone_number, password) values ('admin@naver.com', 'test', '01098765432', 'qwer1234');
insert into cart(email, id) values ('admin@naver.com', 1);

