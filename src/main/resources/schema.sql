create table product
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    name      VARCHAR(255) NOT NULL,
    image_url TEXT         NOT NULL,
    price     INT          NOT NULL,
    primary key (id)
);

create table member
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(260) NOT NULL,
    primary key (id)
);

-- 해당 테이블에 대해 고민해보기
-- 1. PK를 고민해보기
-- 2. FK를 고민해보기
create table cart
(
    id         BIGINT NOT NULL AUTO_INCREMENT,
    member_id  BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    foreign key (member_id) references member (id) on delete cascade,
    foreign key (product_id) references product (id) on delete cascade,
    primary key (id)
);
