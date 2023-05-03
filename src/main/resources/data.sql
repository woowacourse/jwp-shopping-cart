CREATE TABLE IF NOT EXISTS products
(
id      BIGINT      NOT NULL AUTO_INCREMENT,
name    VARCHAR(10) NOT NULL,
price   DOUBLE      NOT NULL,
image   TEXT        NOT NULL,
created_at timestamp NOT NULL default current_timestamp,
updated_at timestamp NOT NULL default current_timestamp on update current_timestamp,

PRIMARY KEY(id)
);
CREATE TABLE IF NOT EXISTS members
(
id          BIGINT      NOT NULL AUTO_INCREMENT,
name        VARCHAR(10) NOT NULL,
email       VARCHAR(30) NOT NULL,
password    VARCHAR(16) NOT NULL,

PRIMARY KEY(id)
)
