drop table if exists order_detail;

drop table if exists orders;

drop table if exists cart_item;

drop table if exists full_address;

drop table if exists privacy;

drop table if exists product;

drop table if exists customer;

create table customer
(
    id                bigint       not null auto_increment,
    email             varchar(255) not null unique,
    password          varchar(255) not null,
    profile_image_url varchar(255) not null,
    created_at        timestamp default current_timestamp,
    terms             tinyint(1)   not null,
    primary key (id)
);

create table privacy
(
    customer_id bigint       not null,
    name        varchar(255) not null,
    gender      varchar(9)   not null,
    contact     varchar(11)  not null,
    birth_day   timestamp,
    foreign key (customer_id) references customer (id) on delete cascade,
    primary key (customer_id)
);

create table full_address
(
    customer_id    bigint       not null,
    address        varchar(255) not null,
    detail_address varchar(255),
    zone_code      char(5)      not null,
    foreign key (customer_id) references customer (id) on delete cascade,
    primary key (customer_id)
);

create table product
(
    id          bigint       not null auto_increment,
    name        varchar(255) not null,
    description varchar(255) not null,
    price       integer      not null,
    stock       integer      not null,
    image_url   varchar(255),
    primary key (id)
);

create table cart_item
(
    id          bigint  not null auto_increment,
    customer_id bigint  not null,
    product_id  bigint  not null,
    quantity    integer not null,
    foreign key (customer_id) references customer (id) on delete cascade,
    foreign key (product_id) references product (id) on delete cascade,
    primary key (id)
);

create table orders
(
    id          bigint not null auto_increment,
    customer_id bigint not null,
    foreign key (customer_id) references customer (id) on delete cascade,
    primary key (id)
);

create table order_detail
(
    id         bigint  not null auto_increment,
    order_id   bigint  not null,
    product_id bigint  not null,
    quantity   integer not null,
    foreign key (order_id) references orders (id) on delete cascade,
    foreign key (product_id) references product (id) on delete cascade,
    primary key (id)
)
