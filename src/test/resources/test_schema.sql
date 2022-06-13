drop table if exists orders_detail;

drop table if exists orders;

drop table if exists cart_item;

drop table if exists product;

drop table if exists customer;

create table customer
(
    id       bigint      not null auto_increment,
    username varchar(20) not null unique,
    password varchar(64) not null,
    nickname varchar(10) not null,
    age      int         not null,
    primary key (id)
);

create table product
(
    id        bigint       not null auto_increment,
    name      varchar(255) not null,
    price     integer      not null,
    thumbnail varchar(255),
    primary key (id)
);

create table cart_item
(
    id                bigint       not null auto_increment,
    customer_username varchar(255) not null,
    product_id        bigint       not null,
    quantity          bigint       not null,
    primary key (id)
);

create table orders
(
    id                bigint       not null auto_increment,
    customer_username varchar(255) not null,
    primary key (id)
);

create table orders_detail
(
    id         bigint  not null auto_increment,
    orders_id  bigint  not null,
    product_id bigint  not null,
    quantity   integer not null,
    primary key (id)
);