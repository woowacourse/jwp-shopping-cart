drop table if exists orders_detail;

drop table if exists orders;

drop table if exists cart_item;

drop table if exists product;

drop table if exists member;

create table member
(
    id       bigint       not null auto_increment,
    email varchar(255) not null,
    name varchar(20) not null,
    password varchar(255) not null,
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
    id          bigint not null auto_increment,
    member_id bigint not null,
    product_id  bigint not null,
    quantity integer not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table cart_item
    add constraint fk_cart_item_to_member
        foreign key (member_id) references member (id) on delete cascade;

alter table cart_item
    add constraint fk_cart_item_to_product
        foreign key (product_id) references product (id) on delete cascade;

create table orders
(
    id          bigint not null auto_increment,
    member_id bigint not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table orders
    add constraint fk_orders_to_member
        foreign key (member_id) references member (id) on delete cascade;

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
        foreign key (orders_id) references orders (id) on delete cascade;

alter table orders_detail
    add constraint fk_orders_detail_to_product
        foreign key (product_id) references product (id) on delete cascade;
