-- product
insert into product (name, image_url, price)
values ('사과', 'https://news.imaeil.com/photos/2021/10/19/2021101920550174833_l.jpg', 4000);
insert into product (name, image_url, price)
values ('치킨', 'https://data.0app0.com/diet/shop/goods/20210219/20210219151516_6436453018_2.jpg', 20000);

-- member
insert into member (email, password)
values ('hongSile@wooteco.com', 'hongsile');
insert into member (email, password)
values ('seovalue@wooteco.com', 'seovalue');

-- cart
insert into cart (member_id, product_id)
values (1L, 1L);
