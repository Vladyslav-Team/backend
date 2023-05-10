CREATE TABLE orders
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    sponsor_id  BIGINT,
    order_id    VARCHAR(255),
    status      VARCHAR(255),
    create_date date,
    update_date date,
    activation  VARCHAR(30),
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_SPONSOR FOREIGN KEY (sponsor_id) REFERENCES sponsor (sponsor_id) ON DELETE CASCADE;

CREATE DOMAIN OrderStatus AS VARCHAR(30) CHECK (VALUE IN ('PAYER_ACTION_REQUIRED', 'SUCCESS', 'USED'));
