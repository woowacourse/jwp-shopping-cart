insert into cart_item (id, customer_id, product_id, quantity)
values (1, 25, 1, 5),
       (2, 25, 2, 6);

insert into orders (id, customer_id)
values (1, 25),
       (2, 25);

insert into orders_detail (id, orders_id, product_id, quantity)
values (1, 1, 1, 1),
       (2, 1, 2, 2),
       (3, 2, 2, 3),
       (4, 2, 3, 4);
