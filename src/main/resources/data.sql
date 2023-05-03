CREATE TABLE product
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    name      VARCHAR(255) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    price     INT          NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO users (email, password)
VALUES ('a@a.com', 'password1');
INSERT INTO users (email, password)
VALUES ('b@b.com', 'password2');

