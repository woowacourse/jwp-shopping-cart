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
