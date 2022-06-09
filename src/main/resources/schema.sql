drop table if exists orders_detail;

drop table if exists orders;

drop table if exists cart_item;

drop table if exists product;

drop table if exists customer;

create table customer
(
    id bigint not null auto_increment,
    email varchar(255) not null unique,
    password varchar(64) not null,
    name varchar(255) not null,
    phone varchar(13) not null,
    address varchar(255) not null,
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
    customer_id bigint not null,
    product_id  bigint not null,
    quantity int not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table cart_item
    add constraint fk_cart_item_to_customer
        foreign key (customer_id) references customer (id);

alter table cart_item
    add constraint fk_cart_item_to_product
        foreign key (product_id) references product (id);

create table orders
(
    id          bigint not null auto_increment,
    customer_id bigint not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table orders
    add constraint fk_orders_to_customer
        foreign key (customer_id) references customer (id);

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

insert into product (name, price, image_url)
values ('콜드 브루 몰트', 4800, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/02/[9200000001636]_20210225093600536.jpg'),
       ('바닐라 크림 콜드 브루', 4500, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000000487]_20210430112319040.jpg'),
       ('시그니처 핫 초콜릿', 5500, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[72]_20210415140949967.jpg'),
       ('제주 비자림 콜드 브루', 6500, 'https://image.istarbucks.co.kr/upload/store/skuimg/2022/03/[9200000002672]_20220311105511600.jpg'),
       ('롤린 민트 초코 콜드 브루', 2800, 'https://image.istarbucks.co.kr/upload/store/skuimg/2022/04/[9200000003988]_20220406113215251.jpg'),
       ('기운내라임', 4800, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9300000002853]_20210419104333070.jpg'),
       ('콜드 브루', 1800, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000000038]_20210430113202458.jpg'),
       ('라벤더 카페 브레베', 5800, 'https://image.istarbucks.co.kr/upload/store/skuimg/2022/04/[9200000004119]_20220412083025862.png'),
       ('럼 샷 코르타도', 5500, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/02/[9200000001086]_20210225090838931.jpg'),
       ('사케라또 비안코 오버 아이스', 6800, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/02/[9200000002095]_20210225095033382.jpg'),
       ('사케라또 아포가토', 4800, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/03/[9200000003505]_20210322093241535.jpg'),
       ('바닐라 플랫 화이트', 5800, 'https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000002406]_20210415135507733.jpg');
