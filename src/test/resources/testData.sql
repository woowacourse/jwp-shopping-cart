SET FOREIGN_KEY_CHECKS = 0;
truncate table orders_detail;
truncate table cart_item;
truncate table orders;
truncate table product;
truncate table customer;
SET FOREIGN_KEY_CHECKS = 1;

insert into customer (name, email, password) values
('썬', 'sun@gmail.com', '$2a$12$oieQrkTJBRir5HXe85Z0t.9KUDoiGb/5e3JqIwrtDBedHDDCRbpqG');

insert into product (name, price, image_url) values
('치킨', 10000, 'http://example.com/chicken.jpg'),
('맥주', 20000, 'http://example.com/beer.jpg');
