drop table if exists orders_detail;

drop table if exists orders;

drop table if exists cart_item;

drop table if exists product;

drop table if exists customer;

create table customer
(
    id       bigint       not null auto_increment,
    email    varchar(255) not null,
    nickname varchar(255) not null unique,
    password varchar(255) not null,
    primary key (id)
) engine = InnoDB
  default charset = utf8mb4;

create table product
(
    id        bigint       not null auto_increment,
    name      varchar(255) not null,
    price     integer      not null,
    image_url varchar(255),
    primary key (id)
) engine = InnoDB
  default charset = utf8mb4;

create table cart_item
(
    id          bigint  not null auto_increment,
    customer_id bigint  not null,
    product_id  bigint  not null,
    quantity    integer not null,
    primary key (id)
) engine = InnoDB
  default charset = utf8mb4;

create table orders
(
    id          bigint not null auto_increment,
    customer_id bigint not null,
    order_date  datetime,
    primary key (id)
) engine = InnoDB
  default charset = utf8mb4;

create table orders_detail
(
    id         bigint  not null auto_increment,
    orders_id  bigint  not null,
    product_id bigint  not null,
    quantity   integer not null,
    primary key (id)
) engine = InnoDB
  default charset = utf8mb4;
