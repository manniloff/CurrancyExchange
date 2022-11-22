CREATE TABLE cdn_cur_currency_rate
(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    label VARCHAR(10),
    rate DOUBLE,
    retrieved_date DATE
);

CREATE TABLE exchange_history
(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    change_from VARCHAR(10),
    changed_value DOUBLE(20,3),
    change_to  VARCHAR(10),
    retrieved_value DOUBLE(20,3),
    transaction_date TIMESTAMP(0)
);