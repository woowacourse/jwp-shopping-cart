CREATE TABLE IF NOT EXISTS PRODUCT
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    image_url VARCHAR(max) NOT NULL,
    price INT NOT NUll,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS MEMBER
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS CART
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    cart_count INT NOT NULL default '0',
    PRIMARY KEY(id),
    FOREIGN KEY(product_id) REFERENCES PRODUCT (id),
    FOREIGN KEY(member_id) REFERENCES MEMBER (id)
);

insert into MEMBER values(1L, 'test', 'test');
insert into MEMBER values(2L, 'test1', 'test1');
