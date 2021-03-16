package com.napier.sem;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class representing a city record containing the data
 * we show when displaying a city.
 * This consists of:
 * name, country, district, population.
 */
public class CapitalCity {
    // Private properties
    private String name;
    private String country;
    private Integer population;


    /**
     * Takes the result of an executed SQL query, extracts
     * the data we need and stores it in the class properties
     *
     * @param result The result of a query with a city as a result
     * @throws SQLException
     */
    public CapitalCity(ResultSet result) throws SQLException {
        name = result.getString("name");
        country = result.getString("country");
        population = result.getInt("population");
    }

    /**
     * Formats the capital city data in a consistent, readable manner
     *
     * @return
     */
    public String toString() {
        return String.format("Name: %s |  Country: %s |  Population: %s\n" +
                        "-------------------------------------------------------",
                this.name,
                this.country,
                this.population);
    }
}
