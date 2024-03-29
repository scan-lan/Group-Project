package com.napier.sem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * The Data Access Object (DAO) is used for querying the database and returning
 * the results in a usable manner.
 */
public class DAO
{
    // Private properties
    private final Connection connection;

    public DAO(Connection connection) { this.connection = connection; }

    /**
     * Generates a valid condition for use with our SQL queries
     * @param areaFilter The geographical category by which we'll be filtering
     * @param areaName The name of the location which will be used
     * @return The where condition string
     */
    public static String getWhereCondition(String areaFilter, String areaName)
    {
        String whereCondition;

        if (areaName == null || areaFilter == null) { return null; }

        switch (areaFilter)
        {
            case App.WORLD:
                whereCondition = "country.code LIKE '%'\n";
                break;
            case App.CONTINENT:
                whereCondition = "country.continent = '" + areaName + "'\n";
                break;
            case App.REGION:
                whereCondition = "country.region = '" + areaName + "'\n";
                break;
            case App.COUNTRY:
                whereCondition = "country.name = '" + areaName + "'\n";
                break;
            case App.DISTRICT:
                whereCondition = "city.district = '" + areaName + "'\n";
                break;
            case App.CITY:
                whereCondition = "city.name = '" + areaName + "'\n";
                break;
            default:
                return null;
        }
        return whereCondition;
    }

    /**
     * This takes an SQL query in the form of a string and executes it against
     * the database.  It will return a list of Record objects.
     * @param statementString The SQL statement to be executed.
     * @param recordType The type of record that the query will return (country, city, capital city etc.).
     * @return An ArrayList of country objects
     */
    public List<Record> executeStatement(String statementString, String recordType)
    {
        ArrayList<Record> records = new ArrayList<>();

        if (connection == null) return null;

        try
        {
            // Create the SQL statement object for sending statements to the database
            Statement statement = connection.createStatement();
            // Execute the query
            ResultSet resultSet = statement.executeQuery(statementString);
            // Create Country object and add it to the list for each result in the query
            while (resultSet.next())
            {
                if (resultSet.getString("name") != null) records.add(new Record(resultSet, recordType));
            }
        }
        catch (SQLException e)
        {
            System.out.println(recordType + " query failed");
            System.out.println(e.getMessage());
            return null;
        }
        return records;
    }

    /**
     * This method checks if the parameters being passed to a query will result in an invalid SQL statement.
     * @param queryName Name of the query being tested
     * @param whereCondition The where condition being passed to the query
     * @param areaFilter The area filter being passed to the query
     * @param validAreaFilters The area filters that are accepted by the query
     * @param n The value of n, if the query takes an n
     * @return True if the query will result in an invalid SQL statement, otherwise false
     */
    public boolean queryInvalid(String queryName,
                                String whereCondition,
                                String areaFilter,
                                List<String> validAreaFilters,
                                int n)
    {
        if (whereCondition == null || n < 1)
        {
            System.out.printf("%s - invalid query condition. ", queryName);
            return true;
        }
        else if (!validAreaFilters.contains(areaFilter))
        {
            System.out.printf("%s - bad area filter passed. ", queryName);
            return true;
        }
        return false;
    }

    /**
     * Use cases 1.1-1.3
     * Constructs an SQL query to fetch all countries in a specific area, and executes the query.
     * @param areaFilter The type of area you want to filter by (continent, country etc.).
     * @param areaName The name of the area you want to get countries from.
     * @return An ordered list of countries sorted by descending population
     */
    public List<Record> allCountriesIn(String areaFilter, String areaName)
    {
        String whereCondition = getWhereCondition(areaFilter, areaName);
        ArrayList<String> validAreaFilters = new ArrayList<>(Arrays.asList(App.WORLD, App.CONTINENT, App.REGION));

        if (queryInvalid("allCountriesIn", whereCondition,
                areaFilter, validAreaFilters, 1)) return new ArrayList<>();

        // Define the SQL query as a string
        String statementString = "SELECT code,\n" +
                "country.name,\n" +
                "continent,\n" +
                "region,\n" +
                "country.population,\n" +
                "city.name AS capital\n" +
                "FROM country\n" +
                "    JOIN city ON country.capital = city.id\n" +
                "WHERE " + whereCondition +
                "ORDER BY country.population DESC";

        return executeStatement(statementString, App.COUNTRY);
    }

    /**
     * Use cases 2.1-2.3
     * Constructs an SQL query to fetch the top N most populous countries in a specific area, and executes the query.
     * @param areaFilter The type of area you want to filter by (continent, country etc.).
     * @param areaName The name of the area you want to get countries from.
     * @param n The maximum number of results shown.
     * @return An ordered list of countries sorted by descending population.
     */
    public List<Record> topNCountriesIn(String areaFilter, String areaName, Integer n)
    {
        String whereCondition = getWhereCondition(areaFilter, areaName);
        ArrayList<String> validAreaFilters = new ArrayList<>(Arrays.asList(App.WORLD, App.CONTINENT, App.REGION));

        if (queryInvalid("topNCountriesIn", whereCondition,
                areaFilter, validAreaFilters, n)) return new ArrayList<>();

        // Define the SQL query as a string
        String statementString = "SELECT country.code,\n" +
                "country.name,\n" +
                "continent,\n" +
                "region,\n" +
                "country.population,\n" +
                "city.name AS capital\n" +
                "FROM country\n" +
                "    JOIN city ON country.code = city.countrycode\n" +
                "    AND country.capital = city.id\n" +
                "WHERE " + whereCondition +
                "ORDER BY population DESC\n" +
                "LIMIT " + n;
        return executeStatement(statementString, App.COUNTRY);
    }

    /**
     * Use cases 3.1-3.5
     * Constructs an SQL query to fetch all cities in a specific area, and executes the query.
     * @param areaFilter The type of area you want to filter by (continent, country etc.).
     * @param areaName The name of the area you want to get cities from.
     * @return An ordered list of cities sorted by descending population
     */
    public List<Record> allCitiesIn(String areaFilter, String areaName)
    {
        String whereCondition = getWhereCondition(areaFilter, areaName);
        ArrayList<String> validAreaFilters = new ArrayList<>(Arrays.asList(App.WORLD, App.CONTINENT, App.REGION,
                App.COUNTRY, App.DISTRICT));

        if (queryInvalid("allCitiesIn", whereCondition,
                areaFilter, validAreaFilters, 1)) return new ArrayList<>();

        // Define the SQL query as a string
        String statementString = "SELECT city.name, district, city.population, country.name AS country\n" +
                "FROM city\n" +
                "    JOIN country ON city.countrycode = country.code\n" +
                "WHERE " + whereCondition +
                "ORDER BY city.population DESC;";

        return executeStatement(statementString, App.CITY);
    }

    /**
     * Use cases 4.1-4.5
     * Constructs an SQL query to fetch the top N most populous cities in a specific area, and executes the query.
     * @param areaFilter The type of area you want to filter by (continent, country etc.).
     * @param areaName The name of the area you want to get cities from.
     * @param n The maximum number of results shown.
     * @return An ordered list of cities sorted by descending population.
     */
    public List<Record> topNCitiesIn(String areaFilter, String areaName, Integer n)
    {
        String whereCondition = getWhereCondition(areaFilter, areaName);
        ArrayList<String> validAreaFilters = new ArrayList<>(Arrays.asList(App.WORLD, App.CONTINENT, App.REGION,
                App.COUNTRY, App.DISTRICT));

        if (queryInvalid("topNCitiesIn", whereCondition,
                areaFilter, validAreaFilters, n)) return new ArrayList<>();

        // Define the SQL query as a string
        String statementString = "SELECT city.name, district, city.population, country.name AS country\n" +
                "FROM city\n" +
                "    JOIN country ON city.countrycode = country.code\n" +
                "WHERE city.population > 0 \n" +
                "AND " + whereCondition +
                "ORDER BY city.population DESC \n" +
                "LIMIT " + n;

        return executeStatement(statementString, App.CITY);
    }

    /**
     * Use cases 5.1-5.3
     * Constructs an SQL query to fetch all capital cities in a specific area, and executes the query.
     * @param areaFilter The type of area you want to filter by (continent, country etc.).
     * @param areaName The name of the area you want to get capital cities from.
     * @return An ordered list of capital cities sorted by descending population
     */
    public List<Record> allCapitalCitiesIn(String areaFilter, String areaName)
    {
        String whereCondition = getWhereCondition(areaFilter, areaName);
        ArrayList<String> validAreaFilters = new ArrayList<>(Arrays.asList(App.WORLD, App.CONTINENT, App.REGION));

        if (queryInvalid("allCapitalCitiesIn", whereCondition,
                areaFilter, validAreaFilters, 1)) return new ArrayList<>();

        // Define the SQL query as a string
        String statementString = "SELECT city.name,\n" +
                "city.population,\n" +
                "country.region,\n" +
                "country.continent,\n" +
                "country.name AS country \n" +
                "FROM city\n" +
                "    JOIN country ON city.countrycode = country.code\n" +
                "WHERE city.id = country.capital \n" +
                "AND " + whereCondition +
                "ORDER BY city.population DESC";

        return executeStatement(statementString, App.CAPITAL_CITY);
    }

    /**
     * Use cases 6.1-6.3
     * Constructs an SQL query to fetch the top N most populous capital cities in a specific area, and executes the query.
     * @param areaFilter The type of area you want to filter by (continent, country etc.).
     * @param areaName The name of the area you want to get capital cities from.
     * @param n The maximum number of results shown.
     * @return An ordered list of capital cities in a specific area sorted by descending population.
     */
    public List<Record> topNCapitalCitiesIn(String areaFilter, String areaName, Integer n)
    {
        String whereCondition = getWhereCondition(areaFilter, areaName);
        ArrayList<String> validAreaFilters = new ArrayList<>(Arrays.asList(App.WORLD, App.CONTINENT, App.REGION));

        if (queryInvalid("topNCapitalCitiesIn", whereCondition,
                areaFilter, validAreaFilters, n)) return new ArrayList<>();

        // Define the SQL query as a string
        String statementString = "SELECT city.name,\n" +
                "city.population,\n" +
                "country.region,\n" +
                "country.continent,\n" +
                "country.name AS country \n" +
                "FROM city\n" +
                "JOIN country ON city.countrycode = country.code\n" +
                "AND city.id = country.capital \n" +
                "WHERE city.population > 0 \n" +
                "AND " + whereCondition +
                "ORDER BY city.population DESC\n" +
                "LIMIT " + n;

        return executeStatement(statementString, App.CAPITAL_CITY);
    }

    /**
     * Use cases 7.1-7.3
     * Constructs an SQL query to fetch the population in a specific area as well as the population who live
     * in cities and those who don't in that area, and executes the query.
     * @param areaFilter The type of area you want to filter by (continent, country etc.).
     * @param areaName The name of the area you want to get the residence report from.
     * @return The report as the only item in an array.
     */
    public List<Record> populationLivingInAndNotInCities(String areaFilter, String areaName)
    {
        String whereCondition = getWhereCondition(areaFilter, areaName);
        ArrayList<String> validAreaFilters = new ArrayList<>(Arrays.asList(App.CONTINENT, App.REGION, App.COUNTRY));

        if (queryInvalid("populationLivingInAndNotInCities", whereCondition,
                areaFilter, validAreaFilters, 1)) return new ArrayList<>();

        // Define the SQL query as a string
        String statementString = "SELECT name,\n" +
                "totalPopulation,\n" +
                "populationInCities,\n" +
                "(totalPopulation - populationInCities) AS populationNotInCities\n" +
                "FROM (SELECT " + whereCondition.split("\\s+")[0] + " AS name,\n" +
                "SUM(population) AS totalPopulation\n" +
                "    FROM country\n" +
                "    WHERE " + whereCondition + ") t,\n" +
                "    (SELECT SUM(city.population) AS populationInCities\n" +
                "    FROM city\n" +
                "        JOIN country ON city.countrycode = country.code\n" +
                "    WHERE " + whereCondition + ") c";

        return executeStatement(statementString, App.RESIDENCE_REPORT);
    }

    /**
     * Use cases 8.1-8.6
     * Constructs an SQL query to fetch the population of a specific area, and executes the query.
     * @param areaFilter The type of area you want to filter by (continent, country etc.).
     * @param areaName The name of the area of which you want to get the population.
     * @return The population of the specified area.
     */
    public List<Record> populationOf(String areaFilter, String areaName)
    {
        String whereCondition = getWhereCondition(areaFilter, areaName);
        ArrayList<String> validAreaFilters = new ArrayList<>(Arrays.asList(App.WORLD, App.CONTINENT, App.REGION,
                App.COUNTRY, App.DISTRICT, App.CITY));

        if (queryInvalid("populationOf", whereCondition,
                areaFilter, validAreaFilters, 1)) return new ArrayList<>();

        // Define the SQL query as a string
        String statementString = "SELECT " +
                ((areaFilter.equals(App.WORLD)) ? "'world'" : whereCondition.split("\\s+")[0]) +
                " AS name,\n" +
                "SUM(" + whereCondition.split("\\.")[0] + ".population) AS population\n" +
                "FROM country\n" +
                (("city".equals(whereCondition.split("\\.")[0])) ? "JOIN city ON countryCode = code\n" : "") +
                "WHERE " + whereCondition;

        return executeStatement(statementString, App.POPULATION);
    }

    /**
     * Use case 9.1
     * Constructs an SQL query to find the number of people who speak Chinese, English, Hindi, Spanish or Arabic.
     * @return An ordered list of languages spoken in the world sorted by the number of speakers.
     */
    public List<Record> languageReport()
    {
        // Define the SQL query as a string
        String statementString = "WITH x AS (SELECT SUM(population) AS world_population FROM country)\n" +
                "SELECT `language` AS name, speakers, ((speakers / world_population) * 100) AS percentage\n" +
                "FROM x, (\n" +
                "    SELECT `language`,\n" +
                "       CEILING(SUM(population * (percentage / 100))) AS speakers\n" +
                "    FROM countrylanguage\n" +
                "             JOIN country\n" +
                "                  ON countrycode = code\n" +
                "    WHERE countrylanguage.language = 'Chinese' OR countrylanguage.language = 'English' " +
                "    OR countrylanguage.language = 'Spanish' OR countrylanguage.language = 'Hindi' " +
                "    OR countrylanguage.language = 'Arabic'\n" +
                "    GROUP BY `language`\n" +
                "    ORDER BY speakers DESC) AS language_info";

        return executeStatement(statementString, App.LANGUAGE);
    }
}
