TRUNCATE TABLE orders_detail;
TRUNCATE TABLE orders;
TRUNCATE TABLE cart_item;
TRUNCATE TABLE product;
TRUNCATE TABLE customer;

ALTER TABLE customer
    ALTER COLUMN id RESTART WITH 1;

INSERT INTO product
values (1, '우유', 3000, 'http://example1.com');
INSERT INTO product
values (2, '바나나', 3000, 'http://example2.com');
