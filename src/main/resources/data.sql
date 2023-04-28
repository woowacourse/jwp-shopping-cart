CREATE TABLE IF NOT EXISTS ITEMS
(
    item_id     BIGINT    AUTO_INCREMENT    NOT NULL,
    name        VARCHAR(255)                NOT NULL,
    image_url   CLOB                        NOT NULL,
    price       INT                         NOT NULL,

    PRIMARY KEY (item_id)
);

CREATE TABLE IF NOT EXISTS USERS
(
    user_id     BIGINT    AUTO_INCREMENT    NOT NULL,
    email       VARCHAR(255)                NOT NULL,
    password    VARCHAR(255)                NOT NULL,

    PRIMARY KEY (user_id)
);

INSERT INTO ITEMS(name, image_url, price) VALUES ('자전거1', 'https://www.altonsports.com/prdimg/get/21-INNOZEN24_P_01%281060X600%29.jpg', 10000);
INSERT INTO ITEMS(name, image_url, price) VALUES ('자전거2', 'https://cdn.imweb.me/thumbnail/20220817/7b35b82e7c1ce.jpg', 50000);
INSERT INTO ITEMS(name, image_url, price) VALUES ('자전거3', 'https://thumbnail8.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2022/01/25/11/8/3294fc8a-92a5-410b-b3bb-fb3c7e18a1d9.jpg', 100000);

INSERT INTO USERS(email, password) VALUES ('a@a.com', 'a');
INSERT INTO USERS(email, password) VALUES ('b@b.com', 'b');
