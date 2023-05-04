CREATE TABLE IF NOT EXISTS product (
    id BIGINT NOT NULL UNIQUE AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS member (
    id BIGINT NOT NULL UNIQUE AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    primary key (id)
);

CREATE TABLE IF NOT EXISTS cart (
    id BIGINT NOT NULL AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    count INT NOT NULL,
    primary key (id),
    CONSTRAINT fk_member_id FOREIGN KEY (member_id) references member(id) ON DELETE CASCADE,
    CONSTRAINT fk_product_id FOREIGN KEY (product_id) references product(id) ON DELETE CASCADE
);
