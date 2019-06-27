### Table's definitions

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
    tax_code    INT(2)     NOT NULL,
    tax_rate    DECIMAL(20, 2),
    CONSTRAINT `mat_key_03` FOREIGN KEY (id) REFERENCES material (id),
    CONSTRAINT `plant_key_03` FOREIGN KEY (plant_id) REFERENCES plant (id),
    CONSTRAINT `currency_key_02` FOREIGN KEY (currency_id) REFERENCES currency (id),
    PRIMARY KEY (id, plant_id, currency_id, tax_code)
);

### View's section

CREATE VIEW IF NOT EXISTS vmaterial
AS
SELECT m.id,
       m.description,
       mu.plant_id,
       mu.rate AS mat_unit,
       mu.uom,
       mp.price,
       curr.id   AS curr_id,
       curr.name AS curr_name,
       mt.tax_code,
       mt.tax_rate
FROM material m
         INNER JOIN matunit mu ON m.id = mu.id
         INNER JOIN matprice mp ON (m.id = mp.id AND mu.plant_id = mp.plant_id)
         INNER JOIN currency curr ON mp.currency_id = curr.id
         INNER JOIN mat_tax mt ON (m.id = mt.id AND mp.plant_id = mt.plant_id AND mp.currency_id = mt.currency_id)
ORDER BY m.id;