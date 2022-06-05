set foreign_key_checks = 0;

truncate table orders_detail;

truncate table orders;

truncate table cart_item;

truncate table product;

truncate table customer;

set foreign_key_checks = 1;

insert into customer (username)
values ('puterism'),
       ('tanney-102'),
       ('jho2301'),
       ('365kim'),
       ('dudtjr913'),
       ('jum0'),
       ('hyuuunjukim'),
       ('zereight'),
       ('devhyun637'),
       ('swon3210'),
       ('bigsaigon333'),
       ('yungo1846'),
       ('zigsong'),
       ('iborymagic'),
       ('0307kwon'),
       ('gwangyeol-im'),
       ('shinsehantan'),
       ('ddongule'),
       ('seojihwan'),
       ('0imbean0'),
       ('sunyoungkwon'),
       ('hchayan'),
       ('2sooy'),
       ('yujo11'),
       ('sunhpark42')
;

insert into product(id, name, price, image_url)
values (1,'치약',1600,'https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/163178721379405896.jpg?gif=1&w=512&h=512&c=c'),
       (2,'칫솔',4300,'https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/162011554440791261.jpg?gif=1&w=512&h=512&c=c'),
       (3,'비누',2200,'https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/159108336974173358.jpg?gif=1&w=512&h=512&c=c')
;

alter table product auto_increment = 4;
