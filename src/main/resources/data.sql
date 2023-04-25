DROP TABLE product_list if exists;

CREATE TABLE product_list (
    id          BIGINT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(10)     NOT NULL,
    image       VARCHAR(255)    NOT NULL,
    price       INT             NOT NULL
);