DROP table IF EXISTS PUBLIC.statistic cascade;
CREATE TABLE IF NOT EXISTS PUBLIC.statistic (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    app varchar(300),
    uri varchar(300),
    ip varchar(15),
    created TIMESTAMP WITHOUT TIME ZONE)