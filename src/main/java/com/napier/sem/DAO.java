package com.napier.sem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


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
                whereCondition = "TRUE\n";
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
            default:
                return null;
        }
        return whereCondition;
    }

    /**
     * This takes an SQL query in the form of a string and executes it against
     * the database.  It will return a list of Record objects.  It will return
     * the countries in a list of Country objects.
     * @param statementString The SQL statement to be executed
     * @return An ArrayList of country objects
     */
    public ArrayList<Record> executeStatement(String statementString, String recordType)
    {
        ArrayList<Record> records = new ArrayList<>();

        if (connection == null){ return records; }

        try
        {
            // Create the SQL statement object for sending statements to the database
            Statement statement = connection.createStatement();
            // Execute the query
            ResultSet resultSet = statement.executeQuery(statementString);
            // Create Country object and add it to the list for each result in the query
            while (resultSet.next())
            {
                records.add(new Record(resultSet, recordType));
            }
        }
        catch (SQLException e)
        {
            System.out.println(recordType + " query failed");
            System.out.println(e.getMessage());
        }
        return records;
    }

    /**
     * Use cases 1.1-1.3
     * Constructs the SQL query required and returns the result of the
     * query.
     *
     * @return An ordered list of countries in the world sorted by descending population
     */
    public ArrayList<Record> allCountriesIn(String areaFilter, String areaName)
    {
        String whereCondition = getWhereCondition(areaFilter, areaName);
        if (whereCondition == null)
        {
            System.out.println("allCountriesIn - invalid query condition");
            return new ArrayList<>();
        }

        // Define the SQL query as a string
        String statementString = "SELECT code, country.name, continent, region, country.population, city.name AS capital\n" +
                "FROM country\n" +
                "    JOIN city ON country.capital = city.id\n" +
                "WHERE " + whereCondition +
                "ORDER BY country.population DESC";

        return executeStatement(statementString, App.COUNTRY);
    }

    /**
     * Use case 2.1-2.3
     * Constructs the SQL query required and returns the result of the
     * query.
     *
     * @return An ordered list of countries sorted by descending population
     */
    public ArrayList<Record> topNCountriesIn(String areaFilter, String areaName, Integer n)
    {
        String whereCondition = getWhereCondition(areaFilter, areaName);
        if (whereCondition == null || n < 1)
        {
            System.out.println("topNCountriesIn - invalid query condition");
            return new ArrayList<>();
        }

        // Define the SQL query as a string
        String statementString = "SELECT country.code, country.name, continent, region, country.population, city.name AS capital\n" +
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
     * Constructs an SQL query to fetch all cities in a given area, and executes the query.
     *
     * @return An ordered list of cities in a defined area sorted by descending population
     */
    public ArrayList<Record> allCitiesIn(String areaFilter, String areaName)
    {
        String whereCondition = getWhereCondition(areaFilter, areaName);
        if (whereCondition == null)
        {
            System.out.println("allCitiesIn - invalid query condition");
            return new ArrayList<>();
        }

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
     * Constructs an SQL query to fetch the top N populated cities in a specific area, and executes the query.
     *
     * @return An ordered list of cities in a defined area sorted by descending population
     */
    public ArrayList<Record> topNCitiesIn(String areaFilter, String areaName, Integer n)
    {
        String whereCondition = getWhereCondition(areaFilter, areaName);

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
     *
     * @return An ordered list of capital cities in a specific area sorted by descending population
     */
    public ArrayList<Record> allCapitalCitiesIn(String areaFilter, String areaName)
    {
        String whereCondition = getWhereCondition(areaFilter, areaName);

        // Define the SQL query as a string
        String statementString = "SELECT city.name, city.population, country.region, country.continent, country.name AS country \n" +
                "FROM city\n" +
                "    JOIN country ON city.countrycode = country.code\n" +
                "WHERE city.id = country.capital \n" +
                "AND " + whereCondition +
                "ORDER BY city.population DESC;";

        return executeStatement(statementString, App.CAPITAL_CITY);
    }

    /**
     * Use cases 6.1-6.3
     * Constructs an SQL query to fetch the top N populated cities in a specific area, and executes the query.
     *
     * @return An ordered list of cities in a defined area sorted by descending population
     */
    public ArrayList<Record> topNCapitalCitiesIn(String areaFilter, String areaName, Integer n)
    {
        String whereCondition = getWhereCondition(areaFilter, areaName);

        // Define the SQL query as a string
        String statementString = "SELECT city.name, city.population, country.region, country.continent, country.name AS country \n" +
                "FROM city\n" +
                "JOIN country ON city.countrycode = country.code\n" +
                "AND city.id = country.capital \n" +
                "WHERE city.population > 0 \n" +
                "AND " + whereCondition +
                "ORDER BY city.population DESC \n" +
                "LIMIT " + n;

        return executeStatement(statementString, App.CAPITAL_CITY);
    }

    /**
     * Use cases 7.1-7.3
     * Constructs an SQL query to fetch the population in a specific area as well as the population who live in cities and those who don't, and executes the query.
     *
     * @return The population of a specified area as well as the population who live in cities and those who don't
     */
    public ArrayList<Record> populationCitiesAndNonCities(String areaFilter, String areaName)
    {
        String whereCondition = getWhereCondition(areaFilter, areaName);

        String selectCondition = null;
        switch (areaFilter)
        {
            case App.CONTINENT:
                selectCondition = "country.continent";
                break;
            case App.REGION:
                selectCondition = "country.region";
                break;
            case App.COUNTRY:
                selectCondition = "country.name";
                break;
        }
        if (selectCondition == null)
        {
            System.out.println("populationCitiesAndNonCities - invalid query condition");
            return new ArrayList<>();
        }

        // Define the SQL query as a string
        String statementString = "SELECT "+ selectCondition +" AS name,\n" +
                "   SUM(DISTINCT country.population) AS areaPopulation,\n" +
                "   SUM(city.population) AS cityPopulation,\n" +
                "   ((SUM(city.population) / SUM(DISTINCT country.population)) * 100) AS cityPercentage,\n" +
                "   (SUM(DISTINCT country.population) - SUM(city.population)) AS nonCityPopulation,\n" +
                "   ((SUM(DISTINCT country.population) - SUM(city.population)) / SUM(DISTINCT country.population) * 100) AS nonCityPercentage\n" +
                "FROM country\n" +
                "   JOIN city ON code = countryCode\n" +
                "WHERE " + whereCondition;

        return executeStatement(statementString, App.CITY_POPULATION);
    }

    /**
     * Use case 9.1
     * Constructs an SQL query to find the number of people who speak Chinese/English/Hindi/Spanish/Arabic
     *
     * @return An ordered list of languages spoken in the world sorted by the number of langauge speakers descending
     */
    public ArrayList<Record> languageReport()
    {
        String statementString = "WITH x AS (SELECT SUM(population) AS world_population FROM country)\n" +
                "SELECT `language`, speakers, ((speakers / world_population) * 100) AS percentage\n" +
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
