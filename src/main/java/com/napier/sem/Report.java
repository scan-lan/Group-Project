package com.napier.sem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Report
{
    // Private properties
    private final Connection con;

    public Report(Connection con)
    {
        this.con = con;
    }

    /**
     * Executes an SQL query against the database and builds an ArrayList
     * of Country objects from the query result
     * @return An ordered list of countries sorted by descending population
     */
    public ArrayList<Country> TopNCountries(Integer n)
    {
        ArrayList<Country> countries = new ArrayList<>();
        try
        {
            // Create the SQL statement object for sending statements to the database
            Statement statement = con.createStatement();
            // Create the statement you want to send as a string
            String statementString = "SELECT code, name, continent, region, population, (\n" +
                    "    SELECT name\n" +
                    "    FROM city ci\n" +
                    "    WHERE countrycode = co.code\n" +
                    "        AND ci.id = co.capital\n" +
                    "    ) AS capital\n" +
                    "FROM country co\n" +
                    "ORDER BY population DESC\n" +
                    "LIMIT " + n;
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

    public void testQuery()
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
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


