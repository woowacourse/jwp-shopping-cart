CREATE TABLE IF NOT EXISTS product
(
    id     INT          NOT NULL AUTO_INCREMENT,
    name   VARCHAR(255) NOT NULL,
    price  INT          NOT NULL,
    imgUrl TEXT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS member
(
    id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
