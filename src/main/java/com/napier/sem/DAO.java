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
     * the database.  It is only for use with statements that should return
     * country records.  It will return the countries in a list of Country
     * objects.
     * @param statementString The SQL statement to be executed
     * @return An ArrayList of country objects
     */
    public ArrayList<Country> executeCountryStatement(String statementString)
    {
        ArrayList<Country> countries = new ArrayList<>();
        try
        {
            // Create the SQL statement object for sending statements to the database
            Statement statement = connection.createStatement();
            // Execute the query
            ResultSet resultSet = statement.executeQuery(statementString);
            // Create Country object and add it to the list for each result in the query
            while (resultSet.next())
            {
                countries.add(new Country(resultSet));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Query failed");
            System.out.println(e.getMessage());
        }
        return countries;
    }

    /**
     * This takes an SQL query in the form of a string and executes it against
     * the database.  It is only for use with statements that should return
     * city records.  It will return the cities in a list of City
     * objects.
     *
     * @param statementString The SQL statement to be executed
     * @return An ArrayList of city objects
     */
    public ArrayList<City> executeCityStatement(String statementString)
    {
        ArrayList<City> cities = new ArrayList<>();
        try {
            // Create the SQL statement object for sending statements to the database
            Statement statement = connection.createStatement();
            // Execute the query
            ResultSet resultSet = statement.executeQuery(statementString);
            // Create City object and add it to the list for each result in the query
            while (resultSet.next()) {
                cities.add(new City(resultSet));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Query ExecuteCityStatement failed");
            System.out.println(e.getMessage());
        }
        return cities;
    }

    /**
     * This takes an SQL query in the form of a string and executes it against
     * the database.  It is only for use with statements that should return
     * Capital City records.  It will return the capital cities in a list of Capital City
     * objects.
     *
     * @param statementString The SQL statement to be executed
     * @return An ArrayList of city objects
     */
    public ArrayList<CapitalCity> executeCapitalCityStatement(String statementString)
    {
        ArrayList<CapitalCity> capitalCities = new ArrayList<>();
        try {
            // Create the SQL statement object for sending statements to the database
            Statement statement = connection.createStatement();
            // Execute the query
            ResultSet resultSet = statement.executeQuery(statementString);
            // Create Capital City object and add it to the list for each result in the query
            while (resultSet.next()) {
                capitalCities.add(new CapitalCity(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Query ExecuteCityStatement failed");
            System.out.println(e.getMessage());
        }
        return capitalCities;
    }

    /**
     * Use cases 1.1-1.3
     * Constructs the SQL query required and returns the result of the
     * query.
     *
     * @return An ordered list of countries in the world sorted by descending population
     */
    public ArrayList<Country> allCountriesIn(String areaFilter, String areaName)
    {
        String whereCondition = getWhereCondition(areaFilter, areaName);

        // Define the SQL query as a string
        String statementString = "SELECT code, country.name, continent, region, country.population, city.name AS capital\n" +
                "FROM country\n" +
                "    JOIN city ON country.capital = city.id\n" +
                "WHERE " + whereCondition +
                "ORDER BY country.population DESC";

        return executeCountryStatement(statementString);
    }

    /**
     * Use case 2.1-2.3
     * Constructs the SQL query required and returns the result of the
     * query.
     *
     * @return An ordered list of countries sorted by descending population
     */
    public ArrayList<Country> TopNCountriesIn(String areaFilter, String areaName, Integer n)
    {
        String whereCondition = getWhereCondition(areaFilter, areaName);

        // Define the SQL query as a string
        String statementString = "SELECT country.code, country.name, continent, region, country.population, city.name AS capital\n" +
                "FROM country\n" +
                "    JOIN city ON country.code = city.countrycode\n" +
                "    AND country.capital = city.id\n" +
                "WHERE " + whereCondition +
                "ORDER BY population DESC\n" +
                "LIMIT " + n;
        return executeCountryStatement(statementString);
    }

    /**
     * Use cases 3.1-3.5
     * Constructs an SQL query to fetch all cities in a given area, and executes the query.
     *
     * @return An ordered list of cities in a defined area sorted by descending population
     */
    public ArrayList<City> allCitiesIn(String areaFilter, String areaName)
    {
        String whereCondition = getWhereCondition(areaFilter, areaName);

        // Define the SQL query as a string
        String statementString = "SELECT city.name, district, city.population, country.name AS country\n" +
                "FROM city\n" +
                "    JOIN country ON city.countrycode = country.code\n" +
                "WHERE " + whereCondition +
                "ORDER BY city.population DESC;";

        return executeCityStatement(statementString);
    }

    /**
     * Use cases 4.1-4.5
     * Constructs an SQL query to fetch the top N populated cities in a specific area, and executes the query.
     *
     * @return An ordered list of cities in a defined area sorted by descending population
     */
    public ArrayList<City> topNCitiesIn(String areaFilter, String areaName, Integer n)
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

        return executeCityStatement(statementString);
    }


    /**
     * Use cases 5.1-5.3
     * Constructs an SQL query to fetch all capital cities in a specific area, and executes the query.
     *
     * @return An ordered list of capital cities in a specific area sorted by descending population
     */
    public ArrayList<CapitalCity> allCapitalCitiesIn(String areaFilter, String areaName)
    {
        String whereCondition = getWhereCondition(areaFilter, areaName);

        // Define the SQL query as a string
        String statementString = "SELECT city.name, city.population, country.name AS country \n" +
                "FROM city\n" +
                "    JOIN country ON city.countrycode = country.code\n" +
                "WHERE city.id = country.capital \n" +
                "AND " + whereCondition +
                "ORDER BY city.population DESC;";

        return executeCapitalCityStatement(statementString);
    }
}
