truncate table products;
alter table products alter id restart with 1;

insert into products(product_name, product_price, product_image) values ('test1', 1000, 'https://test.com/image1');
insert into products(product_name, product_price, product_image) values ('test2', 2000, 'https://test.com/image2');
insert into products(product_name, product_price, product_image) values ('test3', 3000, 'https://test.com/image3');
