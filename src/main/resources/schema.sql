create table products
(
    id bigint auto_increment primary key,
    product_name varchar(100) not null ,
    product_price int not null ,
    product_image varchar(max) not null
);
