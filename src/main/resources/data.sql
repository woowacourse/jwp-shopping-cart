CREATE TABLE Product (
     id INT NOT NULL AUTO_INCREMENT,
     name VARCHAR(50) NOT NULL,
     price INT NOT NULL,
     image_url LONGTEXT NOT NULL,
     PRIMARY KEY (id)
);

CREATE TABLE Member (
    id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Cart (
    member_id INT NOT NULL,
    product_id INT NOT NULL,
    FOREIGN KEY(member_id) REFERENCES Member(id),
    FOREIGN KEY(product_id) REFERENCES Product(id)
);
