truncate table cart;
alter table cart auto_increment = 1;
insert into cart (user_id, product_id)
values (1, 1), (1, 2), (2, 1);
