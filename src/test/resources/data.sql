CREATE TABLE IF NOT EXISTS ITEMS
(
    id        BIGINT      NOT NULL AUTO_INCREMENT,
    name      VARCHAR(50) NOT NULL,
    image_url VARCHAR(5000),
    price     INT         NOT NULL,
    PRIMARY KEY (id)
);
