CREATE TABLE IF NOT EXISTS product
(
    id    BIGINT      NOT NULL AUTO_INCREMENT,
    name  VARCHAR(64) NOT NULL,
    price INT         NOT NULL,
    image TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_list
(
    id          BIGINT      NOT NULL AUTO_INCREMENT,
    email       VARCHAR(32) NOT NULL,
    password    VARCHAR(32) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS cart
(
    id          BIGINT  NOT NULL AUTO_INCREMENT,
    user_id     BIGINT  NOT NULL,
    product_id  BIGINT  NOT NULL,
    PRIMARY KEY (id)
);

-- Dummy Data --
INSERT INTO product(name, price, image) VALUES ('치킨', 20000, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQq3KgxiY16cfXs1r99Q6QF9KEz2ahuh967Xg&usqp=CAU');
INSERT INTO product(name, price, image) VALUES ('샐러드', 10000, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTsvb-GYnRmJZRY7tyLWIoRDN_aGmCOQgVXOz3HX80td8rHbmTvWw7TH6zPd4Bt2CoOnno&usqp=CAU');

INSERT INTO user_list(email, password) VALUES ('test01@gmail.com', '12121212');
INSERT INTO user_list(email, password) VALUES ('test02@gmail.com', '34343434');
