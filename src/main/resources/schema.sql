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
    id       INT          NOT NULL AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(50)  NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (username)
);

CREATE TABLE CART
(
    id         INT NOT NULL AUTO_INCREMENT,
    user_id    INT NOT NULL,
    product_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES MEMBER (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES PRODUCT (id) ON DELETE CASCADE
);

INSERT INTO MEMBER (username, password)
VALUES ('user1@email.com', 'password1');
INSERT INTO MEMBER (username, password)
VALUES ('user2@email.com', 'password2');
