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
  email               VARCHAR(500)        NOT NULL   UNIQUE ,
  password            VARCHAR(500)        NOT NULL   CHECK (4 <= LENGTH(password) AND LENGTH(password) <= 16),
  PRIMARY KEY (member_id)
);

CREATE TABLE IF NOT EXISTS CART_ITEM (
  member_id           BIGINT              NOT NULL   REFERENCES MEMBER(member_id) ON DELETE CASCADE,
  product_id          BIGINT              NOT NULL   REFERENCES PRODUCT(product_id) ON DELETE CASCADE,
  amount              INT                 NOT NULL,
  PRIMARY KEY(member_id, product_id)
);
