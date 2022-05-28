drop table if exists orders_detail;

drop table if exists orders;

drop table if exists cart_item;

drop table if exists product;

drop table if exists full_address;

drop table if exists privacy;

drop table if exists user;

create table user
(
    id                bigint       not null auto_increment,
    email             varchar(255) not null unique,
    password          varchar(255) not null,
    profile_image_url varchar(255) not null,
    privacy_id        bigint       not null,
    created_at        timestamp default current_timestamp,
    terms             tinyint(1) not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table user
    foreign key (privacy_id) references privacy (id);

create table privacy
(
    id              bigint       not null auto_increment,
    name            varchar(255) not null,
    gender          varchar(9)   not null,
    birth_day       timestamp,
    full_address_id bigint       not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table privacy
    foreign key (full_address_id) references full_address (id);

create table full_address
(
    id             bigint       not null auto_increment,
    address        varchar(255) not null,
    detail_address varchar(255),
    zone_code      char(5)      not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table product
(
    id        bigint       not null auto_increment,
    name      varchar(255) not null,
    price     integer      not null,
    image_url varchar(255),
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table cart_item
(
    id         bigint not null auto_increment,
    user_id    bigint not null,
    product_id bigint not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table cart_item
    add constraint fk_cart_item_to_user
        foreign key (user_id) references user (id);

alter table cart_item
    add constraint fk_cart_item_to_product
        foreign key (product_id) references product (id);

create table orders
(
    id      bigint not null auto_increment,
    user_id bigint not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table orders
    add constraint fk_orders_to_user
        foreign key (user_id) references user (id);

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
