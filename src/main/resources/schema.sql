set FOREIGN_KEY_CHECKS = 0;
drop table if exists orders_detail;
drop table if exists orders;
drop table if exists cart;
drop table if exists product;
drop table if exists customer;
set FOREIGN_KEY_CHECKS = 1;

create table customer
(
    id bigint auto_increment not null,
    email varchar(255) not null unique,
    password varchar(255) not null,
    nickname varchar(255) not null,
    primary key(id)
);

create table product
(
    id        bigint       not null auto_increment,
    name      varchar(255) not null,
    price     integer      not null,
    image varchar(255),
    primary key (id)
);

create table cart
(
    id          bigint not null auto_increment,
    customer_id bigint not null,
    product_id  bigint not null,
    quantity   integer not null,

    primary key (id)
--    foreign key (customer_id) references customer(id),
--    foreign key (product_id) references product(id)
);

create table orders
(
    id          bigint not null auto_increment,
    customer_id bigint not null,

    primary key (id),
    foreign key (customer_id) references customer(id)
);

create table orders_detail
(
    id         bigint  not null auto_increment,
    orders_id  bigint  not null,
    product_id bigint  not null,
    quantity   integer not null,

    primary key (id),
    foreign key (orders_id) references orders(id),
    foreign key (product_id) references product(id)
);
