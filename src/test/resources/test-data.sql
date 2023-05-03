DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS product;

CREATE TABLE IF NOT EXISTS product
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    name       VARCHAR(255) NOT NULL,
    image_url  VARCHAR(500) NOT NULL,
    price      INT unsigned NOT NULL,
    created_at DATETIME     NOT NULL default current_timestamp,
    updated_at DATETIME     NOT NULL default current_timestamp on update current_timestamp,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS member
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    email      VARCHAR(100) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    created_at DATETIME     NOT NULL default current_timestamp,
    updated_at DATETIME     NOT NULL default current_timestamp on update current_timestamp,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS cart
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    product_id BIGINT       NOT NULL,
    member_id  VARCHAR(255) NOT NULL,
    created_at DATETIME     NOT NULL default current_timestamp,
    updated_at DATETIME     NOT NULL default current_timestamp on update current_timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (member_id) REFERENCES member (id)
    );
