# all cities in world
SELECT city.name, district, city.population, country.name AS country
FROM city
    JOIN country ON city.countrycode = country.code
ORDER BY city.population DESC;

# all cities in continent
SELECT city.name, district, city.population, country.name AS country
FROM city
    JOIN country ON city.countrycode = country.code
WHERE country.continent = 'x'
ORDER BY city.population DESC;

# all cities in region
SELECT city.name, district, city.population, country.name AS country
FROM city
    JOIN country ON city.countrycode = country.code
WHERE country.region = 'x'
ORDER BY city.population DESC;

# all cities in country
SELECT city.name, district, city.population, country.name AS country
FROM city
    JOIN country ON city.countrycode = country.code
WHERE country.name = 'x'
ORDER BY city.population DESC;

# all cities in district
SELECT city.name, district,  city.population, country.name AS country
FROM city
    JOIN country ON city.countrycode = country.code
WHERE city.district = 'x'
ORDER BY city.population DESC;