-- 4. Crear una tabla llamada challenge con las especificaciones presentadas en la última sección del documento (Consideraciones) 
-- Create sequence
DROP SEQUENCE seq;
CREATE SEQUENCE seq
    START WITH 1 
    INCREMENT BY 1
    MAXVALUE 999999
    MINVALUE 1
    NOCYCLE;

-- Create table 
DROP TABLE challenge;
PURGE RECYCLEBIN;

CREATE TABLE challenge.challenge (
    seq NUMBER GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    yn CHAR(1) DEFAULT 'Y' NOT NULL,
    age SMALLINT DEFAULT 0,
    birthday DATE,
    bool CHAR(5) DEFAULT 'true' NOT NULL,
    city VARCHAR2(20 CHAR),
    ccnumber NUMBER(12, 0) DEFAULT 1000,
    "date" DATE,
    digit NUMBER(1, 0),
    dollar NUMBER(19, 4),
    "first" VARCHAR2(50 CHAR),
    chifre NUMBER,
    "name" VARCHAR2(50 CHAR),
    "last" VARCHAR2(50 CHAR),
    paragraph CLOB,
    sentence CLOB,
    
    CONSTRAINT check_yn CHECK (yn IN ('Y', 'N')),
    CONSTRAINT check_bool CHECK (bool IN ('true', 'false')),
    CONSTRAINT check_age CHECK (age BETWEEN 0 and 120),
    CONSTRAINT check_ccnumber CHECK (ccnumber BETWEEN 1000 and 999999999999) 
) TABLESPACE challenge; 

--INSERTS

--Works ok
INSERT INTO challenge (yn) VALUES
('Y');
INSERT INTO challenge (yn) VALUES
('N');

-- Works ok
INSERT INTO challenge (yn)
    SELECT 'N' FROM dual UNION ALL
    SELECT 'Y' FROM dual UNION ALL
    SELECT 'Y' FROM dual; 

-- Constraint Broken: Primary Key
INSERT ALL
    INTO challenge (yn) VALUES ('N')
    INTO challenge (yn) VALUES ('Y')
    INTO challenge (yn) VALUES ('N')
SELECT * FROM challenge;



