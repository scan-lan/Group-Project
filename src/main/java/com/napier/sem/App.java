package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

public class App
{
    // Constants
    static final String WORLD = "world";
    static final String CONTINENT = "continent";
    static final String REGION = "region";
    static final String COUNTRY = "country";
    static final String DISTRICT = "district";
    static final String CAPITAL_CITY = "capital city";
    static final String CITY = "city";

    static final String databaseDriver = "com.mysql.cj.jdbc.Driver";

    // Connection to MySQL database
    public static Connection connection;

    public static void main(String[] args) {
        // Create new Application
        App app = new App();

        // Connect to database
        if (args.length < 1)
        {
            connection = connect("localhost:33061", databaseDriver, false);
        }
        else
        {
            connection = connect(args[0], databaseDriver, false);
        }

        // Create instance of the database access object
        DAO dao = new DAO(connection);

        // Use-case 1.1-1.3
        // Produce a report on all countries in the world organised by largest population to smallest
        ArrayList<Record> countries = dao.allCountriesIn(WORLD, "");
        // Produce a report on all countries in a continent organised by largest population to smallest
        // ArrayList<Country> countries = dao.allCountriesIn(CONTINENT, "Africa");
        // Produce a report on all countries in a region organised by largest population to smallest
        // ArrayList<Country> countries = dao.allCountriesIn(REGION, "Southern and Central Asia");

        // Use-case 2.1-2.3
        // Run top N countries query against database
        // ArrayList<Country> countries = dao.topNCountriesIn(WORLD, "", 10);
        // Run top N populated countries in specified continent query
        // ArrayList<Country> countries = dao.topNCountriesIn(CONTINENT, "Asia", 5);
        // Run top N populated countries in specified region query
        // ArrayList<Country> countries = dao.topNCountriesIn(REGION, "Eastern Asia", 5);

        // Use-case 3.1-3.5
        // Produce a report on all cities in the world organised by largest population to smallest
        // ArrayList<Record> cities = dao.allCitiesIn(WORLD, "");
        // Produce a report on all cities in a continent organised by largest population to smallest
        // ArrayList<Record> cities = dao.allCitiesIn(CONTINENT, "Europe");
        // Produce a report on all cities in a region organised by largest population to smallest
        // ArrayList<Record> cities = dao.allCitiesIn(REGION, "Caribbean");
        // Produce a report on all cities in a country organised by largest population to smallest
        // ArrayList<Record> cities = dao.allCitiesIn(COUNTRY, "India");
        // Produce a report on all cities in a district organised by largest population to smallest
        // ArrayList<Record> cities = dao.allCitiesIn(DISTRICT, "Alagoas");

        // Use-case 4.1-4.5
        // Produce a report on the top N populated cities in the world where N is provided by the user
        // ArrayList<Record> cities = dao.topNCitiesIn(WORLD, "", 5);
        // Produce a report on the top N populated cities in a continent where N is provided by the user
        // ArrayList<Record> cities = dao.topNCitiesIn(CONTINENT, "Europe", 5);
        // Produce a report on the top N populated cities in a region where N is provided by the user
        // ArrayList<Record> cities = dao.topNCitiesIn(REGION, "Caribbean", 5);
        // Produce a report on the top N populated cities in a country where N is provided by the user
        // ArrayList<Record> cities = dao.topNCitiesIn(COUNTRY, "China", 5);
        // Produce a report on the top N populated cities in a district where N is provided by the user
        // ArrayList<Record> cities = dao.topNCitiesIn(DISTRICT, "Scotland", 6);


        // Use-case 5.1-5.3
        // Produce a report on all capital cities in the world organised by largest population to smallest
        // ArrayList<Record> capitalCities = dao.allCapitalCitiesIn(WORLD, "");
        // Produce a report on all capital cities in a continent organised by largest population to smallest
        // ArrayList<Record> capitalCities = dao.allCapitalCitiesIn(CONTINENT, "Asia");
        // Produce a report on all capital cities in a region organised by largest population to smallest
        // ArrayList<Record> capitalCities = dao.allCapitalCitiesIn(REGION, "Caribbean");

        // Display Country results
        for (Record record : countries) System.out.println(record);

        // Display City results
        // for (Record city : cities) System.out.println(city);

        // Display Capital City results
        // for (Record capitalCity : capitalCities) System.out.println(capitalCity);

        // Disconnect from database
        disconnect(connection);
    }

    /**
     * Connect to the MySQL world database.
     * @return a database connection object
     */
    public static Connection connect(String location, String databaseDriver, boolean isTest)
    {
        try
        {
            // Load Database driver
            Class.forName(databaseDriver);
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
        }

        int retries = 60;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Connect to database
                Connection connection = DriverManager.getConnection("jdbc:mysql://" + location + "/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root",
                        "example");
                System.out.println("Successfully connected");
                return connection;
            }
            catch (SQLException e)
            {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(e.getMessage());
            }
            if (!isTest)
            {
                try
                {
                    // Wait a bit before next retry
                    Thread.sleep(5000);
                }
                catch (InterruptedException ie)
                {
                    System.out.println("Thread interrupted? Should not happen.");
                }
            }
        }
        return null;
    }

    /**
     * Disconnect from the MySQL database.
     */
    public static void disconnect(Connection connection)
    {
        if (connection != null)
        {
            try
            {
                // Close connection
                connection.close();
            }
            catch (SQLException e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }
}
