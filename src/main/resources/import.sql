drop table product if exists;
create table product
(
    id        bigint       not null auto_increment,
    name      varchar(255) not null,
    price     integer      not null,
    quantity  integer      not null,
    image_url varchar(255),
    primary key (id)
);

insert into product (name, price, quantity, image_url)
values ('캠핑 의자', 35000, 100, 'https://thawing-fortress-83192.herokuapp.com/static/images/camping-chair.jpg');
insert into product (name, price, quantity, image_url)
values ('그릴', 100000, 100, 'https://thawing-fortress-83192.herokuapp.com/static/images/grill.jpg');
insert into product (name, price, quantity, image_url)
values ('아이스박스', 20000, 100, 'https://thawing-fortress-83192.herokuapp.com/static/images/icebox.jpg');