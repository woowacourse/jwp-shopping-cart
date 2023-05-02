CREATE TABLE product
(
    id bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    image text NOT NULL,
    price int NOT NULL
);

CREATE TABLE member
(
    id bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL
);

CREATE TABLE cart_item
(
    id bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    member_id bigint NOT NULL,
    product_id bigint NOT NULL
);

ALTER TABLE cart_item ADD CONSTRAINT fk_member_id FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE CASCADE;
ALTER TABLE cart_item ADD CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE;
