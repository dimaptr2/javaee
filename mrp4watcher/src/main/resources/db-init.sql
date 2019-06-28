CREATE TABLE IF NOT EXISTS plant
(
    id   VARCHAR(4) NOT NULL PRIMARY KEY,
    name VARCHAR(50)
);
CREATE TABLE IF NOT EXISTS pur_group
(
    id   VARCHAR(3) NOT NULL PRIMARY KEY,
    name VARCHAR(50)
);
CREATE TABLE IF NOT EXISTS period
(
    id   INT(2) NOT NULL PRIMARY KEY,
    name VARCHAR(10)
);
CREATE TABLE IF NOT EXISTS currency
(
    id   VARCHAR(3) NOT NULL PRIMARY KEY,
    name VARCHAR(50)
);
CREATE TABLE IF NOT EXISTS measure
(
    id   VARCHAR(3) NOT NULL PRIMARY KEY,
    name VARCHAR(50)
);
CREATE TABLE IF NOT EXISTS warehouse
(
    id VARCHAR(4) NOT NULL,
    plant_id VARCHAR(4) NOT NULL,
    name VARCHAR(50),
    CONSTRAINT `plant_key_00` FOREIGN KEY (plant_id) REFERENCES plant (id),
    PRIMARY KEY (id, plant_id)
);
CREATE TABLE IF NOT EXISTS material
(
    id          BIGINT NOT NULL PRIMARY KEY,
    description VARCHAR(50)
);
CREATE TABLE IF NOT EXISTS matunit
(
    id       BIGINT     NOT NULL,
    plant_id VARCHAR(4) NOT NULL,
    uom      VARCHAR(3) NOT NULL,
    rate     DECIMAL(20, 3),
    CONSTRAINT `mat_key_01` FOREIGN KEY (id) REFERENCES material (id),
    CONSTRAINT `plant_key_01` FOREIGN KEY (plant_id) REFERENCES plant (id),
    CONSTRAINT `measure_key_01` FOREIGN KEY (uom) REFERENCES measure (id),
    PRIMARY KEY (id, plant_id, uom)
);
CREATE TABLE IF NOT EXISTS matprice
(
    id          BIGINT     NOT NULL,
    plant_id    VARCHAR(4) NOT NULL,
    currency_id VARCHAR(3) NOT NULL,
    price       DECIMAL(20, 2),
    CONSTRAINT `mat_key_02` FOREIGN KEY (id) REFERENCES material (id),
    CONSTRAINT `plant_key_02` FOREIGN KEY (plant_id) REFERENCES plant (id),
    CONSTRAINT `currency_key_01` FOREIGN KEY (currency_id) REFERENCES currency (id),
    PRIMARY KEY (id, plant_id, currency_id)
);
CREATE TABLE IF NOT EXISTS mat_tax
(
    id          BIGINT     NOT NULL,
    plant_id    VARCHAR(4) NOT NULL,
    currency_id VARCHAR(3) NOT NULL,
    tax_code    INT(2),
    tax_rate    DECIMAL(20, 2),
    CONSTRAINT `mat_key_03` FOREIGN KEY (id) REFERENCES material (id),
    CONSTRAINT `plant_key_03` FOREIGN KEY (plant_id) REFERENCES plant (id),
    CONSTRAINT `currency_key_02` FOREIGN KEY (currency_id) REFERENCES currency (id),
    PRIMARY KEY (id, plant_id, currency_id, tax_code)
);
CREATE TABLE IF NOT EXISTS delivery_type
(
    id VARCHAR(4) NOT NULL,
    plant_id VARCHAR(4) NOT NULL,
    description VARCHAR(50),
    CONSTRAINT `plant_key_04` FOREIGN KEY (plant_id) REFERENCES plant (id),
    PRIMARY KEY (id, plant_id)
);
CREATE TABLE IF NOT EXISTS moving_type
(
    id VARCHAR(3) NOT NULL,
    plant_id VARCHAR(4) NOT NULL,
    description VARCHAR(50),
    CONSTRAINT `plant_key_05` FOREIGN KEY (plant_id) REFERENCES plant (id),
    PRIMARY KEY (id, plant_id)
);