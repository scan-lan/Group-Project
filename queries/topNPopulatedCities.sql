#
4.1 - 4.5
# The top N populated cities in ... where N is provided by the user.
SELECT ID, city.name, district, city.population, country.name AS country
FROM city
JOIN country
ON city.countrycode = country.code
1. ""
2. WHERE country.continent = ...
3. WHERE country.region = ...
4. WHERE country.name = ...
5. WHERE city.district = ...
ORDER BY population DESC
LIMIT N; #
variable