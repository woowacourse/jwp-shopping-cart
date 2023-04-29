CREATE TABLE product
(
    id    INT           NOT NULL AUTO_INCREMENT,
    name  VARCHAR(50)   NOT NULL,
    price INT           NOT NULL,
    image VARCHAR(2000) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users
(
    id       INT          NOT NULL AUTO_INCREMENT,
    email    VARCHAR(320) NOT NULL,
    password VARCHAR(20)  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE cart
(
    id         INT NOT NULL AUTO_INCREMENT,
    user_id    INT NOT NULL,
    product_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE
);
