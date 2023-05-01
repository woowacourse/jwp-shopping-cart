CREATE TABLE MEMBER (
    member_id BIGINT NOT NULL AUTO_INCREMENT,
    nickname VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (member_id)
);

CREATE TABLE PRODUCT (
     product_id BIGINT NOT NULL AUTO_INCREMENT,
     name VARCHAR(50) NOT NULL,
     img_url VARCHAR(255) NOT NULL,
     price INT NOT NULL,
     PRIMARY KEY (product_id)
);

CREATE TABLE CART (
      cart_id BIGINT	NOT NULL AUTO_INCREMENT,
      member_id	BIGINT	NOT NULL,
      product_id BIGINT NOT NULL,
      PRIMARY KEY (cart_id),
      FOREIGN KEY (member_id) REFERENCES MEMBER (member_id),
      FOREIGN KEY (product_id) REFERENCES PRODUCT (product_id)
);
