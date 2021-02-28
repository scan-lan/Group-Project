package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

public class App {
    // Report constants
    private final String CONTINENT = "continent";
    private final String REGION = "region";

    // City report constants
    private final String COUNTRY = "country";
    private final String DISTRICT = "district";

    // Connection to MySQL database.
    private Connection connection = null;

    public static void main(String[] args) {
        // Create new Application
        App app = new App();

        // Connect to database
        app.connect();

        // Create instance of the database access object
        DAO dao = new DAO(app.connection);

        // Run the test query
        // dao.testQuery();

        // Use-case 1.1
        // produce a report on all countries in the world organised by largest population to smallest
        // ArrayList<Country> countries = dao.allCountries();

        // Use-cases 1.2 and 1.3
        // produce a report on all countries in the world organised by largest population to smallest
        // ArrayList<Country> countries = dao.allCountries("region", "Southern and Central Asia");

        // Use-case 2.1
        // Run top 10 countries query against database
        // ArrayList<Country> countries = dao.TopNCountries(10);

        // Use-case 2.2
        // Run top N populated countries in specified continent query
        // ArrayList<Country> countries = dao.TopNCountriesContinent(5,"Asia");

        // Use-case 2.3
        // Run top N populated countries in specified region query
        // ArrayList<Country> countries = dao.TopNCountriesRegion(5,"Eastern Asia");

        // Use-case 3.1
        //produce a report on all cities in the world organised by largest population to smallest
        //ArrayList<City> cities = dao.allCities();

        // Use-case 3.2
        //produce a report on all cities in a defined area organised by largest population to smallest
        ArrayList<City> cities = dao.allCities("region", "Southern and Central Asia");



        // Display results
        // for (Country country : countries) System.out.println(country);
        for (City city : cities) System.out.println(city);

        // Disconnect from database
        app.disconnect();
    }

    /**
     * Connect to the MySQL world database.
     */
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                connection = DriverManager.getConnection("jdbc:mysql://database:3306/world?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException e)
            {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(e.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
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