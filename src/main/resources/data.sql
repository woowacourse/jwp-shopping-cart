
insert into customer (loginId, username, password)
values ('puterism@gmail.com', 'puterism', '3ff9c39a6c154bf5c37a02c5bfc4656cca8b8d588d70dcd5eb6654b59ca06b56'),
       ('tanney@gmail.com-102','tanney-102', '3ff9c39a6c154bf5c37a02c5bfc4656cca8b8d588d70dcd5eb6654b59ca06b56'),
       ('jho2301@gmail.com', 'jho2301', '3ff9c39a6c154bf5c37a02c5bfc4656cca8b8d588d70dcd5eb6654b59ca06b56'),
       ('365kim@gmail.com','365kim', '3ff9c39a6c154bf5c37a02c5bfc4656cca8b8d588d70dcd5eb6654b59ca06b56'),
       ('dudtjr913@gmail.com','dudtjr913', '3ff9c39a6c154bf5c37a02c5bfc4656cca8b8d588d70dcd5eb6654b59ca06b56'),
       ('jum0@gmail.com','jum0', '3ff9c39a6c154bf5c37a02c5bfc4656cca8b8d588d70dcd5eb6654b59ca06b56'),
       ('hyuuunjukim@gmail.com','hyuuunjukim', '3ff9c39a6c154bf5c37a02c5bfc4656cca8b8d588d70dcd5eb6654b59ca06b56'),
       ('zereight@gmail.com','zereight', '3ff9c39a6c154bf5c37a02c5bfc4656cca8b8d588d70dcd5eb6654b59ca06b56'),
       ('devhyun637@gmail.com','devhyun637', '3ff9c39a6c154bf5c37a02c5bfc4656cca8b8d588d70dcd5eb6654b59ca06b56'),
       ('swon3210@gmail.com','swon3210', '3ff9c39a6c154bf5c37a02c5bfc4656cca8b8d588d70dcd5eb6654b59ca06b56'),
       ('bigsaigon333@gmail.com','bigsaigon333', '3ff9c39a6c154bf5c37a02c5bfc4656cca8b8d588d70dcd5eb6654b59ca06b56'),
       ('yungo1846@gmail.com','yungo1846', '3ff9c39a6c154bf5c37a02c5bfc4656cca8b8d588d70dcd5eb6654b59ca06b56'),
       ('zigsong@gmail.com','zigsong', '3ff9c39a6c154bf5c37a02c5bfc4656cca8b8d588d70dcd5eb6654b59ca06b56'),
       ('iborymagic@gmail.com','iborymagic', '3ff9c39a6c154bf5c37a02c5bfc4656cca8b8d588d70dcd5eb6654b59ca06b56'),
       ('0307kwon@gmail.com','0307kwon', '3ff9c39a6c154bf5c37a02c5bfc4656cca8b8d588d70dcd5eb6654b59ca06b56'),
       ('gwangyeol@gmail.com','gwangyeol-im', '3ff9c39a6c154bf5c37a02c5bfc4656cca8b8d588d70dcd5eb6654b59ca06b56'),
       ('shinsehantan@gmail.com','shinsehantan', '3ff9c39a6c154bf5c37a02c5bfc4656cca8b8d588d70dcd5eb6654b59ca06b56'),
       ('ddongule@gmail.com','ddongule', '3ff9c39a6c154bf5c37a02c5bfc4656cca8b8d588d70dcd5eb6654b59ca06b56'),
       ('seojihwan@gmail.com','seojihwan', '3ff9c39a6c154bf5c37a02c5bfc4656cca8b8d588d70dcd5eb6654b59ca06b56'),
       ('0imbean0@gmail.com','0imbean0', '3ff9c39a6c154bf5c37a02c5bfc4656cca8b8d588d70dcd5eb6654b59ca06b56'),
       ('sunyoungkwon@gmail.com','sunyoungkwon', '3ff9c39a6c154bf5c37a02c5bfc4656cca8b8d588d70dcd5eb6654b59ca06b56'),
       ('hchayan@gmail.com','hchayan', '3ff9c39a6c154bf5c37a02c5bfc4656cca8b8d588d70dcd5eb6654b59ca06b56'),
       ('2sooy@gmail.com','2sooy', '3ff9c39a6c154bf5c37a02c5bfc4656cca8b8d588d70dcd5eb6654b59ca06b56'),
       ('yujo11@gmail.com','yujo11', '3ff9c39a6c154bf5c37a02c5bfc4656cca8b8d588d70dcd5eb6654b59ca06b56'),
       ('sunhpark42@gmail.com','sunhpark42', '3ff9c39a6c154bf5c37a02c5bfc4656cca8b8d588d70dcd5eb6654b59ca06b56')
;

insert into product (id, name, price, image_url)
values (1, '테스트 1', '1000', 'test'),
       (2, '테스트 2', '2000', 'test'),
       (3, '테스트 3', '3000', 'test');

insert into cart_item (id, customer_id, product_id)
values (1, 25, 1),
       (2, 25, 2);

insert into orders (id, customer_id)
values (1, 25),
       (2, 25);

insert into orders_detail (id, orders_id, product_id, quantity)
values (1, 1, 1, 1),
       (2, 1, 2, 2),
       (3, 2, 2, 3),
       (4, 2, 3, 4);

