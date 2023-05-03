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
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);

INSERT INTO member (email, password) values ('email@email', 'password');
INSERT INTO member (email, password) values ('email2@email', 'password2');
