-- 10. Alterar la tabla challenge y adicionar un campo numérico denominado trgr. 
ALTER TABLE challenge.challenge
ADD trgr NUMBER DEFAULT 0 NOT NULL;

-- 11. Actualizar el campo trgr con el valor de seq incrementando este valor en 100. 

-- Option 1: Create a sequence that starts with the last identity sequence number 
DROP SEQUENCE seq_trgr;
CREATE SEQUENCE seq_trgr
    START WITH 100 
    INCREMENT BY 1
    NOCYCLE;
    
ALTER TABLE challenge 
    MODIFY trgr 
    DEFAULT  seq_trgr.nextval;

-- Option 2 (X): Drop and new column with identity --> Nope, just one identity column
ALTER TABLE challenge DROP column trgr;

ALTER TABLE challenge 
    ADD trg NUMBER GENERATED ALWAYS AS IDENTITY START WITH 100 INCREMENT BY 1 NOT NULL;


-- Option 3: Trigger on after insert (Option 1 to find last seq_identity)
CREATE OR REPLACE TRIGGER update_trgr
AFTER INSERT 
    ON challenge.challenge
DECLARE 
    curr_seq NUMBER;
BEGIN
    
    SELECT seq 
    INTO curr_seq
    FROM challenge.challenge
    WHERE seq = (SELECT MAX(seq) FROM challenge.challenge);
    
    UPDATE challenge.challenge
    SET trgr = curr_seq + 100
    WHERE seq = curr_seq;
    
END;


-------------------------------------------------
-- Return Last in sequence / identity
--------------------------------------------------

-- Last row
SELECT seq 
FROM challenge
WHERE SEQ = (SELECT MAX(seq) FROM challenge);

-- Currval
SELECT TABLE_NAME, COLUMN_NAME, DATA_DEFAULT 
FROM USER_TAB_COLUMNS
WHERE TABLE_NAME = 'CHALLENGE';

SELECT "ISEQ$$_76671".currval FROM dual; 

--PL/SQL : Anon
DECLARE
    curr_ide NUMBER;

BEGIN
    SELECT seq 
    INTO curr_ide
    FROM challenge
    WHERE SEQ = (SELECT MAX(seq) FROM challenge);
    
    dbms_output.put_line( 'LAST SEQ IDENTITY : ' || curr_ide );    
END;


-- PL/SQL: Function
CREATE OR REPLACE NONEDITIONABLE FUNCTION f_lastseq 
    RETURN NUMBER
AS
    curr_ide NUMBER;
BEGIN
    SELECT seq 
    INTO curr_ide
    FROM challenge
    WHERE SEQ = (SELECT MAX(seq) FROM challenge);
    RETURN curr_ide;
END;


-------------------------------------------------
-- Insert some things
--------------------------------------------------

INSERT INTO challenge (yn) VALUES
('Y');
INSERT INTO challenge (yn) VALUES
('N');

