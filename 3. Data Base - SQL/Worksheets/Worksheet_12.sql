-- 12.  Crear un stored procedure llamado calculus que calcule la media, mediana, moda, mínimo y máximo para el atributo ccnumber.

-- Query
SELECT 
    --SUM(challenge.ccnumber) / SIZE(challenge.ccnumber) MEDIA
    AVG(challenge.ccnumber) MEDIA_AVG,
    MEDIAN(challenge.ccnumber) MEDIANA,
    STATS_MODE(challenge.ccnumber) MODA,
    MIN(challenge.ccnumber) MINIMO,
    MAX(challenge.ccnumber) MAXIMO
FROM challenge;

-- Stored Procedure : DBMS Output
CREATE OR REPLACE PROCEDURE calculus_dbms

IS
    v_media NUMBER;
    v_mediana NUMBER;
    v_moda NUMBER;
    v_minimo NUMBER;
    v_maximo NUMBER;
BEGIN
    
    SELECT 
        AVG(challenge.ccnumber) MEDIA,
        MEDIAN(challenge.ccnumber) MEDIANA,
        STATS_MODE(challenge.ccnumber) MODA,
        MIN(challenge.ccnumber) MINIMO,
        MAX(challenge.ccnumber) MAXIMO
    INTO
        v_media,
        v_mediana,
        v_moda,
        v_minimo,
        v_maximo
    FROM challenge;
    
    dbms_output.put_line( 'MEDIA : ' || v_media );
    dbms_output.put_line('MEDIANA : ' || v_mediana);
    dbms_output.put_line('MODA : ' || v_moda);
    dbms_output.put_line('MINIMO : ' || v_minimo);
    dbms_output.put_line('MAXIMO : ' || v_maximo);
    
END;


EXECUTE calculus_dbms();


-- Stored Procedure : REFCURSOR Out Variable
CREATE OR REPLACE PROCEDURE calculus (l_cursor IN OUT SYS_REFCURSOR)
IS
BEGIN
    OPEN l_cursor FOR
        SELECT 
            AVG(challenge.ccnumber) MEDIA,
            MEDIAN(challenge.ccnumber) MEDIANA,
            STATS_MODE(challenge.ccnumber) MODA,
            MIN(challenge.ccnumber) MINIMO,
            MAX(challenge.ccnumber) MAXIMO
        FROM challenge;
    --CLOSE l_cursor;
END;

--Execute Procedure
VARIABLE cursor_output REFCURSOR;
EXECUTE calculus(:cursor_output);
PRINT :cursor_output;


-- Function :  Return
CREATE OR REPLACE NONEDITIONABLE FUNCTION f_calculus 
    RETURN SYS_REFCURSOR
AS
    l_cursor SYS_REFCURSOR;
BEGIN
    OPEN l_cursor FOR
        SELECT 
            AVG(challenge.ccnumber) MEDIA,
            MEDIAN(challenge.ccnumber) MEDIANA,
            STATS_MODE(challenge.ccnumber) MODA,
            MIN(challenge.ccnumber) MINIMO,
            MAX(challenge.ccnumber) MAXIMO
        FROM challenge;
    RETURN l_cursor;
END;

--Execute Function
SELECT F_CALCULUS()
FROM dual;