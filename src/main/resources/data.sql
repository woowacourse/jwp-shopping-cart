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

insert into MEMBER values(1L, 'test', 'test');
insert into MEMBER values(2L, 'test1', 'test1');
