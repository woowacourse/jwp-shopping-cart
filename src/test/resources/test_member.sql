drop table cart_item if exists;
drop table member if exists;

create table member
(
    id       bigint       not null auto_increment,
    email    varchar(255) not null unique,
    password varchar(30)  not null,
    nickname varchar(20)  not null,
    primary key (id)
);