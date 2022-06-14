drop table if exists orders_detail;

drop table if exists orders;

drop table if exists cart;

drop table if exists product;

drop table if exists customer;

create table customer
(
    id       bigint       not null auto_increment,
    email    varchar(255) not null unique,
    name     varchar(30) not null,
    phone    varchar(13) not null,
    address  varchar(255) not null,
    password varchar(64) not null,
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

create table cart
(
    id          bigint not null auto_increment,
    customer_id bigint not null,
    product_id  bigint not null,
    quantity    integer not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

insert into product(name, price, thumbnail) values ('콜드 브루 몰트', 4800, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/02/[9200000001636]_20210225093600536.jpg');
insert into product(name, price, thumbnail) values ('바닐라 크림 콜드 브루', 4500, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000000487]_20210430112319040.jpg');
insert into product(name, price, thumbnail) values ('시그니처 핫 초콜릿', 5500, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[72]_20210415140949967.jpg');
