CREATE TABLE PRODUCT
(
    id          INT          NOT NULL AUTO_INCREMENT,
    name        VARCHAR(50)  NOT NULL,
    image_url   VARCHAR(255) NOT NULL,
    price       INT          NOT NULL,
    description TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE CATEGORY
(
    id   INT         NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE PRODUCT_CATEGORY
(
    product_id  INT NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY (product_id, category_id),
    FOREIGN KEY (product_id) REFERENCES PRODUCT (id),
    FOREIGN KEY (category_id) REFERENCES CATEGORY (id)
);
