set foreign_key_checks = 0;

truncate table orders_detail;
alter table orders_detail auto_increment = 1;

truncate table orders;
alter table orders auto_increment = 1;

truncate table cart_item;
alter table cart_item auto_increment = 1;

truncate table product;
alter table product auto_increment = 1;

truncate table customer;
alter table customer auto_increment = 1;

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
