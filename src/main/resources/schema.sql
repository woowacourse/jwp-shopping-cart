CREATE TABLE IF NOT EXISTS products
(
    id        BIGINT        NOT NULL AUTO_INCREMENT,
    name      VARCHAR(30)   NOT NULL,
    image_url VARCHAR(1000) NOT NULL,
    price     INT           NOT NULL,
    PRIMARY KEY (id)
);
