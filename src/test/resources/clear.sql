SET foreign_key_checks = 0;

truncate table orders_detail;
--alter table orders_detail alter column id restart with 1;

truncate table orders;
--alter table orders alter column id restart with 1;

truncate table cart_item;
--alter table cart_item alter column id restart with 1;

truncate table product;
--alter table product alter column id restart with 1;

truncate table customer;
--alter table customer alter column id restart with 1;

insert into customer (email, username, password)
values ('puterism@naver.com', 'puterism', '12349053145'),
       ('yujo@naver.com', 'yujo11', '123456789'),
       ('sunhpark@naver.com', 'sunhpark42', 'sdakdasf1234')
;

SET foreign_key_checks = 1;
