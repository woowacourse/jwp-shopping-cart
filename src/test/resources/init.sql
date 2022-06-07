TRUNCATE TABLE orders_detail;
TRUNCATE TABLE orders;
TRUNCATE TABLE cart_item;
TRUNCATE TABLE product;
TRUNCATE TABLE customer;

insert into product (id, name, price, image_url)
values (1, '상품1', 1000, 'http://img1.url'),
       (2, '상품2', 2000, 'http://img2.url'),
       (3, '상품3', 3000, 'http://img3.url'),
       (4, '상품4', 4000, 'http://img4.url');
