CREATE TABLE IF NOT EXISTS `ACCOUNT` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `NAME` varchar(30)  NOT NULL,
    `SERIAL_NUMBER` varchar(50)  NOT NULL UNIQUE,
    `ACCOUNT_TYPE` varchar(10)  NOT NULL,
    `OWNER_ID` BIGINT NOT NULL CHECK (OWNER_ID > 0),
    `CURRENCY_CODE` CHAR(3) NOT NULL,
    `BALANCE` DECIMAL(18,2) NOT NULL CHECK (BALANCE >= 0),
    `CREATED_ON` timestamp,
    `LAST_UPDATED_ON` timestamp,
     FOREIGN KEY (OWNER_ID) REFERENCES CUSTOMER(ID)
);

ALTER TABLE ACCOUNT ADD CONSTRAINT unique_account_serialNumber UNIQUE (SERIAL_NUMBER);

/*
Not using hibernate auto_ddl
*/
CREATE SEQUENCE ACCOUNT_SEQ START WITH 1 INCREMENT BY 1;