
-- 14. Generar 100.000 registros (con valores aleatorios pero que cada dato no exceda los límites máximos y mínimos de cada atributo) e insertarlos en la tabla challenge.

-- Random Data --> Insert Select
-- Has trouble with being the firts insert
-- And after it's done, it basically cannot do it again
INSERT INTO challenge (yn, age, birthday, bool, city, ccnumber, "date", digit, dollar, "first", chifre, "name", "last", paragraph, sentence)
    SELECT 
        decode(round(dbms_random.value), 1, 'Y', 'N'),
        dbms_random.value(0, 120),

        TO_DATE(TRUNC(DBMS_RANDOM.VALUE( 
            TO_CHAR(TO_DATE('01/01/2000','DD/MM/YYYY'),'J'),
            TO_CHAR(SYSDATE, 'J')
        )),'J'),
        
        decode(round(dbms_random.value), 1, 'true', 'false'),
        dbms_random.string('A',dbms_random.value(5, 20)),
        dbms_random.value(1000, 999999999999),
        
        TO_DATE(TRUNC(DBMS_RANDOM.VALUE(
            TO_CHAR(TO_DATE('01/01/2000','DD/MM/YYYY'),'J'),
            TO_CHAR(SYSDATE, 'J')
        )),'J'),
        
        dbms_random.value(0,9),
        round(dbms_random.value(0, 999999), 4),
        dbms_random.string('A',dbms_random.value(5, 50)),
        dbms_random.value(0, 100),
        dbms_random.string('A',dbms_random.value(5, 50)),
        dbms_random.string('A',dbms_random.value(5, 50)),
        dbms_random.string('A',dbms_random.value(5, 500)),
        dbms_random.string('A',dbms_random.value(5, 100))
        
    FROM challenge
CONNECT BY level <= 100000;


-- Random Data --> Insert Select PL/SQL
-- Has trouble with being the firts insert
-- And after it's done, it basically cannot do it again --> Levels??
DECLARE
    
    v_mindate NUMBER := TO_CHAR(TO_DATE('01/01/2000','DD/MM/YYYY'),'J');
    v_sysdate NUMBER :=  TO_CHAR(SYSDATE, 'J');
    
BEGIN 

    INSERT INTO challenge (yn, age, birthday, bool, city, ccnumber, "date", digit, dollar, "first", chifre, "name", "last", paragraph, sentence)
        SELECT 
            decode(round(dbms_random.value), 1, 'Y', 'N'),
            dbms_random.value(0, 120),

            TO_DATE(TRUNC(DBMS_RANDOM.VALUE( v_mindate,v_sysdate )),'J'),
            
            decode(round(dbms_random.value), 1, 'true', 'false'),
            dbms_random.string('A',dbms_random.value(5, 20)),
            dbms_random.value(1000, 999999999999),
            
            TO_DATE(TRUNC(DBMS_RANDOM.VALUE(v_mindate,v_sysdate)),'J'),
            
            dbms_random.value(0,9),
            round(dbms_random.value(0, 999999), 4),
            dbms_random.string('A',dbms_random.value(5, 50)),
            dbms_random.value(0, 100),
            dbms_random.string('A',dbms_random.value(5, 50)),
            dbms_random.string('A',dbms_random.value(5, 50)),
            dbms_random.string('A',dbms_random.value(5, 500)),
            dbms_random.string('A',dbms_random.value(5, 100))
            
        FROM challenge
    CONNECT BY level <= 100000;
    
END;


-- Loop 
-- It works ok, kinda slow though
DECLARE
    
    v_mindate NUMBER := TO_CHAR(TO_DATE('01/01/2000','DD/MM/YYYY'),'J');
    v_sysdate NUMBER :=  TO_CHAR(SYSDATE, 'J');
    rows_inserted number := 0;
    
BEGIN

    LOOP
        Begin
            INSERT INTO challenge(yn, age, birthday, bool, city, ccnumber, "date", digit, dollar, "first", chifre, "name", "last", paragraph, sentence) 
            VALUES(
                decode(round(dbms_random.value), 1, 'Y', 'N'),
                dbms_random.value(0, 120),
    
                TO_DATE(TRUNC(DBMS_RANDOM.VALUE( v_mindate,v_sysdate )),'J'),
                
                decode(round(dbms_random.value), 1, 'true', 'false'),
                dbms_random.string('A',dbms_random.value(5, 20)),
                dbms_random.value(1000, 999999999999),
                
                TO_DATE(TRUNC(DBMS_RANDOM.VALUE(v_mindate,v_sysdate)),'J'),
                
                dbms_random.value(0,9),
                round(dbms_random.value(0, 999999), 4),
                dbms_random.string('A',dbms_random.value(5, 50)),
                dbms_random.value(0, 100),
                dbms_random.string('A',dbms_random.value(5, 50)),
                dbms_random.string('A',dbms_random.value(5, 50)),
                dbms_random.string('A',dbms_random.value(5, 500)),
                dbms_random.string('A',dbms_random.value(5, 100))
            );
            rows_inserted := rows_inserted + 1;
        END;
        EXIT WHEN rows_inserted = 100000;
    END LOOP;
    COMMIT;
    
END;

-- Loop : Procedure
DROP PROCEDURE random_insert;
CREATE OR REPLACE PROCEDURE random_insert
    (numberOfRows IN NUMBER)

IS
    v_mindate NUMBER := TO_CHAR(TO_DATE('01/01/2000','DD/MM/YYYY'),'J');
    v_sysdate NUMBER :=  TO_CHAR(SYSDATE, 'J');
    v_rowsIn NUMBER := 0;
    v_numberOfRows NUMBER := numberOfRows;

BEGIN

    LOOP
        BEGIN
            INSERT INTO challenge.challenge(yn, age, birthday, bool, city, ccnumber, "date", digit, dollar, "first", chifre, "name", "last", paragraph, sentence) 
            VALUES(
                decode(round(dbms_random.value), 1, 'Y', 'N'),
                dbms_random.value(0, 120),

                TO_DATE(TRUNC(DBMS_RANDOM.VALUE( v_mindate,v_sysdate )),'J'),

                decode(round(dbms_random.value), 1, 'true', 'false'),
                dbms_random.string('A',dbms_random.value(5, 20)),
                dbms_random.value(1000, 999999999999),

                TO_DATE(TRUNC(DBMS_RANDOM.VALUE(v_mindate,v_sysdate)),'J'),

                dbms_random.value(0,9),
                round(dbms_random.value(0, 999999), 4),
                dbms_random.string('A',dbms_random.value(5, 50)),
                dbms_random.value(0, 100),
                dbms_random.string('A',dbms_random.value(5, 50)),
                dbms_random.string('A',dbms_random.value(5, 50)),
                dbms_random.string('A',dbms_random.value(5, 500)),
                dbms_random.string('A',dbms_random.value(5, 100))
            );
            v_rowsIn := v_rowsIn + 1;
        END;
        EXIT WHEN v_rowsIn = v_numberOfRows;
    END LOOP;
    COMMIT;

END;

EXECUTE random_insert(10);
EXECUTE random_insert(100000);

--------------------------------------------
-- For too small tables
--------------------------------------------
-- More space for the initial tablespace*
ALTER TABLESPACE challenge
    ADD DATAFILE 'C:\app\User\product\21c\oradata\XE\ChallengeTableSpace2E.dbf'
    SIZE 10M
    AUTOEXTEND ON;

--------------------------------------------
-- Some other things 
--------------------------------------------

SELECT * FROM challenge;

-- Insert Date : String (Indicate format) to Date 
INSERT INTO challenge (birthday) VALUES
(TO_DATE('2009/01/01','YYYY/MM/DD'));

-- Retrieve Date: Date to String with new format - Birthday and date
SELECT 
    TO_CHAR(birthday,'DD/MM/YYYY') AS birthday,
    TO_CHAR("date",'YYYY/MM/DD') AS "date",
FROM challenge;
