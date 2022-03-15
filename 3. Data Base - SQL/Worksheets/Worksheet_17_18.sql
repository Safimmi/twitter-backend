-- 17. Generar una consulta que filtre aquellos registros que en la columna yn tienen un valor Y y cruzarlos con los que tienen un valor de N en el campo yn donde el campo age tenga el mismo valor 

-- Option 1
WITH n AS ( 
    SELECT seq, age, yn 
    FROM challenge.challenge 
    WHERE LOWER(yn) = 'n' 
) 
SELECT y.seq AS y_seq, n.seq AS n_seq, y.age AS age 
FROM challenge.challenge y 
INNER JOIN n 
    ON y.age = n.age 
WHERE LOWER(y.yn) = 'y'; 

-- Option 2
WITH n AS ( 
    SELECT seq, age, yn 
    FROM challenge.challenge 
    WHERE yn = 'N' 
), 
y AS ( 
    SELECT seq, age, yn 
    FROM challenge.challenge 
    WHERE yn = 'Y' 
) 
SELECT y.seq AS y_seq, n.seq AS n_seq, y.age AS age 
FROM y 
LEFT OUTER JOIN n ON y.age = n.age; 



-- 18. En la consulta anterior adicionar una columna donde se cuente los registros que tienen N y los registros que tienen Y 
WITH  
    y_count AS ( 
        SELECT count(*) as y_total_count 
        FROM challenge.challenge 
        WHERE yn = 'Y' 
    ), 
    n_count AS ( 
        SELECT count(*) as n_total_count 
        FROM challenge.challenge 
        WHERE yn = 'N' 
    ), 
    y AS ( 
        SELECT seq, age, yn, y_total_count 
        FROM challenge.challenge, y_count 
        WHERE yn = 'Y' 
    ), 
    n AS ( 
        SELECT seq, age, yn, n_total_count 
        FROM challenge.challenge, n_count 
        WHERE yn = 'N' 
    ) 
SELECT 
    y.seq AS y_seq, 
    n.seq AS n_seq, 
    y.age AS age, 
    y_total_count, 
    n_total_count 
FROM y 
LEFT OUTER JOIN n ON y.age = n.age; 