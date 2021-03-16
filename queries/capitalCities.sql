--Use case 5-1 - All Capital Cities in the world
SELECT city.name, city.population, country.name AS country
FROM city
         JOIN country ON city.countrycode = country.code
WHERE city.id = country.capital
ORDER BY city.population DESC;


--Use case 5-2 - All Capital Cities in a specified continent
SELECT city.name, city.population, country.name AS country
FROM city
         JOIN country ON city.countrycode = country.code
WHERE country.continent = x
  AND city.id = country.capital
ORDER BY city.population DESC;

--Use case 5-3 - All Capital Cities in a specified region
SELECT city.name, city.population, country.name AS country
FROM city
         JOIN country ON city.countrycode = country.code
WHERE country.region = x
  AND city.id = country.capital
ORDER BY city.population DESC;