CREATE TABLE Product (
     id BIGINT NOT NULL AUTO_INCREMENT,
     name VARCHAR(100) NOT NULL,
     price INT NOT NULL,
     image_url LONGTEXT NOT NULL,
     PRIMARY KEY (id)
);

CREATE TABLE Member (
    email VARCHAR(30) UNIQUE NOT NULL,
    password VARCHAR(20) NOT NULL,
    PRIMARY KEY (email)
);

CREATE TABLE Cart (
    id BIGINT AUTO_INCREMENT,
    member_email VARCHAR(30) NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT `cart_ibfk_1` FOREIGN KEY (member_email) REFERENCES Member (email) ON DELETE CASCADE,
    CONSTRAINT `cart_ibfk_2` FOREIGN KEY (product_id) REFERENCES Product (id) ON DELETE CASCADE
);
