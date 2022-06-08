create table customer
(
    id       bigint      not null auto_increment,
    username varchar(20) not null unique,
    password varchar(64) not null,
    nickname varchar(10) not null,
    age      int         not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table product
(
    id        bigint       not null auto_increment,
    name      varchar(255) not null,
    price     integer      not null,
    thumbnail varchar(255),
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table cart_item
(
    id          bigint not null auto_increment,
    customer_id bigint not null,
    product_id  bigint not null,
    quantity    bigint not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table orders
(
    id          bigint not null auto_increment,
    customer_id bigint not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table orders_detail
(
    id         bigint  not null auto_increment,
    orders_id  bigint  not null,
    product_id bigint  not null,
    quantity   integer not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

insert into product (name, price, thumbnail)
values ('싱싱한감자', 5000, 'https://storybook.takealook.kr/image/potato.jpg'),
       ('양념감자', 2000, 'https://storybook.takealook.kr/image/potato.jpg'),
       ('왕감자', 40000, 'https://storybook.takealook.kr/image/potato.jpg');