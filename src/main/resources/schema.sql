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
-- 1. PK를 설정 안 하기
-- 2. FK를 설정 안 하기
create table cart
(
    member_id  BIGINT NOT NULL,
    product_id BIGINT NOT NULL
);
