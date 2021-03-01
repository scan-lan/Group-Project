package com.napier.sem;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class representing a city record containing the data
 * we show when displaying a city.
 * This consists of:
 * name, country, district, population.
 */
public class City {
    // Private properties

    private String name;
    private String country;
    private String district;
    private Integer population;



    /**
     * Takes the result of an executed SQL query, extracts
     * the data we need and stores it in the class properties
     *
     * @param result The result of a query with a city as a result
     * @throws SQLException
     */
    public City(ResultSet result) throws SQLException {

        name = result.getString("name");
        country = result.getString("country");
        district = result.getString("district");
        population = result.getInt("population");


    }

    /**
     * Formats the city data in a consistent, readable manner
     *
     * @return
     */
    public String toString() {
        return String.format("\n Name: %s |  Country: %s \n" +
                        "\n District: %s |  Population: %s\n" +

                        "-------------------------------------------------------",

                this.name,
                this.country,
                this.district,
                this.population);
    }
}
