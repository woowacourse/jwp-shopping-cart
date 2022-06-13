set foreign_key_checks = 0;

truncate table orders_detail;
alter table orders_detail auto_increment = 1;

truncate table orders;
alter table orders auto_increment = 1;

truncate table cart_item;
alter table cart_item auto_increment = 1;

truncate table product;
alter table product auto_increment = 1;

truncate table customer;
alter table customer auto_increment = 1;

set foreign_key_checks = 1;

insert into customer (username, email, password)
values ('puterism', 'crew01@naver.com', 'a12345'),
       ('tanney-102', 'crew02@naver.com', 'a12345'),
       ('jho2301', 'crew03@naver.com', 'a12345')
;

insert into product (name, price, image_url)
values ('water', 1000, 'image_url'),
       ('cheese', 2000, 'image_url'),
       ('paper', 100, 'image_url'),
       ('pen', 500, 'image_url')
;