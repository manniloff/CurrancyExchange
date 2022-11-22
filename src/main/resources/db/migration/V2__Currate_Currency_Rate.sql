CREATE TABLE currate_currency_rate
(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    label VARCHAR(10),
    rate DOUBLE,
    retrieved_date DATE
);