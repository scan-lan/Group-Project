package com.napier.sem;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class representing a country record containing the data
 * we show when displaying a country.
 * This consists of:
 *     code, name, continent, region, population, capital
 */
public class Country
{
    // Private properties
    private String code;
    private String name;
    private String continent;
    private String region;
    private Integer population;
    private String capital;


    /**
     * Takes the result of an executed SQL query, extracts
     * the data we need and stores it in the class properties
     * @param result The result of a query with a country as a result
     * @throws SQLException
     */
    public Country(ResultSet result) throws SQLException {
        code = result.getString("code");
        name = result.getString("name");
        continent = result.getString("continent");
        region = result.getString("region");
        population = result.getInt("population");
        capital = result.getString("capital");
    }

    /**
     * Formats the country data in a consistent, readable manner
     * @return
     */
    public String toString()
    {
        return String.format("Country code: %s | Name: %s | Continent: %s\n" +
                        "Region: %s | Population: %s | Capital: %s\n" +
                        "-------------------------------------------------------",
                this.code,
                this.name,
                this.continent,
                this.region,
                this.population,
                this.capital);
    }
}
