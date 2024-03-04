CREATE TABLE IF NOT EXISTS `MOVEMENT` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `ACCOUNT_ID` BIGINT NOT NULL,
    `AMOUNT` DECIMAL(18,2) NOT NULL CHECK (AMOUNT >= 0),
    `MOVEMENT_TYPE` CHAR(6) NOT NULL,
    `STATUS` VARCHAR(10) NOT NULL,
    `CREATED_ON` timestamp,
    `LAST_UPDATED_ON` timestamp,
     FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `ACCOUNT` (`ID`)
);

/*
Not using hibernate auto_ddl
*/
CREATE SEQUENCE MOVEMENT_SEQ START WITH 1 INCREMENT BY 1;