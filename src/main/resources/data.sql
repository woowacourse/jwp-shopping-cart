CREATE TABLE IF NOT EXISTS product (
    id       INT           NOT NULL AUTO_INCREMENT,
    name     VARCHAR(20)   NOT NULL,
    image    VARCHAR(255)  NOT NULL,
    price    INT           NOT NULL,
    PRIMARY KEY (id)
);
