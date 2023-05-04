CREATE TABLE product
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    name      VARCHAR(255) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    price     INT          NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE member
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE cart
(
    id         BIGINT NOT NULL AUTO_INCREMENT,
    member_id  BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) references member (id),
    FOREIGN KEY (product_id) references product (id)
);