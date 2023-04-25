CREATE TABLE product (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    img_url VARCHAR(255),
    price INT NOT NULL,
    PRIMARY KEY (id);
);