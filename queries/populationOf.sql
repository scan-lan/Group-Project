# population of a continent
SELECT SUM(country.population)
FROM country
WHERE country.continent = "Asia";

# population of a district
SELECT SUM(city.population)
FROM country
         JOIN city ON country.code = city.countrycode
WHERE city.district = "Scotland";
