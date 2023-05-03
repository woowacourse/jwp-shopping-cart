DROP TABLE IF EXISTS member CASCADE;
DROP TABLE IF EXISTS product CASCADE;
DROP TABLE IF EXISTS cart CASCADE;

CREATE TABLE product
(
    id        BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(50) NOT NULL,
    price     INT         NOT NULL,
    image_url TEXT        NOT NULL
);

CREATE TABLE member
(
    id       INT AUTO_INCREMENT
        PRIMARY KEY,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(15)  NOT NULL
);

CREATE TABLE cart
(
    id         INT AUTO_INCREMENT
        PRIMARY KEY,
    product_id INT NOT NULL,
    member_id  INT NOT NULL,
    CONSTRAINT member_id
        foreign key (member_id) references member (id),
    constraint product_id
        foreign key (product_id) references product (id)
);
