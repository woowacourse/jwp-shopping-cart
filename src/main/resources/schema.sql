CREATE TABLE IF NOT EXISTS products
(
id      BIGINT      NOT NULL AUTO_INCREMENT,
name    VARCHAR(10) NOT NULL,
price   DOUBLE      NOT NULL,
image   TEXT        NOT NULL,

PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS members
(
id          BIGINT      NOT NULL AUTO_INCREMENT,
name        VARCHAR(10) NOT NULL,
email       VARCHAR(30) NOT NULL,
password    VARCHAR(16) NOT NULL,

PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS carts
(
id          BIGINT NOT NULL AUTO_INCREMENT,
memberId    BIGINT NOT NULL,
productId   BIGINT NOT NULL,

PRIMARY KEY(id),
FOREIGN KEY(memberId) REFERENCES members(id) ON DELETE CASCADE,
FOREIGN KEY(productId) REFERENCES products(id) ON DELETE CASCADE
);
