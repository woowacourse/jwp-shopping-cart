CREATE TABLE PRODUCT
(
    id        INT AUTO_INCREMENT NOT NULL,
    name      VARCHAR NOT NULL,
    price     INT     NOT NULL,
    image_url VARCHAR NOT NULL,
    PRIMARY KEY (id)
);

insert into PRODUCT(name, price, image_url)
values ('123', 1000, 'naver');

insert into PRODUCT(name, price, image_url)
values ('12345', 1000, 'naver');