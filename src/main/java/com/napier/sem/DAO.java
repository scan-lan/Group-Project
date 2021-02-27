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

    public DAO(Connection connection)
    {
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
    private ArrayList<Country> ExecuteCountryStatement(String statementString)
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
        } catch (SQLException e)
        {
            System.out.println("Query TopNCountries failed");
            System.out.println(e.getMessage());
        }
        return countries;
    }

    /**
     * Constructs the SQL query required and returns the result of the
     * query.
     * @return An ordered list of countries sorted by descending population
     */
    public ArrayList<Country> TopNCountries(Integer n)
    {
        // Define the SQL query as a string
        String statementString = "SELECT code, name, continent, region, population, (\n" +
                    "    SELECT name\n" +
                    "    FROM city ci\n" +
                    "    WHERE countrycode = co.code\n" +
                    "        AND ci.id = co.capital\n" +
                    "    ) AS capital\n" +
                    "FROM country co\n" +
                    "ORDER BY population DESC\n" +
                    "LIMIT " + n;
        return ExecuteCountryStatement(statementString);
    }

    /**
     * Constructs the SQL query required and returns the result of the
     * query.
     * @return An ordered list of countries in a specified continent sorted by descending population
     */
    public ArrayList<Country> TopNCountriesContinent(Integer n,String continentName)
    {
        // Define the SQL query as a string
        String statementString = "SELECT code, name, continent, region, population, (\n" +
                "    SELECT name\n" +
                "    FROM city ci\n" +
                "    WHERE countrycode = co.code\n" +
                "        AND ci.id = co.capital\n" +
                "    ) AS capital\n" +
                "FROM country co\n" +
                "WHERE co.continent = '"+continentName+"'\n" +
                "ORDER BY population DESC\n" +
                "LIMIT " + n;

        return ExecuteCountryStatement(statementString);
    }

    public ArrayList<Country> TopNCountriesRegion(Integer n,String regionName)
    {
        // Define the SQL query as a string
        String statementString = "SELECT code, name, continent, region, population, (\n" +
                "    SELECT name\n" +
                "    FROM city ci\n" +
                "    WHERE countrycode = co.code\n" +
                "        AND ci.id = co.capital\n" +
                "    ) AS capital\n" +
                "FROM country co\n" +
                "WHERE co.region = '"+regionName+"'\n" +
                "ORDER BY population DESC\n" +
                "LIMIT " + n;

        return ExecuteCountryStatement(statementString);
    }

    /**
     * This is a sanity-check query just ensuring that the database can be accessed
     */
    public void testQuery()
    {
        try
        {
            // Create an SQL statement
            Statement stmt = connection.createStatement();
            // Create string for SQL statement
            String strSelect = "SELECT code, name, continent\n" +
                    "FROM country\n" +
                    "LIMIT 5";
            // Execute SQL statement
            ResultSet resultSet = stmt.executeQuery(strSelect);
            while (resultSet.next())
            {
                System.out.println(resultSet.getString("code"));
                System.out.println(resultSet.getString("name"));
                System.out.println(resultSet.getString("continent"));
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
        }
    }
}


