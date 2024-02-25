CREATE TABLE IF NOT EXISTS `CURRENCY` (
    `ID` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `USER_ID`  int NOT NULL,
    `CODE` int NOT NULL,
    `DESCRIPTION` VARCHAR(30) NOT NULL,
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;


-- Assuming currency_id is in a currencies table and referenced in a transactions table
ALTER TABLE transactions
ADD FOREIGN KEY (CURRENCY_CODE) REFERENCES CURRENCY(id);
