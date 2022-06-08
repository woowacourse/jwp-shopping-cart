drop table if exists order_detail;
drop table if exists orders;
drop table if exists cart_item;
drop table if exists product;
drop table if exists customer;

create table customer
(
    id           bigint       not null auto_increment,
    name         varchar(20)  not null unique,
    password     varchar(60)  not null,
    email        varchar(255) not null unique,
    address      varchar(255) not null,
    phone_number varchar(13)  not null,
    is_deleted   tinyint(1)   not null,
    primary key (id)
) engine = InnoDB
  default charset = utf8mb4;

create table product
(
    id          bigint       not null auto_increment,
    name        varchar(255) not null,
    price       integer      not null,
    image_url   varchar(255),
    is_deleted  tinyint(1)   not null,
    description varchar(255),
    primary key (id)
) engine = InnoDB
  default charset = utf8mb4;

create table cart_item
(
    id          bigint not null auto_increment,
    customer_id bigint not null,
    product_id  bigint not null,
    quantity    bigint not null,
    primary key (id),
    foreign key (customer_id) references customer (id),
    foreign key (product_id) references product (id)
) engine = InnoDB
  default charset = utf8mb4;

alter table cart_item
    add unique (customer_id, product_id);

create table orders
(
    id          bigint not null auto_increment,
    customer_id bigint not null,
    primary key (id),
    foreign key (customer_id) references customer (id)
) engine = InnoDB
  default charset = utf8mb4;

create table order_detail
(
    id         bigint  not null auto_increment,
    order_id   bigint  not null,
    product_id bigint  not null,
    quantity   integer not null,
    primary key (id),
    foreign key (order_id) references orders (id),
    foreign key (product_id) references product (id)
) engine = InnoDB
  default charset = utf8mb4;