create table if not exists customer
(
    id       bigint       not null auto_increment,
    username varchar(30)  not null unique,
    email    varchar(100) not null unique,
    password varchar(255) not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table if not exists product
(
    id        bigint       not null auto_increment,
    name      varchar(255) not null,
    price     integer      not null,
    image_url varchar(255),
    quantity  integer      not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table if not exists cart_item
(
    id          bigint not null auto_increment,
    customer_id bigint not null,
    product_id  bigint not null,
    count integer not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table cart_item
    add constraint fk_cart_item_to_customer
        foreign key (customer_id) references customer (id) on delete cascade;

alter table cart_item
    add constraint fk_cart_item_to_product
        foreign key (product_id) references product (id);

create table if not exists orders
(
    id          bigint not null auto_increment,
    customer_id bigint not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table orders
    add constraint fk_order_to_customer
        foreign key (customer_id) references customer (id) on delete cascade;

create table if not exists orders_detail
(
    id         bigint  not null auto_increment,
    orders_id  bigint  not null references orders (id),
    product_id bigint  not null references product (id),
    count   integer not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;