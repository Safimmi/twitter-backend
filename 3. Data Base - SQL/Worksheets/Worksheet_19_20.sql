--19. Crear una consulta que muestre el mes de los campos birthday y date 
SELECT 
    EXTRACT(MONTH FROM birthday) as birthday_month, 
    EXTRACT(MONTH FROM "date") as date_month 
FROM challenge.challenge; 

--20. Eliminar los 100.000 registros insertados por el grupo. 
TRUNCATE TABLE challenge.challenge; 