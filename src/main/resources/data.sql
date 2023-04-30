create table product
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    name      VARCHAR(255) NOT NULL,
    image_url TEXT         NOT NULL,
    price     DECIMAL      NOT NULL,
    primary key (id)
);

create table member
(
    email VARCHAR(255) NOT NULL,
    password TEXT NOT NULL,
    primary key (email)
);

insert into member (email, password) values ('a@a.com', 'password1');
insert into member (email, password) values ('b@b.com', 'password2');