CREATE TABLE IF NOT EXISTS `CUSTOMER` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `NAME` varchar(30)  NOT NULL,
    `EMAIL` varchar(50)  NOT NULL,
    `DATE_OF_BIRTH` timestamp  NOT NULL,
    `CREATED_ON` timestamp  NOT NULL,
    `LAST_UPDATED_ON` timestamp  NOT NULL
);

ALTER TABLE CUSTOMER ADD CONSTRAINT unique_customer_email UNIQUE (EMAIL);

/*
Not using hibernate auto_ddl
*/
CREATE SEQUENCE CUSTOMER_SEQ START WITH 1 INCREMENT BY 1;
