create table products
(
    id bigint auto_increment primary key,
    product_name varchar(100) not null ,
    product_price int not null ,
    product_image varchar(max) not null
);
CREATE TABLE user_table
(
    id bigint auto_increment primary key,
    email varchar(100) not null,
    password varchar(100) not null
);
CREATE TABLE cart_table
(
    id bigint auto_increment primary key,
    user_id bigint not null,
    product_id bigint not null,
    foreign key (user_id) references user_table (id),
    foreign key (product_id) references products (id)
);
