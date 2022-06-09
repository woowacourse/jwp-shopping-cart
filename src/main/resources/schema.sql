drop table if exists orders_detail;

drop table if exists orders;

drop table if exists cart_item;

drop table if exists product;

drop table if exists customer;

create table customer
(
    id       bigint       not null auto_increment,
    login_id varchar(255) not null unique,
    name     varchar(255) not null,
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
    id          bigint  not null auto_increment,
    customer_id bigint  not null,
    product_id  bigint  not null,
    quantity    integer not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table cart_item
    add constraint fk_cart_item_to_customer
        foreign key (customer_id) references customer (id) on delete cascade;

alter table cart_item
    add constraint fk_cart_item_to_product
        foreign key (product_id) references product (id) on delete cascade;

create table orders
(
    id          bigint not null auto_increment,
    customer_id bigint not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table orders
    add constraint fk_orders_to_customer
        foreign key (customer_id) references customer (id) on delete cascade;

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

insert into product (name, price, image_url)
values ('[아서] 칠레산 코호 냉동 연어필렛 trim D(껍질있음) 1.1~1.3kg', 24500,'https://cdn-mart.baemin.com/sellergoods/main/92438f0e-0c4b-425e-b03b-999cee7cdca2.jpg'),
       ('[아서] LEROY 노르웨이 생연어 원물 1마리 6~7kg', 193800,'https://cdn-mart.baemin.com/sellergoods/main/03751585-2305-4999-85dd-7d3aba184fe6.jpg'),
       ('[아서] 옥구농협 못잊어 신동진(신동진/보통/21년)20KG', 54300, 'https://cdn-mart.baemin.com/sellergoods/bulk/20220203-195555/23358-main-01.jpg'),
       ('[아서] 국내산 양상추 300g이상 1개', 2500,'https://cdn-mart.baemin.com/sellergoods/main/2a69b1c6-fa88-49f0-b049-0259674580f8.png'),
       ('[아서] 페루산 냉동 애플망고(2cmx2cm다이스) 1kg', 5090,'https://cdn-mart.baemin.com/sellergoods/main/2bb8dfcb-e4b0-48ff-a028-05aa9d9c2f19.jpg'),
       ('[아서] 국내산 팽이버섯 150g', 570, 'https://cdn-mart.baemin.com/sellergoods/bulk/20200902-201039/14900-main-01.jpg'),
       ('[아서] 냉동 브로콜리 (중국산) 1kg', 2820,'https://cdn-mart.baemin.com/sellergoods/main/d71a6c1d-2ff4-421f-bfa0-1080bcdaaa06.jpg'),
       ('[아서] 천일 갈릭라이스N 200g', 870,'https://cdn-mart.baemin.com/sellergoods/main/a7b26c35-e160-4d54-8889-bf2bf361e273.jpg'),
       ('[아서] 냉동다진마늘 1kg', 2670, 'https://cdn-mart.baemin.com/sellergoods/bulk/20220331-112605/24908-main-01.jpg'),
       ('[아서] 카벤디쉬 크링클컷 2kg', 6890,'https://cdn-mart.baemin.com/sellergoods/main/197d5058-373b-4bea-9a79-a0a30576765c.jpg'),
       ('[아서] 칠레산 코호 냉동 연어필렛 trim D(껍질있음) 1.1~1.3kg', 24500,'https://cdn-mart.baemin.com/sellergoods/main/92438f0e-0c4b-425e-b03b-999cee7cdca2.jpg'),
       ('[아서] LEROY 노르웨이 생연어 원물 1마리 6~7kg', 193800,'https://cdn-mart.baemin.com/sellergoods/main/03751585-2305-4999-85dd-7d3aba184fe6.jpg'),
       ('[아서] 옥구농협 못잊어 신동진(신동진/보통/21년)20KG', 54300,'https://cdn-mart.baemin.com/sellergoods/bulk/20220203-195555/23358-main-01.jpg'),
       ('[아서] 국내산 양상추 300g이상 1개', 2500,'https://cdn-mart.baemin.com/sellergoods/main/2a69b1c6-fa88-49f0-b049-0259674580f8.png'),
       ('[아서] 페루산 냉동 애플망고(2cmx2cm다이스) 1kg', 5090,'https://cdn-mart.baemin.com/sellergoods/main/2bb8dfcb-e4b0-48ff-a028-05aa9d9c2f19.jpg'),
       ('[아서] 국내산 팽이버섯 150g', 570, 'https://cdn-mart.baemin.com/sellergoods/bulk/20200902-201039/14900-main-01.jpg'),
       ('[아서] 냉동 브로콜리 (중국산) 1kg', 2820,'https://cdn-mart.baemin.com/sellergoods/main/d71a6c1d-2ff4-421f-bfa0-1080bcdaaa06.jpg'),
       ('[아서] 천일 갈릭라이스N 200g', 870,'https://cdn-mart.baemin.com/sellergoods/main/a7b26c35-e160-4d54-8889-bf2bf361e273.jpg'),
       ('[아서] 냉동다진마늘 1kg', 2670, 'https://cdn-mart.baemin.com/sellergoods/bulk/20220331-112605/24908-main-01.jpg'),
       ('[아서] 카벤디쉬 크링클컷 2kg', 6890,'https://cdn-mart.baemin.com/sellergoods/main/197d5058-373b-4bea-9a79-a0a30576765c.jpg'),
       ('[아서] 칠레산 코호 냉동 연어필렛 trim D(껍질있음) 1.1~1.3kg', 24500,'https://cdn-mart.baemin.com/sellergoods/main/92438f0e-0c4b-425e-b03b-999cee7cdca2.jpg'),
       ('[아서] LEROY 노르웨이 생연어 원물 1마리 6~7kg', 193800,'https://cdn-mart.baemin.com/sellergoods/main/03751585-2305-4999-85dd-7d3aba184fe6.jpg'),
       ('[아서] 옥구농협 못잊어 신동진(신동진/보통/21년)20KG', 54300, 'https://cdn-mart.baemin.com/sellergoods/bulk/20220203-195555/23358-main-01.jpg'),
       ('[아서] 국내산 양상추 300g이상 1개', 2500, 'https://cdn-mart.baemin.com/sellergoods/main/2a69b1c6-fa88-49f0-b049-0259674580f8.png'),
       ('[아서] 페루산 냉동 애플망고(2cmx2cm다이스) 1kg', 5090, 'https://cdn-mart.baemin.com/sellergoods/main/2bb8dfcb-e4b0-48ff-a028-05aa9d9c2f19.jpg'),
       ('[아서] 국내산 팽이버섯 150g', 570, 'https://cdn-mart.baemin.com/sellergoods/bulk/20200902-201039/14900-main-01.jpg'),
       ('[아서] 냉동 브로콜리 (중국산) 1kg', 2820, 'https://cdn-mart.baemin.com/sellergoods/main/d71a6c1d-2ff4-421f-bfa0-1080bcdaaa06.jpg'),
       ('[아서] 천일 갈릭라이스N 200g', 870, 'https://cdn-mart.baemin.com/sellergoods/main/a7b26c35-e160-4d54-8889-bf2bf361e273.jpg'),
       ('[아서] 냉동다진마늘 1kg', 2670, 'https://cdn-mart.baemin.com/sellergoods/bulk/20220331-112605/24908-main-01.jpg'),
       ('[아서] 카벤디쉬 크링클컷 2kg', 6890, 'https://cdn-mart.baemin.com/sellergoods/main/197d5058-373b-4bea-9a79-a0a30576765c.jpg')
;
