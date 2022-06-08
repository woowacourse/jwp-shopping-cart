set foreign_key_checks = 0;

-- truncate table orders_detail;
--
-- truncate table orders;

truncate table cart_item;

truncate table product;

truncate table customer;

set foreign_key_checks = 1;

insert into customer (email, password, nickname)
values ('puterism@email.com', '12345678a', 'nick1'),
       ('sunhpark42@email.com', '12345678a', 'nick2')
;
