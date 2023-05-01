insert into product(id, name, price, image_url)
values (1, '피자', 10000, 'https://t1.daumcdn.net/cfile/tistory/241D303357DD2A1404');

insert into product(id, name, price, image_url)
values (2, '치킨', 20000, 'https://pelicana.co.kr/resources/images/menu/best_menu03_200623.jpg');

insert into cart(id)
values (1);

insert into cart(id)
values (2);

insert into account(id, email, password, cart_id)
values (1, '회원1@gg', '1234', 1);

insert into account(id, email, password, cart_id)
values (2, '회원2@gg', '12345', 2);
