-- 5. Crear una tabla llamada logo. 
DROP TABLE logo;
CREATE TABLE challenge.logo (
    "ref" NUMBER,
    "description" varchar2(30 CHAR),
    file_dir BLOB DEFAULT EMPTY_BLOB(),
    
    CONSTRAINT logo PRIMARY KEY ("ref")
) TABLESPACE challenge; 

-- 6. Guardar el logo de Endava en la tabla logo. 

--Drop if previous directory
GRANT DROP ANY DIRECTORY TO challenge;
DROP DIRECTORY img_dir;

--Create Directory
GRANT CREATE ANY DIRECTORY TO challenge;
CREATE DIRECTORY img_dir as 'D:\Endava\Challenge\3. Data Base - SQL';

-- Grant Permission from System to the user
GRANT READ, WRITE ON DIRECTORY img_dir TO challenge; 

--Option 1
INSERT INTO challenge.logo VALUES
    (1, 'No image', EMPTY_BLOB());
INSERT INTO challenge.logo VALUES
    (2, 'Endava`s Logo', BFILENAME('IMG_DIR','EndavaLogo.jpg'));

-- Option 2
DECLARE
    V_TEMP BLOB;
    V_NAME VARCHAR2(20);
    V_BFILE BFILE;
    
BEGIN 
    
    --INSERT
    INSERT INTO challenge.logo VALUES
    (3, 'Endavas Logo 2', EMPTY_BLOB()) Returning FILE_DIR INTO V_TEMP;
    
    V_NAME := 'EndavaLogo.jpg';
    V_BFILE := BFILENAME('IMG_DIR', V_NAME);
    
    DBMS_LOB.OPEN(V_BFILE, DBMS_LOB.LOB_READONLY);
    DBMS_LOB.LOADFROMFILE(V_TEMP, V_BFILE, DBMS_LOB.GETLENGTH(V_BFILE));
    DBMS_LOB.CLOSE(V_BFILE);
    COMMIT;
    
END;