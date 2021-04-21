package com.napier.sem;

import java.sql.*;

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
    static final String LANGUAGE = "language";
    static final String POPULATION_RESIDENCE_REPORT = "population residence report";
    static final String POPULATION = "population";

    static final String HORIZONTAL_LINE = "------------------------------------------------------------" +
            "------------------------------------------------------------";
    static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";

    // Connection to MySQL database
    public static Connection connection;

    public static void main(String[] args)
    {
        // Connect to database
        if (args.length < 1)
        {
            connection = connect("localhost:33061", DATABASE_DRIVER, false);
        }
        else
        {
            connection = connect(args[0], DATABASE_DRIVER, false);
        }

        // Create instance of the database access object
        DAO dao = new DAO(connection);

        UserPrompt prompt = new UserPrompt(dao);

        prompt.start();

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
            return null;
        }

        int retries = 60;
        for (int i = 1; i <= retries; ++i)
        {
            System.out.println(App.HORIZONTAL_LINE +  "\nConnecting to database...");
            try
            {
                if (location == null) { return null; }

                // Connect to database
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://" + location + "/world?allowPublicKeyRetrieval=true&useSSL=false",
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
