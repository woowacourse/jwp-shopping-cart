SET foreign_key_checks = 0;

truncate table orders_detail;
alter table orders_detail alter column id restart with 1;

truncate table orders;
alter table orders alter column id restart with 1;

truncate table cart_item;
alter table cart_item alter column id restart with 1;

truncate table product;
alter table product alter column id restart with 1;

truncate table customer;
alter table customer alter column id restart with 1;

SET foreign_key_checks = 1;
