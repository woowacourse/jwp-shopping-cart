drop table if exists cart_item;

drop table if exists product;

drop table if exists customer;

create table customer
(
    id       bigint       not null auto_increment,
    username varchar(30)  not null unique,
    email    varchar(100) not null unique,
    password varchar(255) not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table product
(
    id        bigint       not null auto_increment,
    name      varchar(255) not null,
    price     integer      not null,
    image_url varchar(255),
    quantity  integer      not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table cart_item
(
    id          bigint not null auto_increment,
    customer_id bigint not null,
    product_id  bigint not null,
    count integer not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table cart_item
    add constraint fk_cart_item_to_customer
        foreign key (customer_id) references customer (id);

alter table cart_item
    add constraint fk_cart_item_to_product
        foreign key (product_id) references product (id);
