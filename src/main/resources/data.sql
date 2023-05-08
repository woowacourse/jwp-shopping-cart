


CREATE TABLE IF NOT EXISTS MEMBER (
    member_id LONG NOT NULL AUTO_INCREMENT,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    PRIMARY KEY (member_id)
);

CREATE TABLE IF NOT EXISTS PRODUCT (
    product_id LONG NOT NULL AUTO_INCREMENT,
    name VARCHAR(50)NOT NULL,
    image VARCHAR(255) NOT NULL,
    price LONG NOT NULL,
    PRIMARY KEY (product_id)
);

CREATE TABLE IF NOT EXISTS CART (
    id LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id LONG NOT NULL,
    product_id LONG NOT NULL
);

insert into member (email, password) values ('mail1', 'pass1');

insert into member (email, password) values ('user1', 'pass222');

