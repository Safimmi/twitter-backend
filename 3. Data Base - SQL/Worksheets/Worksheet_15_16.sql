--15. Generar una consulta donde liste todas las tablas del sistema. 

-- User tables *
SELECT table_name, tablespace_name
FROM USER_TABLES
ORDER BY TABLE_NAME;

--All tables 
SELECT table_name, owner
FROM ALL_TABLES
ORDER BY owner, table_name;

-- Scheme Tables
SELECT table_name, owner
FROM ALL_TABLES
WHERE owner='CHALLENGE'
ORDER BY owner, table_name;

-- All Schemes Tables (DBA_TABLES: Data Dictionary)
SELECT table_name, owner
FROM DBA_TABLES
WHERE owner='CHALLENGE'
ORDER BY owner, table_name;


--16. Generar una consulta donde liste todos los atributos. 

-- Describe a table
DESC challenge;

--Static Data Dictionary Views
DESC SYS.ALL_TAB_COLUMNS;
DESC SYS.ALL_TABLES;
DESC DBA_TAB_COLUMNS;
DESC DBA_TABLES;


-- All tables 
SELECT *
FROM SYS.ALL_TAB_COLUMNS
ORDER BY owner, table_name;


-- All tables from an specific user/schema (SYS)
SELECT ATC.column_id, 
    ATC.owner,
    ATC.table_name, 
    ATC.column_name, 
    ATC.data_type, 
    ATC.data_length,
    AT.num_rows,
    ATC.data_precision, 
    ATC.data_scale, 
    ATC.nullable
FROM SYS.ALL_TAB_COLUMNS ATC
INNER JOIN SYS.ALL_TABLES AT 
    ON ATC.owner = AT.owner 
    AND ATC.table_name = AT.table_name
WHERE ATC.owner = 'CHALLENGE'
ORDER BY ATC.table_name, ATC.owner, ATC.column_id;

-- All tables (DBA) 
SELECT DTC.column_id,
    DTC.owner AS schema_name,
    DTC.table_name,
    DT.tablespace_name,
    DTC.column_name,
    DTC.data_type,
    DTC.data_length,
    DT.num_rows,
    DTC.data_precision,
    DTC.data_scale,
    DTC.nullable
FROM SYS.DBA_TAB_COLUMNS DTC
INNER JOIN SYS.DBA_TABLES DT
    ON DTC.owner = DT.owner
    AND DTC.table_name = DT.table_name
--WHERE DTC.owner = 'CHALLENGE'
ORDER BY  DTC.owner, DTC.table_name, DTC.column_id;


