# The top N populated countries in the world where N is provided by the user.
SELECT code, name, continent, region, population, (
    SELECT name
    FROM city ci
    WHERE countrycode = co.code
        AND ci.id = co.capital
    ) AS capital
FROM country co
ORDER BY population DESC
LIMIT N; # variable

# The top N populated countries in a continent where N is provided by the user.
SELECT code, name, continent, region, population, (
    SELECT name
    FROM city ci
    WHERE countrycode = co.code
      AND ci.id = co.capital
) AS capital
FROM country co
WHERE continent = X # variable
ORDER BY population DESC
LIMIT N; # variable

# The top N populated countries in a region where N is provided by the user.
SELECT code, name, continent, region, population, (
    SELECT name
    FROM city ci
    WHERE countrycode = co.code
      AND ci.id = co.capital
) AS capital
FROM country co
WHERE region = X # variable
ORDER BY population DESC
LIMIT N; # variable