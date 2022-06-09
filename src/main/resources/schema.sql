drop table if exists cart_item;

drop table if exists product;

drop table if exists accounts;

create table accounts
(
    id       bigint              not null auto_increment,
    email    varchar(255) unique not null,
    password varchar(128)        not null,
    nickname varchar(255)        not null,
    is_admin boolean             not null,
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
    account_id bigint not null,
    product_id bigint not null,
    quantity   int,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table cart_item
    add constraint fk_cart_item_to_customer
        foreign key (account_id) references accounts (id);

alter table cart_item
    add constraint fk_cart_item_to_product
        foreign key (product_id) references product (id);
