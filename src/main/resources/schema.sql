drop table if exists cart_item;
drop table if exists product;
drop table if exists member;

create table MEMBER
(
    id       bigint       not null auto_increment,
    email    varchar(255) not null unique,
    password varchar(30)  not null,
    nickname varchar(20)  not null,
    primary key (id)
);

create table PRODUCT
(
    id        bigint       not null auto_increment,
    name      varchar(255) not null,
    price     integer      not null,
    stock     integer      not null,
    image_url varchar(255),
    primary key (id)
);

create table CART_ITEM
(
    id         bigint  not null auto_increment,
    member_id  bigint  not null,
    product_id bigint  not null,
    quantity   integer not null,
    primary key (id),
    foreign key (member_id) references MEMBER (id) ON DELETE CASCADE,
    foreign key (product_id) references PRODUCT (id) ON DELETE CASCADE
);
