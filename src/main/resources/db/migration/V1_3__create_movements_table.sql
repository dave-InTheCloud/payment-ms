CREATE TABLE IF NOT EXISTS `MOVEMENT` (
    `ID` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `USER_ID`  int NOT NULL,
    `FROM_ACCOUNT_ID` int NOT NULL,
    `TO_ACCOUNT_ID` int NOT NULL,
    `AMOUNT` VARCHAR(30) NOT NULL CHECK (AMOUNT > 0),
    `AMOUNT_TYPE` CHAR(1) NOT NULL,
    `STATUS` VARCHAR(30) NOT NULL,
    `CREATED_ON` timestamp,
    `LAST_UPDATED_ON` timestamp,
     FOREIGN KEY (`USER_ID`) REFERENCES `CUSTOMER` (`ID`),
     FOREIGN KEY (`FROM_ACCOUNT_ID`) REFERENCES `ACCOUNT` (`ID`),
     FOREIGN KEY (`TO_ACCOUNT_ID`) REFERENCES `ACCOUNT` (`ID`)
);

/*
Not using hibernate auto_ddl
*/
CREATE SEQUENCE MOVEMENT_SEQ START WITH 1 INCREMENT BY 1;