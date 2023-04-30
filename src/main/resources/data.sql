CREATE TABLE IF NOT EXISTS product
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    name       VARCHAR(255) NOT NULL,
    image_url  VARCHAR(500) NOT NULL,
    price      INT unsigned NOT NULL,
    created_at DATETIME     NOT NULL default current_timestamp,
    updated_at DATETIME     NOT NULL default current_timestamp on update current_timestamp,
    PRIMARY KEY (id)
);
