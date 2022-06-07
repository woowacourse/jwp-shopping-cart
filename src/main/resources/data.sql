insert into customer (account, nickname, password, address, phone_number)
values ('pobi', 'eden', 'Password123!', 'address', '01012345678');

insert into product (name, price, image_url)
values ('삼립호빵', 5000, 'https://m.bakingmon.com/web/product/big/20200604/ca6e09b022765e331140afd0f8f81cbc.jpg');

insert into product (name, price, image_url)
values ('마이쮸 복숭아', 500, 'http://www.thessan.com/shopimages/thessancom/0060020000042.jpg?1477553153');

insert into product (name, price, image_url)
values ('꼬깔콘', 1300, 'https://sitem.ssgcdn.com/05/90/32/item/0000008329005_i1_1200.jpg');

insert into product (name, price, image_url)
values ('빅파이', 3000, 'http://www.thessan.com/shopimages/thessancom/0060020001432.jpg?1488531916');

insert into cart_item(customer_id, product_id)
values (1, 1);

insert into cart_item(customer_id, product_id)
values (1, 2);

