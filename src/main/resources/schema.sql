drop table if exists orders_detail;

drop table if exists orders;

drop table if exists cart_item;

drop table if exists product;

drop table if exists customer;

create table customer
(
    id              bigint       not null auto_increment,
    email           varchar(50)  not null,
    password        varchar(200) not null,
    profileImageUrl varchar(255)  not null,
    name            varchar(255) not null,
    gender          varchar(10)           default 'undefined',
    birthday        varchar(12)           default '',
    contact         varchar(11)  not null,
    address         varchar(100) not null,
    detailAddress   varchar(50)           default '',
    zoneCode        varchar(5)   not null,
    terms           boolean      not null default true,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table customer
    add unique key (email);

create table product
(
    id        bigint       not null auto_increment,
    name      varchar(255) not null,
    price     integer      not null,
    image_url varchar(255),
    description varchar(255),
    stock integer,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table cart_item
(
    id          bigint not null auto_increment,
    customer_id bigint not null,
    product_id  bigint not null,
    quantity    bigint,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

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
) engine=InnoDB default charset=utf8mb4;

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
) engine=InnoDB default charset=utf8mb4;

alter table orders_detail
    add constraint fk_orders_detail_to_orders
        foreign key (orders_id) references orders (id);

alter table orders_detail
    add constraint fk_orders_detail_to_product
        foreign key (product_id) references product (id);
