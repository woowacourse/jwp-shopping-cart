DROP TABLE IF EXISTS product;

CREATE TABLE product
(
    id     BIGINT AUTO_INCREMENT,
    name   VARCHAR(45) NOT NULL,
    price  INT         NOT NULL,
    img_url CLOB(10K)        NOT NULL,
    PRIMARY KEY (id)
)
