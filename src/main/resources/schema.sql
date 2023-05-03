CREATE TABLE Product (
     id INT NOT NULL AUTO_INCREMENT,
     name VARCHAR(50) NOT NULL,
     price INT NOT NULL,
     image_url LONGTEXT NOT NULL,
     PRIMARY KEY (id)
);

CREATE TABLE Member (
    email VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    PRIMARY KEY (email)
);

CREATE TABLE Cart (
    id INT NOT NULL AUTO_INCREMENT,
    member_email VARCHAR(50) NOT NULL,
    product_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY(member_email) REFERENCES Member(email),
    FOREIGN KEY(product_id) REFERENCES Product(id)
);
