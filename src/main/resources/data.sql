CREATE TABLE IF NOT EXISTS product
(
    id      LONG            NOT NULL    AUTO_INCREMENT,
    name    VARCHAR(255)    NOT NULL,
    imgURL  VARCHAR(255)    NOT NULL,
    price   INT             NOT NULL,
    PRIMARY KEY (id)
);
