truncate table product;
alter table product auto_increment = 1;
insert into product (name, image, price)
values ('삼겹살', '3-hierarchy-meat.jpg', 16000),
       ('목살', 'neck-meat.jpg', 15000),
       ('갈비살', 'ribs-meat.jpg', 14000);
