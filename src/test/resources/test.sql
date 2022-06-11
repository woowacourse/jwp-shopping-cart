drop table if exists orders_detail;

drop table if exists orders;

drop table if exists cart_item;

drop table if exists product;

drop table if exists customer;

create table customer
(
    id       bigint       not null auto_increment,
    email   varchar(320) not null,
    password    varchar(255) not null,
    username    varchar(30) not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table customer
    add unique key (username);

create table product
(
    id        bigint       not null auto_increment,
    name      varchar(255) not null,
    price     integer      not null,
    thumbnail_url varchar(255),
    quantity integer not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table cart_item
(
    id          bigint not null auto_increment,
    customer_id bigint not null,
    product_id  bigint not null,
    count bigint not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table orders
(
    id          bigint not null auto_increment,
    customer_id bigint not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table orders_detail
(
    id          bigint not null auto_increment,
    product_id  bigint not null,
    count bigint not null,
    orders_id    bigint not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table cart_item
    add constraint fk_cart_item_to_customer
        foreign key (customer_id) references customer (id) on delete cascade;

alter table cart_item
    add constraint fk_cart_item_to_product
        foreign key (product_id) references product (id) on delete cascade;

alter table orders
    add constraint fk_orders_to_customer
        foreign key (customer_id) references customer (id) on delete cascade;

alter table orders_detail
    add constraint fk_orders_detail_to_orders
        foreign key (orders_id) references orders (id) on delete cascade;

alter table orders_detail
    add constraint fk_orders_detail_to_product
        foreign key (product_id) references product (id) on delete cascade;