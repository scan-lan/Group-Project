# living in city vs not in city by continent
SELECT country.continent AS name,
       totalPopulation,
       populationInCities,
       (totalPopulation - cityPopulation) AS populationNotInCities
FROM (SELECT country.continent,
             SUM(population) AS totalPopulation
      FROM country
      WHERE country.continent = 'Europe') t,
     (SELECT SUM(city.population) AS populationInCities
      FROM city
               JOIN country ON city.countrycode = country.code
      WHERE country.continent = 'Europe') c;

# living in city vs not in city by region
SELECT country.region AS name,
       totalPopulation,
       populationInCities,
       (totalPopulation - cityPopulation) AS populationNotInCities
FROM (SELECT country.region,
             SUM(population) AS totalPopulation
      FROM country
      WHERE country.region = 'Nordic Countries') t,
     (SELECT SUM(city.population) AS populationInCities
      FROM city
               JOIN country ON city.countrycode = country.code
      WHERE country.region = 'Nordic Countries') c;

# living in city vs not in city by country
SELECT country.name AS name,
       totalPopulation,
       populationInCities,
       (totalPopulation - cityPopulation) AS populationNotInCities
FROM (SELECT country.name,
             SUM(population) AS totalPopulation
      FROM country
      WHERE country.name = 'France') t,
     (SELECT SUM(city.population) AS populationInCities
      FROM city
               JOIN country ON city.countrycode = country.code
      WHERE country.name = 'France') c;