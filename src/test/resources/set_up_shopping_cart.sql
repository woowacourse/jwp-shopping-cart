SET foreign_key_checks = 0;
truncate table cart_item;
truncate table orders_detail;
truncate table orders;
truncate table customer;
truncate table product;
SET foreign_key_checks = 1;

insert into customer (username, password)
values ('puterism', 'Aa!45678');
