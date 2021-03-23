package com.napier.sem;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class representing a city record containing the data
 * we show when displaying a city.
 * This consists of:
 * name, country, district, population.
 */
public class City
{
    // Private properties - Removed private so tests would work, only work around we could think of for now.
    private final String name;
    private final String country;
    private final String district;
    private final Integer population;

    /**
     * Takes the result of an executed SQL query, extracts
     * the data we need and stores it in the class properties
     *
     * @param result The result of a query with a city as a result
     * @throws SQLException
     */
    public City(ResultSet result) throws SQLException
    {
        name = result.getString("name");
        country = result.getString("country");
        district = result.getString("district");
        population = result.getInt("population");
    }

    public City(String name, String country, String district, Integer population)
    {
        this.name = name;
        this.country = country;
        this.district = district;
        this.population = population;
    }

    public String getName() { return this.name; }
    public String getCountry() { return this.country; }
    public String getDistrict() { return this.district; }
    public Integer getPopulation() { return this.population; }

    /**
     * Formats the city data in a consistent, readable manner
     *
     * @return A string representing a city record
     */
    public String toString()
    {
        return String.format("Name: %s |  Country: %s \n" +
                        "District: %s |  Population: %s\n" +
                        "-------------------------------------------------------",
                this.name,
                this.country,
                this.district,
                this.population);
    }
}
