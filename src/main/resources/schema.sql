create table product
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    name      VARCHAR(255) NOT NULL,
    image_url TEXT         NOT NULL,
    price     INT          NOT NULL,
    primary key (id)
);

create table user
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(260) NOT NULL,
    primary key (id)
);
