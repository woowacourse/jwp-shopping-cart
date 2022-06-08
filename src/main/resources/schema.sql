drop table if exists ordered_product;

drop table if exists orders;

drop table if exists cart_item;

drop table if exists thumbnail_image;

drop table if exists product;

drop table if exists customer;

create table customer
(
    id       bigint       not null auto_increment,
    email    varchar(255) not null,
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id)
) engine = InnoDB
  default charset = utf8mb4;

alter table customer
    add unique key (username);

create table product
(
    id             bigint       not null auto_increment,
    name           varchar(255) not null,
    price          integer      not null,
    stock_quantity integer      not null,
    primary key (id)
) engine = InnoDB
  default charset = utf8mb4;

create table thumbnail_image
(
    id         bigint not null auto_increment,
    product_id bigint not null,
    url        varchar(255),
    alt        varchar(255),
    primary key (id)
) engine = InnoDB
  default charset = utf8mb4;

alter table thumbnail_image
    add constraint fk_thumbnail_image_to_product
        foreign key (product_id) references product (id)
            ON DELETE CASCADE;

create table cart_item
(
    id          bigint not null auto_increment,
    quantity    int    not null,
    customer_id bigint not null,
    product_id  bigint not null,
    primary key (id)
) engine = InnoDB
  default charset = utf8mb4;

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
) engine = InnoDB
  default charset = utf8mb4;

alter table orders
    add constraint fk_orders_to_customer
        foreign key (customer_id) references customer (id);

create table ordered_product
(
    id         bigint  not null auto_increment,
    orders_id  bigint  not null,
    product_id bigint  not null,
    quantity   integer not null,
    primary key (id)
) engine = InnoDB
  default charset = utf8mb4;

alter table ordered_product
    add constraint fk_orders_detail_to_orders
        foreign key (orders_id) references orders (id);

alter table ordered_product
    add constraint fk_orders_detail_to_product
        foreign key (product_id) references product (id);