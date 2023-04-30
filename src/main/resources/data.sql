CREATE TABLE IF NOT EXISTS products
(
id      BIGINT      NOT NULL AUTO_INCREMENT,
name    VARCHAR(10) NOT NULL,
price   DOUBLE      NOT NULL,
image   TEXT        NOT NULL,
created_at timestamp not null default current_timestamp,
updated_at timestamp not null default current_timestamp on update current_timestamp,

PRIMARY KEY(id)
);
