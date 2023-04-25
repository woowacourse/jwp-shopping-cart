CREATE TABLE PRODUCT (
     id          INT         NOT NULL AUTO_INCREMENT,
     name       INT         NOT NULL,
     image      TEXT(500)     NOT NULL,
     price      INT         NOT NULL,
     created_at  DATETIME    NOT NULL default current_timestamp,
     PRIMARY KEY (id)
);
