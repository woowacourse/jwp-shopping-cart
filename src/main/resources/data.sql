CREATE TABLE IF NOT EXISTS products
(
id      BIGINT      NOT NULL AUTO_INCREMENT,
name    VARCHAR(10) NOT NULL,
price   DOUBLE      NOT NULL,
image   TEXT        NOT NULL,
PRIMARY KEY(id)
);
