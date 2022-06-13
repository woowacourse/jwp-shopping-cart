set FOREIGN_KEY_CHECKS = 0;
truncate table orders_detail;
truncate table orders;
truncate table cart_item;
truncate table product;
truncate table customer;
set FOREIGN_KEY_CHECKS = 1;

insert into customer (email, nickname, password)
values ('test1@email.com', 'puterism', 'Password123!'),
       ('test2@email.com', 'gwangyeol', 'Password123!'),
       ('test3@email.com', 'test1', 'Password123!');
