CREATE TABLE PRODUCT
(
    id        INT AUTO_INCREMENT NOT NULL,
    name      VARCHAR NOT NULL,
    price     INT     NOT NULL,
    image_url VARCHAR NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE MEMBER
(
    id       INT AUTO_INCREMENT NOT NULL,
    email    VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE CART
(
    id         INT AUTO_INCREMENT NOT NULL,
    member_id  INT NOT NULL,
    product_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES MEMBER (id),
    FOREIGN KEY (product_id) REFERENCES PRODUCT (id)
);

insert into PRODUCT
values (1, 'aaa', 1000, 'test'),
       (2, '123', 1000, 'test2');

insert into MEMBER
values (1, 'email', 'password'),
       (2, 'email1', 'password');

