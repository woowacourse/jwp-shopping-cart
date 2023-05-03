CREATE TABLE PRODUCT (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    price INT NOT NULL,
    image VARCHAR(2000) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE MEMBER (
    id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO MEMBER (email, password) VALUES ('a@a.com', 'password1');
INSERT INTO MEMBER (email, password) VALUES ('b@b.com', 'password2');

CREATE TABLE CART (
    id INT NOT NULL AUTO_INCREMENT,
    product_id INT NOT NULL,
    member_id INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT `winners_ibfk_1` FOREIGN KEY (product_id) REFERENCES PRODUCT (id) ON DELETE CASCADE,
    CONSTRAINT `winners_ibfk_2` FOREIGN KEY (member_id) REFERENCES MEMBER (id) ON DELETE CASCADE
);