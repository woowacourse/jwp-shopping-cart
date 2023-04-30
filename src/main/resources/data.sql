CREATE TABLE IF NOT EXISTS item
(
    id       bigint PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name     varchar(30)                       NOT NULL,
    item_url text                              NOT NULL,
    price    int                               NOT NULL
);

insert into item(name, item_url, price)
values ('치킨', 'https://image.homeplus.kr/td/f42afe4b-e0a8-4c79-a07c-aaee40c93a57', 10000);

CREATE TABLE IF NOT EXISTS member
(
    email        varchar(30) PRIMARY KEY NOT NULL,
    name         varchar(30)             NOT NULL,
    phone_number varchar(30)             NOT NULL,
    password     varchar(30)             NOT NULL
);

insert into member(email, name, phone_number, password) values ('admin@naver.com', 'test', '01098765432', 'qwer1234')
