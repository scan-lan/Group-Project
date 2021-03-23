package com.napier.sem;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class representing a city record containing the data
 * we show when displaying a city.
 * This consists of:
 * name, country, district, population.
 */
public class CapitalCity
{
    // Private properties
    private final String name;
    private final String country;
    private final String region;
    private final String continent;
    private final Integer population;


    /**
     * Takes the result of an executed SQL query, extracts
     * the data we need and stores it in the class properties
     *
     * @param result The result of a query with a city as a result
     * @throws SQLException
     */
    public CapitalCity(ResultSet result) throws SQLException
    {
        name = result.getString("name");
        country = result.getString("country");
        region = result.getString("region");
        continent = result.getString("continent");
        population = result.getInt("population");
    }

    public CapitalCity(String name, String country, String region, String continent, Integer population)
    {
        this.name = name;
        this.country = country;
        this.region = region;
        this.continent = continent;
        this.population = population;
    }

    public String getName() { return this.name; }
    public String getCountry() { return this.country; }
    public String getRegion() { return this.region; }
    public String getContinent() { return this.continent; }
    public Integer getPopulation() { return this.population; }

    /**
     * Formats the capital city data in a consistent, readable manner
     *
     * @return A string representing a capital city
     */
    public String toString()
    {
        return String.format("Name: %s | Country: %s | Population: %s\n" +
                        "-------------------------------------------------------",
                this.name,
                this.country,
                this.population);
    }
}
