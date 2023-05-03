CREATE TABLE IF NOT EXISTS product
(
    id    INT AUTO_INCREMENT,
    name  VARCHAR(20) NOT NULL UNIQUE,
    image VARCHAR     NOT NULL,
    price INT         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS member
(
    id       INT AUTO_INCREMENT,
    email    VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
);
