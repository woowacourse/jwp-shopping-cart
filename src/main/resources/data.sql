CREATE TABLE IF NOT EXISTS product
(
    id      LONG            NOT NULL    AUTO_INCREMENT,
    name    VARCHAR(255)    NOT NULL,
    imgURL  VARCHAR(8000)    NOT NULL,
    price   INT             NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS member
(
    id          LONG            NOT NULL    AUTO_INCREMENT,
    email       VARCHAR(255)    NOT NULL,
    password    VARCHAR(255)    NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO member (id, email, password) VALUES (1, 'a@a.com', 'password1');
INSERT INTO member (id, email, password) VALUES (2, 'b@b.com', 'password2');

CREATE TABLE IF NOT EXISTS cart
(
    id              LONG    NOT NULL    AUTO_INCREMENT,
    member_id       LONG    NOT NULL,
    product_id      LONG    NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id)     REFERENCES  MEMBER (id),
    FOREIGN KEY (product_id)    REFERENCES  PRODUCT (id)
);
