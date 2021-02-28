# All countries in the world organised by largest population to smallest.
SELECT code, name, continent, region, population, (
    SELECT name
    FROM city ci
    WHERE countrycode = co.code
        AND ci.id = co.capital
    ) AS capital
FROM country co
ORDER BY population DESC;


