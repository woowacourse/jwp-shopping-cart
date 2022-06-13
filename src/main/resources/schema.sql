CREATE TABLE customer
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    nickname VARCHAR(8)   NOT NULL,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(60)  NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE product
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    name      VARCHAR(255) NOT NULL,
    price     INTEGER      NOT NULL,
    image_url VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE cart_item
(
    id          BIGINT  NOT NULL AUTO_INCREMENT,
    customer_id BIGINT  NOT NULL,
    product_id  BIGINT  NOT NULL,
    quantity    INTEGER NOT NULL DEFAULT '1',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

ALTER TABLE cart_item
    ADD CONSTRAINT fk_cart_item_to_customer
        FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE cart_item
    ADD CONSTRAINT fk_cart_item_to_product
        FOREIGN KEY (product_id) REFERENCES product (id);

create table orders
(
    id          bigint not null auto_increment,
    customer_id bigint not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table orders
    add constraint fk_orders_to_customer
        foreign key (customer_id) references customer (id);

create table orders_detail
(
    id         bigint  not null auto_increment,
    orders_id  bigint  not null,
    product_id bigint  not null,
    quantity   integer not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table orders_detail
    add constraint fk_orders_detail_to_orders
        foreign key (orders_id) references orders (id);

alter table orders_detail
    add constraint fk_orders_detail_to_product
        foreign key (product_id) references product (id);
