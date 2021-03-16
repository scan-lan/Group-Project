# All countries in the world organised by largest population to smallest.
SELECT code, country.name, region, country.population, city.name AS capital
FROM country
    JOIN city ON country.capital = city.id
ORDER BY country.population DESC;
