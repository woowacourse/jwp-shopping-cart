drop table if exists cart_item cascade;

drop table if exists product cascade;

drop table if exists member cascade;

create table member
(
    id       bigint       not null auto_increment,
    email    varchar(255) not null unique,
    password varchar(30)  not null,
    nickname varchar(20)  not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table product
(
    id        bigint       not null auto_increment,
    name      varchar(255) not null,
    price     integer      not null,
    stock     integer      not null,
    image_url varchar(255),
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table cart_item
(
    id         bigint not null auto_increment,
    member_id  bigint not null,
    product_id bigint not null,
    quantity   int    not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table cart_item
    add constraint fk_cart_item_to_member
        foreign key (member_id) references member (id);

alter table cart_item
    add constraint fk_cart_item_to_product
        foreign key (product_id) references product (id);
