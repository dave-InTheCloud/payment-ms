INSERT INTO CUSTOMER (ID, NAME, EMAIL, DATE_OF_BIRTH, CREATED_ON, LAST_UPDATED_ON)
VALUES
    (NEXT VALUE FOR CUSTOMER_SEQ, 'GOMX-3', 'gomx@example.com', '2015-10-05', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (NEXT VALUE FOR CUSTOMER_SEQ, 'GOMX-4A (Ulloriaq)', 'ulloriaq@example.com', '2018-02-02', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (NEXT VALUE FOR CUSTOMER_SEQ, 'GRUS-1', 'grus@example.com', '2022-07-20', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (NEXT VALUE FOR CUSTOMER_SEQ, 'BRO-1', 'bro1@example.com', '2021-07-29', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (NEXT VALUE FOR CUSTOMER_SEQ, 'BRO-2', 'bro2@example.com', '2021-07-29', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (NEXT VALUE FOR CUSTOMER_SEQ, 'BRO-10', 'bro10@example.com', '2023-11-21', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (NEXT VALUE FOR CUSTOMER_SEQ, 'BRO-11', 'bro11@example.com', '2023-11-21', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (NEXT VALUE FOR CUSTOMER_SEQ, 'Observer-1A', 'Observer1A@example.com', '2023-11-11', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);