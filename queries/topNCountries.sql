# The top N most populous countries in the world where N is provided by the user.
SELECT country.code, country.name, continent, region, country.population, city.name AS capital
FROM country
    JOIN city ON country.code = city.countrycode
    AND country.capital = city.id
ORDER BY population DESC
LIMIT N; # variable

# The top N most populous countries in a continent where N is provided by the user.
SELECT country.code, country.name, continent, region, country.population, city.name AS capital
FROM country
    JOIN city ON country.code = city.countrycode
    AND country.capital = city.id
WHERE continent = X # variable
ORDER BY population DESC
LIMIT N; # variable

# The top N most populous countries in a region where N is provided by the user.
SELECT country.code, country.name, continent, region, country.population, city.name AS capital
FROM country
    JOIN city ON country.code = city.countrycode
    AND country.capital = city.id
WHERE region = X # variable
ORDER BY population DESC
LIMIT N; # variable