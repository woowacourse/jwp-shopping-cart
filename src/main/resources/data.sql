CREATE TABLE IF NOT EXISTS PRODUCT (
    id          BIGINT UNSIGNED     NOT NULL AUTO_INCREMENT,
    name        VARCHAR(50)         NOT NULL,
    url         VARCHAR        NOT NULL,
    price       INT UNSIGNED        NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS MEMBER(
    id          BIGINT UNSIGNED     NOT NULL AUTO_INCREMENT,
    email       VARCHAR(50)         NOT NULL,
    password    VARCHAR(50)         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS CART(
    id          BIGINT UNSIGNED     NOT NULL AUTO_INCREMENT,
    member_id   BIGINT UNSIGNED     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES MEMBER (id)
);

CREATE TABLE IF NOT EXISTS CART_PRODUCT(
    id              BIGINT UNSIGNED     NOT NULL AUTO_INCREMENT,
    product_id      BIGINT UNSIGNED     NOT NULL,
    cart_id         BIGINT UNSIGNED     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES PRODUCT (id),
    FOREIGN KEY (cart_id) REFERENCES CART (id)
);

INSERT INTO PRODUCT (name, url, price)
values ('강아지', 'https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzA0MTJfMTM5%2FMDAxNjgxMjYzNTU2NDI2.wlJys88BgEe2MzQrd2k5jjtXsObAZaOM4eidDcM3iLUg.5eE5nUvqLadE0MwlF9c8XLOgqghimMWQU2psfcRuvFYg.PNG.noblecase%2F20230412_102917_5.png&type=a340', 10000);
INSERT INTO PRODUCT (name, url, price)
values ('고양이', 'https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzA0MTBfMjcz%2FMDAxNjgxMTAwOTc5Nzg3.MEOt2vmlKWIlW4PQFfgHPILk0dJxwX42KzrDVu4puSwg.GcSSR6FJWup8Uo1H0xo0_4FuIMhJYJpw6tUmpKP9-Wsg.JPEG.catopia9%2FDSC01276.JPG&type=a340', 20000);
INSERT INTO PRODUCT (name, url, price)
values ('햄스터', 'https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzA0MjJfMTcx%2FMDAxNjgyMTMzNzQ5MjQ2.DPd6D6NUbKSAOLBVosis9Ptz_lBGkyT4lncgLV0buZUg.KK0-N7fzYAy43jlHd9-4hQJ2CYu7RRqV3UWUi29FQJgg.JPEG.smkh15112%2FIMG_4554.JPG&type=a340', 5000);

INSERT INTO MEMBER (email, password) values ('coding_judith@gmail.com', 'judy123');
INSERT INTO MEMBER (email, password) values ('coding_teo@gmail.com', 'teo123');

INSERT INTO CART (member_id) values (1);
INSERT INTO CART (member_id) values (2);

INSERT INTO CART_PRODUCT (product_id, cart_id) values (1, 1);
INSERT INTO CART_PRODUCT (product_id, cart_id) values (2, 2);
