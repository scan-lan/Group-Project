package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

public class App
{
    // Connection to MySQL database.
    private Connection connection = null;

    public static void main(String[] args)
    {
        // Create new Application
        App app = new App();

        // Connect to database
        app.connect();

        // Create instance of the report object
        Report report = new Report(app.connection);

        // Run the test query
        // report.testQuery();

        // Run top 10 countries query against database
        ArrayList<Country> countries = report.TopNCountries(10);

        // Display results
        for (Country country : countries) System.out.println(country);

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