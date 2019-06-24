# General tables
CREATE TABLE IF NOT EXISTS companies
(
    id   VARCHAR(4) NOT NULL PRIMARY KEY,
    name VARCHAR(50)
);
# Financial periods
CREATE TABLE IF NOT EXISTS periods
(
    id   INT(2) NOT NULL PRIMARY KEY,
    name VARCHAR(10)
);
# Currency
CREATE TABLE IF NOT EXISTS currencies
(
    id   VARCHAR(3) NOT NULL PRIMARY KEY,
    name VARCHAR(50)
);
CREATE TABLE IF NOT EXISTS curr_rates
(
    from_curr VARCHAR(3) NOT NULL,
    to_curr   VARCHAR(3) NOT NULL,
    rate      DECIMAL(20, 2),
    CONSTRAINT `curr_key_01` FOREIGN KEY (from_curr) REFERENCES currencies (id),
    CONSTRAINT `curr_key_02` FOREIGN KEY (to_curr) REFERENCES currencies (id),
    PRIMARY KEY (from_curr, to_curr)
);
# Units of measure
CREATE TABLE IF NOT EXISTS measures
(
    id   VARCHAR(3) NOT NULL PRIMARY KEY,
    name VARCHAR(50)
);
# Coefficients of uoms
CREATE TABLE IF NOT EXISTS measure_rates
(
    from_uom VARCHAR(3) NOT NULL,
    to_uom   VARCHAR(3) NOT NULL,
    rate     DECIMAL(20, 3),
    CONSTRAINT `units_key_01` FOREIGN KEY (from_uom) REFERENCES measures (id),
    CONSTRAINT `units_key_02` FOREIGN KEY (to_uom) REFERENCES measures (id),
    PRIMARY KEY (from_uom, to_uom)
);
# accounts and VAT codes
CREATE TABLE IF NOT EXISTS vat_codes
(
    id          INT PRIMARY KEY,
    description VARCHAR(50),
    rate        DECIMAL(20, 2)
);
CREATE TABLE IF NOT EXISTS account_groups
(
    id          VARCHAR(20) NOT NULL PRIMARY KEY,
    description VARCHAR(50)
);
CREATE TABLE IF NOT EXISTS chart_accounts
(
    id          VARCHAR(4) NOT NULL,
    description VARCHAR(50)
);
CREATE TABLE IF NOT EXISTS account_types
(
    id   VARCHAR(3) NOT NULL PRIMARY KEY,
    name VARCHAR(50)
);
CREATE TABLE IF NOT EXISTS accounts
(
    id           VARCHAR(20) NOT NULL PRIMARY KEY,
    account_type VARCHAR(3)  NOT NULL,
    description  VARCHAR(50),
    CONSTRAINT `acc_type_01` FOREIGN KEY (account_type) REFERENCES account_types (id)
);
# assign accounts to account groups
CREATE TABLE IF NOT EXISTS account_ass
(
    account_id VARCHAR(20) NOT NULL,
    group_id   VARCHAR(20) NOT NULL,
    CONSTRAINT `account_key_01` FOREIGN KEY (account_id) REFERENCES accounts (id),
    CONSTRAINT `acc_group_01` FOREIGN KEY (group_id) REFERENCES account_groups (id),
    PRIMARY KEY (account_id, group_id)
);
# materials
CREATE TABLE IF NOT EXISTS materials
(
    id          BIGINT     NOT NULL,
    company_id  VARCHAR(4) NOT NULL,
    description VARCHAR(50),
    CONSTRAINT `company_key_01` FOREIGN KEY (company_id) REFERENCES companies (id),
    PRIMARY KEY (id, company_id)
);
# material prices
CREATE TABLE IF NOT EXISTS matprices
(
    id          BIGINT     NOT NULL,
    fyear       INT(4)     NOT NULL,
    period_id   INT(2)     NOT NULL,
    currency_id VARCHAR(3) NOT NULL,
    CONSTRAINT `period_key_01` FOREIGN KEY (period_id) REFERENCES periods (id),
    CONSTRAINT `curr_key_03` FOREIGN KEY (currency_id) REFERENCES currencies (id),
    price       DECIMAL(20, 3),
    PRIMARY KEY (id, fyear, period_id, currency_id)
);
# material units
CREATE TABLE IF NOT EXISTS matunits
(
    id         BIGINT     NOT NULL,
    company_id VARCHAR(4) NOT NULL,
    uom_id     VARCHAR(3) NOT NULL,
    rate       DECIMAL(20, 3),
    CONSTRAINT `company_key_02` FOREIGN KEY (company_id) REFERENCES companies (id),
    CONSTRAINT `units_key_03` FOREIGN KEY (uom_id) REFERENCES measures (id),
    PRIMARY KEY (id, company_id, uom_id)
);