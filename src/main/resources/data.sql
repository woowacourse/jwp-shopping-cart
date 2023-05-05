CREATE TABLE IF NOT EXISTS PRODUCT (
  product_id          BIGINT              NOT NULL   AUTO_INCREMENT,
  name                VARCHAR(500)        NOT NULL,
  image_url           TEXT                NOT NULL,
  price               DECIMAL             NOT NULL   CHECK (price >= 0),
  PRIMARY KEY (product_id)
);

CREATE TABLE IF NOT EXISTS MEMBER (
  member_id           BIGINT              NOT NULL   AUTO_INCREMENT,
  name                VARCHAR(500),
  email               VARCHAR(500)        NOT NULL,
  password            VARCHAR(500)        NOT NULL   CHECK (4 <= LENGTH(password) AND LENGTH(password) <= 16),
  PRIMARY KEY (member_id)
);

