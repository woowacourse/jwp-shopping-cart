CREATE TABLE IF NOT EXISTS PRODUCT
(
    id    INT           NOT NULL AUTO_INCREMENT,
    name  VARCHAR(200)  NOT NULL,
    image VARCHAR(2047) NOT NULL,
    price BIGINT        NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS MEMBER
(
    id       INT          NOT NULL AUTO_INCREMENT,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS CART
(
    id        INT NOT NULL AUTO_INCREMENT,
    memberId  INT NOT NULL,
    productId INT NOT NULL,
    FOREIGN KEY (memberId) REFERENCES MEMBER (id) ON DELETE CASCADE,
    FOREIGN KEY (productId) REFERENCES PRODUCT (id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

INSERT INTO member(id, email, password)
VALUES (100, 'moooooo@gmail.com', 'abcd');

INSERT INTO product (id, name, image, price)
VALUES (100, '조개소년', 'a', 1000);

INSERT INTO member(id, email, password)
VALUES (101, 'moooooo0000@gmail.com', 'abcd');

INSERT INTO product (id, name, image, price)
VALUES (101, '조개소녀', 'b', 100000);

