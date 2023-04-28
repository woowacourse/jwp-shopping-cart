CREATE TABLE IF NOT EXISTS product
(
    id     INT          NOT NULL AUTO_INCREMENT,
    name   VARCHAR(255) NOT NULL,
    price  INT          NOT NULL,
    imgUrl TEXT NOT NULL,
    PRIMARY KEY (id)
);
