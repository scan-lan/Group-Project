package com.napier.sem;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class representing a database record which could be one of:
 *   - Country
 *   - City
 *   - Capital City
 *   - Residence Report
 *   - Population
 *   - Language
 * The type is denoted by the recordType field.
 * The possible fields consist of:
 *   countryCode, name, continent, region, country,
 *   district, capital, population, speakers, percentage,
 *   populationLivingInCities, percentageLivingInCities,
 *   populationNotLivingInCities, percentageNotLivingInCities
 */
public class Record
{
    // Private properties
    private String countryCode;
    private String name;
    private String continent;
    private String region;
    private String country;
    private String district;
    private String capital;
    private long population;
    private long speakers;
    private Integer percentage;
    private long populationLivingInCities;
    private double percentageLivingInCities;
    private long populationNotLivingInCities;
    private double percentageNotLivingInCities;
    private final String recordType;

    /**
     * Takes the result of an SQL query for a country or city extracts
     * the data we need and stores it in the class properties
     * @param result The result of a query that returns country records
     * @throws SQLException if a record can't be constructed due to a missing field
     */
    public Record(ResultSet result, String recordType) throws SQLException
    {
        this.recordType = recordType;

        if (result == null) return;

        switch (recordType)
        {
            case App.COUNTRY:
                countryCode = result.getString("code");
                name = result.getString("name");
                continent = result.getString("continent");
                region = result.getString("region");
                population = result.getLong("population");
                capital = result.getString("capital");
                break;
            case App.CAPITAL_CITY:
                name = result.getString("name");
                country = result.getString("country");
                region = result.getString("region");
                continent = result.getString("continent");
                population = result.getLong("population");
                break;
            case App.CITY:
                name = result.getString("name");
                country = result.getString("country");
                district = result.getString("district");
                population = result.getLong("population");
                break;
            case App.RESIDENCE_REPORT:
                name = result.getString("name");
                population = result.getLong("totalPopulation");
                populationLivingInCities = result.getLong("populationInCities");
                percentageLivingInCities = (double) populationLivingInCities / (double) population * 100;
                populationNotLivingInCities = result.getLong("populationNotInCities");
                percentageNotLivingInCities = (double) populationNotLivingInCities / (double) population * 100;
                break;
            case App.LANGUAGE:
                name = result.getString("name");
                speakers = result.getInt("speakers");
                percentage = result.getInt("percentage");
                break;
            case App.POPULATION:
                name = result.getString("name");
                population = result.getLong("population");
                break;
            default:
        }
    }

    // Country constructor, just used for testing purposes.
    public Record(String countryCode, String name, String continent, String region, long population, String capital)
    {
        this.countryCode = countryCode;
        this.name = name;
        this.continent = continent;
        this.region = region;
        this.population = population;
        this.capital = capital;
        recordType = App.COUNTRY;
    }

    // Capital city constructor, just used for testing purposes.
    public Record(String name, String country, String region, String continent, long population)
    {
        this.name = name;
        this.country = country;
        this.region = region;
        this.continent = continent;
        this.population = population;
        recordType = App.CAPITAL_CITY;
    }

    // City constructor, just used for testing purposes.
    public Record(String name, String country, String district, long population)
    {
        this.name = name;
        this.country = country;
        this.district = district;
        this.population = population;
        recordType = App.CITY;
    }

    // Residence Report constructor, just used for testing purposes.
    public Record(String name, long population, long populationLivingInCities, Double percentageLivingInCities, long populationNotLivingInCities, Double percentageNotLivingInCities)
    {
        this.name = name;
        this.population = population;
        this.populationLivingInCities = populationLivingInCities;
        this.percentageLivingInCities = percentageLivingInCities;
        this.populationNotLivingInCities = populationNotLivingInCities;
        this.percentageNotLivingInCities = percentageNotLivingInCities;
        recordType = App.RESIDENCE_REPORT;
    }

    // Language constructor, just used for testing purposes.
    public Record(String name, long speakers, Integer percentage)
    {
        this.name = name;
        this.speakers = speakers;
        this.percentage = percentage;
        recordType = App.LANGUAGE;
    }

    // Population constructor, just used for testing purposes.
    public Record(String name, long population)
    {
        this.name = name;
        this.population = population;
        recordType = App.POPULATION;
    }

    public String getName() { return this.name; }
    public String getContinent() { return this.continent; }
    public String getCountry() { return this.country; }
    public long getPopulation() { return this.population; }

    /**
     * Formats the country data in a consistent, readable manner
     * @return A string representing a country record
     */
    @Override
    public String toString()
    {
        String recordString = "Record.toString failed";
        switch (this.recordType)
        {
            case App.COUNTRY:
                recordString = String.format(App.HORIZONTAL_LINE + "\n" +
                                "Country code: %s | Name: %s | Continent: %s\n" +
                                "Region: %s | Population: %,d | Capital: %s",
                        this.countryCode,
                        this.name,
                        this.continent,
                        this.region,
                        this.population,
                        this.capital);
                break;
            case App.CAPITAL_CITY:
                recordString =  String.format(App.HORIZONTAL_LINE + "\n" +
                                "Name: %s | Country: %s | Population: %,d",
                        this.name,
                        this.country,
                        this.population);
                break;
            case App.CITY:
                recordString = String.format(App.HORIZONTAL_LINE + "\n" +
                                "Name: %s |  Country: %s \n" +
                                "District: %s |  Population: %,d",
                        this.name,
                        this.country,
                        this.district,
                        this.population);
                break;
            case App.RESIDENCE_REPORT:
                recordString = String.format(App.HORIZONTAL_LINE + "\n" +
                                "Area: %s | Total population: %,d\n" +
                                "Population living in Cities: %,d (%.2f%%)\n" +
                                "Population not living in Cities: %,d (%.2f%%)",
                        this.name,
                        this.population,
                        this.populationLivingInCities,
                        this.percentageLivingInCities,
                        this.populationNotLivingInCities,
                        this.percentageNotLivingInCities);
                break;
            case App.LANGUAGE:
                recordString = String.format(App.HORIZONTAL_LINE + "\n" +
                                "Language: %s |  Speakers: %,d | %% of world's population: %d%%",
                        this.name,
                        this.speakers,
                        this.percentage);
                break;
            case App.POPULATION:
                recordString = String.format(App.HORIZONTAL_LINE + "\n" +
                                "Area: %s |  Population: %,d",
                        this.name,
                        this.population);
                break;
            default:
                return null;
        }
        return recordString;
    }
}
