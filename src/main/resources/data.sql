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
