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
    static final String LANGUAGE = "language";
    static final String POPULATION_RESIDENCE_REPORT = "population residence report";
    static final String POPULATION = "population";

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
        // ArrayList<Record> records = dao.allCountriesIn(WORLD, "");
        // Produce a report on all countries in a continent organised by largest population to smallest
        // ArrayList<Record> records = dao.allCountriesIn(CONTINENT, "Africa");
        // Produce a report on all countries in a region organised by largest population to smallest
        // ArrayList<Record> records = dao.allCountriesIn(REGION, "Southern and Central Asia");

        // Use-case 2.1-2.3
        // Run top N countries query against database
        // ArrayList<Record> records = dao.topNCountriesIn(WORLD, "", 10);
        // Run top N populated countries in specified continent query
        // ArrayList<Record> records = dao.topNCountriesIn(CONTINENT, "Asia", 5);
        // Run top N populated countries in specified region query
        // ArrayList<Record> records = dao.topNCountriesIn(REGION, "Eastern Asia", 5);

        // Use-case 3.1-3.5
        // Produce a report on all cities in the world organised by largest population to smallest
        // ArrayList<Record> records = dao.allCitiesIn(WORLD, "");
        // Produce a report on all cities in a continent organised by largest population to smallest
        // ArrayList<Record> records = dao.allCitiesIn(CONTINENT, "Europe");
        // Produce a report on all cities in a region organised by largest population to smallest
        // ArrayList<Record> records = dao.allCitiesIn(REGION, "Caribbean");
        // Produce a report on all cities in a country organised by largest population to smallest
        // ArrayList<Record> records = dao.allCitiesIn(COUNTRY, "India");
        // Produce a report on all cities in a district organised by largest population to smallest
        // ArrayList<Record> records = dao.allCitiesIn(DISTRICT, "Alagoas");

        // Use-case 4.1-4.5
        // Produce a report on the top N populated cities in the world where N is provided by the user
        // ArrayList<Record> records = dao.topNCitiesIn(WORLD, "", 5);
        // Produce a report on the top N populated cities in a continent where N is provided by the user
        // ArrayList<Record> records = dao.topNCitiesIn(CONTINENT, "Europe", 5);
        // Produce a report on the top N populated cities in a region where N is provided by the user
        // ArrayList<Record> records = dao.topNCitiesIn(REGION, "Caribbean", 5);
        // Produce a report on the top N populated cities in a country where N is provided by the user
        // ArrayList<Record> records = dao.topNCitiesIn(COUNTRY, "China", 5);
        // Produce a report on the top N populated cities in a district where N is provided by the user
        // ArrayList<Record> records = dao.topNCitiesIn(DISTRICT, "Scotland", 6);

        // Use-case 5.1-5.3
        // Produce a report on all capital cities in the world organised by largest population to smallest
        // ArrayList<Record> records = dao.allCapitalCitiesIn(WORLD, "");
        // Produce a report on all capital cities in a continent organised by largest population to smallest
        // ArrayList<Record> records = dao.allCapitalCitiesIn(CONTINENT, "Asia");
        // Produce a report on all capital cities in a region organised by largest population to smallest
        // ArrayList<Record> records = dao.allCapitalCitiesIn(REGION, "Caribbean");

        // Use-Cases 6.1-6.3
        // 6.1 - Produce a report on the top N capital cities in the world organised by largest population to smallest
        // ArrayList<Record> records = dao.topNCapitalCitiesIn(WORLD, "",5);
        // 6.2 - Produce a report on the top N capital cities in a continent organised by largest population to smallest
        ArrayList<Record> records = dao.topNCapitalCitiesIn(CONTINENT, "Asia", 5);
        // 6.3 - Produce a report on the top N capital cities in a region organised by largest population to smallest
        // ArrayList<Record> records = dao.topNCapitalCitiesIn(REGION, "Caribbean", 7);

        // Use-Cases 7.1-7.3
        // 7.1 - Produce a report on the population of people, people living in cities, and people not living in cities in each continent
        // ArrayList<Record> populationCities = dao.populationCitiesAndNonCities(CONTINENT, "Europe");
        // 7.2 - Produce a report on the population of people, people living in cities, and people not living in cities in each region
        // ArrayList<Record> populationCities = dao.populationCitiesAndNonCities(REGION, "Caribbean");
        // 7.3 - Produce a report on the population of people, people living in cities, and people not living in cities in each country
        //ArrayList<Record> populationCities = dao.populationCitiesAndNonCities(COUNTRY, "France");

        // Use-Cases 8.1-8.6
        // 8.1 - Produce a report on the population of the world
        // ArrayList<Record> records = dao.populationOf(WORLD, "");
        // 8.2 - Produce a report on the population of a continent
        // ArrayList<Record> records = dao.populationOf(CONTINENT, "Europe");
        // 8.3 - Produce a report on the population of a region
        // ArrayList<Record> records = dao.populationOf(REGION, "Caribbean");
        // 8.4 - Produce a report on the population of a country
        // ArrayList<Record> records = dao.populationOf(COUNTRY, "Spain");
        // 8.5 - Produce a report on the population of a district
        // ArrayList<Record> records = dao.populationOf(DISTRICT, "Scotland");
        // 8.6 - Produce a report on the population of a city
        // ArrayList<Record> records = dao.populationOf(CITY, "London");

        // Use-case 9.1
        // Produce a report on the number of who speak the following specific languages from greatest number to smallest, including the percentage of the world population
        // ArrayList<Record> languages = dao.languageReport();

        //Display results
        for (Record record : records) System.out.println(record);

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
            System.out.println("Connecting to database...");
            try
            {
                if (location == null) { return null; }

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
