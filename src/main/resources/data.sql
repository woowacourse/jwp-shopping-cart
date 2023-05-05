CREATE TABLE IF NOT EXISTS PRODUCT (
  product_id          BIGINT UNSIGNED       NOT NULL   AUTO_INCREMENT,
  name                VARCHAR(500)          NOT NULL,
  image_url           TEXT                  NOT NULL,
  price               DECIMAL UNSIGNED      NOT NULL,
  PRIMARY KEY (product_id)
);
