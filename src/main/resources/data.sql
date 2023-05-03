-- TODO: 기능 구현에 필요한 내용을 추가하거나 수정하세요.
CREATE TABLE IF NOT EXISTS PRODUCT (
    id bigint NOT NULL AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL,
    price int NOT NULL,
    image_url VARCHAR NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS MEMBER (
    id bigint NOT NULL AUTO_INCREMENT,
    email VARCHAR(32) NOT NULL,
    password VARCHAR(64) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE CART(
    id BIGINT NOT NULL AUTO_INCREMENT,
    member_id VARCHAR(255) NOT NULL,
    product_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES MEMBER(id),
    FOREIGN KEY (product_id) REFERENCES PRODUCT(id)
);

INSERT INTO PRODUCT(name, price, image_url) values('맨유', 9999909, 'man united');

INSERT INTO MEMBER(email, password) values('a@a.com', 'password1');
INSERT INTO MEMBER(email, password) values('b@b.com', 'password2');
