CREATE TABLE IF NOT EXISTS PRODUCT
(
    id            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name          VARCHAR(255)    NOT NULL,
    image         VARCHAR(2083),
    price         INT UNSIGNED    NOT NULL,
    created_at    DATETIME        NOT NULL default current_timestamp,
    modified_date DATETIME(6)     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
)

