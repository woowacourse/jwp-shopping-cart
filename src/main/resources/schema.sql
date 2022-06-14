drop table if exists ORDERS_DETAIL;

drop table if exists ORDERS;

drop table if exists CART_ITEM;

drop table if exists PRODUCT;

drop table if exists CUSTOMER;

create table CUSTOMER
(
    id       bigint       not null auto_increment,
    email varchar(255),
    password varchar(64),
    name varchar(255) not null,
    phone varchar(13),
    address varchar(255),
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table CUSTOMER
    add unique key (name);

create table PRODUCT
(
    id        bigint       not null auto_increment,
    name      varchar(255) not null,
    price     integer      not null,
    image_url varchar(255),
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table CART_ITEM
(
    id          bigint not null auto_increment,
    customer_id bigint not null,
    product_id  bigint not null,
    quantity  integer not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table CART_ITEM
    add constraint fk_cart_item_to_customer
        foreign key (customer_id) references CUSTOMER (id) on delete cascade;

alter table CART_ITEM
    add constraint fk_cart_item_to_product
        foreign key (product_id) references PRODUCT (id) on delete cascade;

create table ORDERS
(
    id          bigint not null auto_increment,
    customer_id bigint not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table ORDERS
    add constraint fk_orders_to_customer
        foreign key (customer_id) references CUSTOMER (id) on delete cascade;

create table ORDERS_DETAIL
(
    id         bigint  not null auto_increment,
    orders_id  bigint  not null,
    product_id bigint  not null,
    quantity   integer not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table ORDERS_DETAIL
    add constraint fk_orders_detail_to_orders
        foreign key (orders_id) references ORDERS (id) on delete cascade;

alter table ORDERS_DETAIL
    add constraint fk_orders_detail_to_product
        foreign key (product_id) references PRODUCT (id) on delete cascade;
