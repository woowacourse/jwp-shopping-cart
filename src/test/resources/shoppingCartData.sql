TRUNCATE TABLE product_in_cart;
ALTER TABLE product_in_cart
    AUTO_INCREMENT = 1;
insert into product_in_cart (member_id, product_id)
values (1L, 1L);
