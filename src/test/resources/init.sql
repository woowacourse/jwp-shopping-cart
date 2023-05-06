delete from cart;
delete from product;
delete from member;

alter table cart auto_increment = 1;
alter table product auto_increment = 1;
alter table member auto_increment = 1;
