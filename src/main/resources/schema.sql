CREATE TABLE IF NOT EXISTS PRODUCT (
    id          BIGINT                            NOT NULL AUTO_INCREMENT,
    name        VARCHAR(50)                       NOT NULL,
    image_url   VARCHAR(200)                      NOT NULL,
    price       INT                               NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS MEMBER (
    id           BIGINT                           NOT NULL AUTO_INCREMENT,
    email        VARCHAR(50)                      NOT NULL,
    name         VARCHAR(50)                      NOT NULL,
    password     VARCHAR(100)                     NOT NULL,
    PRIMARY KEY  (id),
    UNIQUE KEY   member_email(email)
);

CREATE TABLE IF NOT EXISTS CART (
    id           BIGINT                           NOT NULL AUTO_INCREMENT,
    member_id    BIGINT                           NOT NULL,
    product_id   BIGINT                           NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id)  REFERENCES MEMBER  (id)      ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (product_id) REFERENCES PRODUCT (id)      ON DELETE CASCADE ON UPDATE CASCADE
);
