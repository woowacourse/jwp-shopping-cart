drop table if exists orders_detail;

drop table if exists orders;

drop table if exists cart_item;

drop table if exists product;

drop table if exists full_address;

drop table if exists privacy;

drop table if exists customer;

create table customer
(
    id                bigint       not null auto_increment,
    email             varchar(255) not null unique,
    password          varchar(255) not null,
    profile_image_url varchar(255) not null,
    created_at        timestamp default current_timestamp,
    terms             tinyint(1) not null,
    primary key (id)
);

create table privacy
(
    customer_id bigint       not null,
    name        varchar(255) not null,
    gender      varchar(9)   not null,
    contact     varchar(11)  not null,
    birth_day   timestamp,
    primary key (customer_id)
);

alter table privacy
    add constraint fk_privacy_to_customer
        foreign key (customer_id) references customer (id);

create table full_address
(
    customer_id    bigint       not null,
    address        varchar(255) not null,
    detail_address varchar(255),
    zone_code      char(5)      not null,
    primary key (customer_id)
);

alter table full_address
    add constraint fk_full_address_to_customer
        foreign key (customer_id) references customer (id);

create table product
(
    id        bigint       not null auto_increment,
    name      varchar(255) not null,
    price     integer      not null,
    image_url varchar(255),
    description varchar(255),
    stock int,
    primary key (id)
);

create table cart_item
(
    id          bigint not null auto_increment,
    customer_id bigint not null,
    product_id  bigint not null,
    quantity int not null,
    primary key (id)
);

alter table cart_item
    add constraint fk_cart_item_to_customer
        foreign key (customer_id) references customer (id);

alter table cart_item
    add constraint fk_cart_item_to_product
        foreign key (product_id) references product (id);

create table orders
(
    id          bigint not null auto_increment,
    customer_id bigint not null,
    primary key (id)
);

alter table orders
    add constraint fk_orders_to_customer
        foreign key (customer_id) references customer (id);

create table orders_detail
(
    id         bigint  not null auto_increment,
    orders_id  bigint  not null,
    product_id bigint  not null,
    quantity   integer not null,
    primary key (id)
);

alter table orders_detail
    add constraint fk_orders_detail_to_orders
        foreign key (orders_id) references orders (id);

alter table orders_detail
    add constraint fk_orders_detail_to_product
        foreign key (product_id) references product (id);
