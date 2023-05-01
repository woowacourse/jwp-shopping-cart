DROP TABLE IF EXISTS PRODUCT;

CREATE TABLE IF NOT EXISTS PRODUCT (
    id             INT             NOT NULL AUTO_INCREMENT,
    name           VARCHAR(11)     NOT NULL,
    price          INT             NOT NULL,
    image_url      TEXT(500)       NOT NULL,
    created_at     DATETIME        NOT NULL default current_timestamp,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS MEMBER;

CREATE TABLE IF NOT EXISTS MEMBER (
    id              INT             NOT NULL AUTO_INCREMENT,
    email           VARCHAR(50)     NOT NULL,
    password        VARCHAR(100)    NOT NULL,
    created_at      DATETIME        NOT NULL default current_timestamp,
    PRIMARY KEY (id)
);

INSERT INTO MEMBER(email, password) VALUES ('munjin0201@naver.com', '1234');
INSERT INTO MEMBER(email, password) VALUES ('wpdnd0201@gmail.com', '5678');
