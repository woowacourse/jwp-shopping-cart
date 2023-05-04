DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS member;

CREATE TABLE product
(
    id     INT          NOT NULL AUTO_INCREMENT,
    name   VARCHAR(255) NOT NULL,
    price  INT          NOT NULL,
    imgUrl VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE member
(
    id       INT          NOT NULL AUTO_INCREMENT,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO member (email, password)
VALUES ('a@a.com', 'password1'),
       ('b@b.com', 'password2');
