CREATE TABLE IF NOT EXISTS PRODUCT (
  product_id          BIGINT            NOT NULL   AUTO_INCREMENT,
  name        VARCHAR(500)      NOT NULL,
  image_url   TEXT              NOT NULL,
  price       DECIMAL           NOT NULL,
  PRIMARY KEY (product_id)
);
