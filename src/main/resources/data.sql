CREATE TABLE PRODUCT (
    id          INT           NOT NULL AUTO_INCREMENT,
    `name`      VARCHAR(50)   NOT NULL,
    image       VARCHAR(255)   NOT NULL,
    price       INT           NOT NULL,
    PRIMARY KEY (id)
);

insert into PRODUCT(name, image, price) values ('치킨', 'https://m.pinktable.co.kr/web/product/big/202201/b5e8d4d93c7cdb33caa3bf80a5f02b2b.jpg', 10000);
insert into PRODUCT(name, image, price) values ('피자', 'https://m.pinktable.co.kr/web/product/big/202201/b5e8d4d93c7cdb33caa3bf80a5f02b2b.jpg', 20000);
insert into PRODUCT(name, image, price) values ('샐러드', 'https://m.pinktable.co.kr/web/product/big/202201/b5e8d4d93c7cdb33caa3bf80a5f02b2b.jpg', 30000);
