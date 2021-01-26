drop table IF EXISTS product;

create TABLE product
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(255) NOT NULL,
    price        DOUBLE       NOT NULL,
    create_date  TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    update_date  TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    delete       TINYINT(1)   NOT NULL DEFAULT 0
);