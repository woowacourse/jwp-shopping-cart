CREATE TABLE PRODUCT (
    id          INT             NOT NULL AUTO_INCREMENT,
    name        VARCHAR(10)     NOT NULL,
    url         VARCHAR(255)    NOT NULL,
    price       INT             NOT NULL,
    PRIMARY  KEY (id)
);

CREATE TABLE MEMBER (
    id          INT             NOT NULL AUTO_INCREMENT,
    email       VARCHAR(100)    NOT NULL,
    password    VARCHAR(10)     NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE CART (
    id          INT             NOT NULL AUTO_INCREMENT,
    product_id  INT             NOT NULL,
    member_id   INT             NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO MEMBER VALUES (1, 'a@a.com', 'password1');
INSERT INTO MEMBER VALUES (2, 'b@b.com', 'password2');
