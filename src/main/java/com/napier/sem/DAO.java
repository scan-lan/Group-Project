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
    private static final String WORLD = "";
    private static final String CONTINENT = "continent";
    private static final String REGION = "region";
    private static final String COUNTRY = "country";
    private static final String DISTRICT = "district";

    public DAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * This takes an SQL query in the form of a string and executes it against
     * the database.  It is only for use with statements that should return
     * country records.  It will return the countries in a list of Country
     * objects.
     * @param statementString The SQL statement to be executed
     * @return An ArrayList of country objects
     */
    private ArrayList<Country> ExecuteCountryStatement(String statementString) {
        ArrayList<Country> countries = new ArrayList<>();
        try {
            // Create the SQL statement object for sending statements to the database
            Statement statement = connection.createStatement();
            // Execute the query
            ResultSet resultSet = statement.executeQuery(statementString);
            // Create Country object and add it to the list for each result in the query
            while (resultSet.next()) {
                countries.add(new Country(resultSet));
            }
        } catch (SQLException e) {
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
    private ArrayList<City> ExecuteCityStatement(String statementString) {
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
        } catch (SQLException e) {
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
    private ArrayList<CapitalCity> ExecuteCapitalCityStatement(String statementString) {
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
    public ArrayList<Country> allCountriesIn(String area, String areaName) {
        String whereClause;

        switch (area) {
            case CONTINENT:
                whereClause = "WHERE country.continent = '" + areaName + "'\n";
                break;
            case REGION:
                whereClause = "WHERE country.region = '" + areaName + "'\n";
                break;
            default:
                whereClause = "";
                break;
        }

        // Define the SQL query as a string
        String statementString = "SELECT code, country.name, continent, region, country.population, city.name AS capital\n" +
                "FROM country\n" +
                "    JOIN city ON country.capital = city.id\n" +
                whereClause +
                "ORDER BY country.population DESC";

        return ExecuteCountryStatement(statementString);

    }


    /**
     * Use case 2.1-2.3
     * Constructs the SQL query required and returns the result of the
     * query.
     *
     * @return An ordered list of countries sorted by descending population
     */
    public ArrayList<Country> TopNCountriesIn(String area, String areaName, Integer n) {
        String whereClause;

        switch (area) {
            case CONTINENT:
                whereClause = "WHERE country.continent = '" + areaName + "'\n";
                break;
            case REGION:
                whereClause = "WHERE country.region = '" + areaName + "'\n";
                break;
            default:
                whereClause = "";
                break;
        }

        // Define the SQL query as a string
        String statementString = "SELECT country.code, country.name, continent, region, country.population, city.name AS capital\n" +
                "FROM country\n" +
                "    JOIN city ON country.code = city.countrycode\n" +
                "    AND country.capital = city.id\n" +
                whereClause +
                "ORDER BY population DESC\n" +
                "LIMIT " + n;
        return ExecuteCountryStatement(statementString);
    }

    /**
     * Use cases 3.1-3.5
     * Constructs an SQL query to fetch all cities in a given area, and executes the query.
     *
     * @return An ordered list of cities in a defined area sorted by descending population
     */
    public ArrayList<City> allCitiesIn(String area, String areaName) {
        String whereClause;

        switch (area) {
            case CONTINENT:
                whereClause = "WHERE country.continent = '" + areaName + "'\n";
                break;
            case REGION:
                whereClause = "WHERE country.region = '" + areaName + "'\n";
                break;
            case COUNTRY:
                whereClause = "WHERE country.name = '" + areaName + "'\n";
                break;
            case DISTRICT:
                whereClause = "WHERE city.district = '" + areaName + "'\n";
                break;
            default:
                whereClause = "";
                break;
        }

        // Define the SQL query as a string
        String statementString = "SELECT city.name, district, city.population, country.name AS country\n" +
                "FROM city\n" +
                "    JOIN country ON city.countrycode = country.code\n" +
                whereClause +
                "ORDER BY city.population DESC;";

        return ExecuteCityStatement(statementString);
    }

    /**
     * Use cases 4.1-4.5
     * Constructs an SQL query to fetch the top N populated cities in a specific area, and executes the query.
     *
     * @return An ordered list of cities in a defined area sorted by descending population
     */
    public ArrayList<City> topNCitiesIn(String area, String areaName, Integer n)
    {
        String whereClause;

        switch (area)
        {
            case CONTINENT:
                whereClause = "AND country.continent = '" + areaName + "'\n";
                break;
            case REGION:
                whereClause = "AND country.region = '" + areaName + "'\n";
                break;
            case COUNTRY:
                whereClause = "AND country.name = '" + areaName + "'\n";
                break;
            case DISTRICT:
                whereClause = "AND city.district = '" + areaName + "'\n";
                break;
            default:
                whereClause = "";
                break;
        }

        // Define the SQL query as a string
        String statementString = "SELECT city.name, district, city.population, country.name AS country\n" +
                "FROM city\n" +
                "    JOIN country ON city.countrycode = country.code\n" +
                "WHERE city.population > 0 \n" +
                whereClause +
                "ORDER BY city.population DESC \n" +
                "LIMIT " + n;

        return ExecuteCityStatement(statementString);
    }


    /**
     * Use cases 5.1-5.3
     * Constructs an SQL query to fetch all capital cities in a specific area, and executes the query.
     *
     * @return An ordered list of captial cities in a specific area sorted by descending population
     */
    public ArrayList<CapitalCity> allCapitalCitiesIn(String area, String areaName) {
        String whereClause;

        switch (area) {
            case CONTINENT:
                whereClause = "AND country.continent = '" + areaName + "'\n";
                break;
            case REGION:
                whereClause = "AND country.region = '" + areaName + "'\n";
                break;
            default:
                whereClause = "";
                break;
        }

        // Define the SQL query as a string
        String statementString = "SELECT city.name, city.population, country.name AS country \n" +
                "FROM city\n" +
                "    JOIN country ON city.countrycode = country.code\n" +
                "WHERE city.id = country.capital \n" +
                whereClause +
                "ORDER BY city.population DESC;";

        return ExecuteCapitalCityStatement(statementString);
    }
}
