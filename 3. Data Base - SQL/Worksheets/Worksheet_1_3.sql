--1. Crear una base de datos challenge. 

--Create Table Space
DROP TABLESPACE challenge; 
CREATE TABLESPACE challenge 
DATAFILE 'C:\app\User\product\21c\oradata\XE\ChallengeTableSpace.dbf'
SIZE 10M;

-- Create User
ALTER SESSION SET "_ORACLE_SCRIPT"=true;
CREATE USER challenge IDENTIFIED BY "pass"; 


--------------------------
-- Grant Access
-- Some Privileges to user challenge 
-------------------------

-- Create User
CREATE USER C##SAFI IDENTIFIED BY pass

DEFAULT TABLESPACE SYSTEM
TEMPORARY TABLESPACE Challenge
QUOTA UNLIMITED ON SYSTEM;

-- Privileges: Tablespace
ALTER USER challenge 
DEFAULT TABLESPACE Challenge
QUOTA UNLIMITED ON challenge;

GRANT UNLIMITED TABLESPACE TO challenge;
GRANT ALTER TABLESPACE TO challenge;

-- Privileges: User
GRANT 
    CREATE SESSION,
    CREATE TABLE,
    CREATE VIEW,
    CREATE SEQUENCE
TO challenge;

--Privileges: Directory
GRANT 
    CREATE ANY DIRECTORY, 
    DROP ANY DIRECTORY 
TO challenge; 

GRANT 
    READ, WRITE 
    ON DIRECTORY img_dir 
TO challenge; 

-- Privileges: Procedures PL/SQL
GRANT
    CREATE ANY PROCEDURE,
    ALTER ANY PROCEDURE,
    DROP ANY PROCEDURE
TO challenge; 

GRANT EXECUTE ON b.procedure_name TO a;

-- Privileges: Dictionary/Views (DBA_TABLES)
GRANT
    SELECT ANY DICTIONARY,
    CREATE SESSION
TO challenge;

-- Privileges: Triggers
GRANT
    ADMINISTER DATABASE TRIGGER,
    ALTER ANY TRIGGER,
    CREATE ANY TRIGGER,
    DROP ANY TRIGGER         
TO challenge;

-- Privileges - Admin Tables
GRANT All ON nombretable TO challenge;
REVOKE ALL ON nombretabla FROM challenge;

GRANT All ON challenge TO challenge;
GRANT All ON logo TO challenge;

-- Delete user
DROP USER challenge cascade;

--2. Crear un usuario de solo lectura en la bd. 
CREATE USER readonly_user IDENTIFIED BY "password"; 
GRANT 
    CREATE SESSION, 
    SELECT ANY TABLE 
TO readonly_user; 

--3. Crear un usuario de lectura/escritura. 
CREATE USER readwrite_user IDENTIFIED BY "password"; 
GRANT 
    CREATE SESSION, 
    SELECT ANY TABLE, 
    INSERT ANY TABLE, 
    DELETE ANY TABLE, 
    UPDATE ANY TABLE 
TO readwrite_user; 