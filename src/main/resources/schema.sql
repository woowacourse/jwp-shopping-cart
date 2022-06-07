drop table if exists orders_detail;

drop table if exists orders;

drop table if exists cart;

drop table if exists product;

drop table if exists member;

create table member
(
    id       bigint       not null auto_increment,
    email    varchar(255) not null,
    password varchar(64)  not null,
    name     varchar(10)  not null,
    primary key (id)
) engine = InnoDB
  default charset = utf8mb4;

alter table member
    add unique key (email);

create table product
(
    id        bigint       not null auto_increment,
    name      varchar(255) not null,
    price     integer      not null,
    image_url varchar(255),
    primary key (id)
) engine = InnoDB
  default charset = utf8mb4;

create table cart
(
    id         bigint not null auto_increment,
    member_id  bigint not null,
    product_id bigint not null,
    quantity   int    not null,
    primary key (id)
) engine = InnoDB
  default charset = utf8mb4;

alter table cart
    add constraint fk_cart_to_member
        foreign key (member_id) references member (id)
            on delete cascade;

alter table cart
    add constraint fk_cart_to_product
        foreign key (product_id) references product (id)
            on delete cascade;

create table orders
(
    id        bigint not null auto_increment,
    member_id bigint not null,
    primary key (id)
) engine = InnoDB
  default charset = utf8mb4;

alter table orders
    add constraint fk_orders_to_member
        foreign key (member_id) references member (id)
            on delete cascade;

create table orders_detail
(
    id         bigint  not null auto_increment,
    orders_id  bigint  not null,
    product_id bigint  not null,
    quantity   integer not null,
    primary key (id)
) engine = InnoDB
  default charset = utf8mb4;

alter table orders_detail
    add constraint fk_orders_detail_to_orders
        foreign key (orders_id) references orders (id)
            on delete cascade;

alter table orders_detail
    add constraint fk_orders_detail_to_product
        foreign key (product_id) references product (id)
            on delete cascade;
