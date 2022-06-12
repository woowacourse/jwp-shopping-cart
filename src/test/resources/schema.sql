drop table if exists orders_detail;

drop table if exists orders;

drop table if exists cart_item;

drop table if exists product;

drop table if exists member;

create table member
(
    id bigint not null auto_increment,
    email varchar(30) not null unique,
    name varchar(10) not null,
    password varchar(64) not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table product
(
    id bigint not null auto_increment,
    name varchar(255) not null,
    price integer not null,
    image_url varchar(255),
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table cart_item
(
    id bigint not null auto_increment,
    member_id bigint not null,
    product_id  bigint not null,
    quantity integer not null,
    primary key (id),
    foreign key (member_id) references member(id) on delete cascade,
    foreign key (product_id) references product (id) on delete cascade
) engine=InnoDB default charset=utf8mb4;

create table orders
(
    id bigint not null auto_increment,
    member_id bigint not null,
    primary key (id),
    foreign key (member_id) references member (id) on delete cascade
) engine=InnoDB default charset=utf8mb4;

create table orders_detail
(
    id bigint  not null auto_increment,
    orders_id  bigint  not null,
    product_id bigint  not null,
    quantity   integer not null,
    primary key (id),
    foreign key (orders_id) references orders (id) on delete cascade,
    foreign key (product_id) references product (id) on delete cascade
) engine=InnoDB default charset=utf8mb4;
