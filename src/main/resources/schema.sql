DROP TABLE IF EXISTS CART;
DROP TABLE IF EXISTS MEMBER;
DROP TABLE IF EXISTS PRODUCT;

CREATE TABLE PRODUCT
(
    id        INT           NOT NULL AUTO_INCREMENT,
    name      VARCHAR(20)   NOT NULL,
    price     INT           NOT NULL,
    image_url VARCHAR(2083) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE MEMBER
(
    id       INT         NOT NULL AUTO_INCREMENT,
    email    varchar(50) NOT NULL,
    password varchar(30) NOT NULL,
    name     varchar(10) NOT NULL,
    address  varchar(50) NOT NULL,
    age      int         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE CART
(
    member_id  INT NOT NULL,
    product_id INT NOT NULL,
    FOREIGN KEY (member_id) REFERENCES MEMBER ON UPDATE CASCADE,
    FOREIGN KEY (product_id) REFERENCES PRODUCT ON UPDATE CASCADE,
    PRIMARY KEY (member_id, product_id)
);
