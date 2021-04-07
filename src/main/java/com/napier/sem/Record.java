package com.napier.sem;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class representing a database record which could be one of:
 *   - Country
 *   - City
 *   - Capital City
 *   - Language
 * The type is denoted by the recordType field.
 * The possible fields consist of:
 *   code, name, continent, region, country,
 *   district, capital, population, language,
 *   speakers, percentage
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
    private String language;
    private long speakers;
    private Integer percentage;
    private long populationLivingInCities;
    private double percentageLivingInCities;
    private long populationNotLivingInCities;
    private double percentageNotLivingInCities;
    private final String recordType;


    /**
     * Constructors
     *
     * Takes the result of an SQL query for a country or city extracts
     * the data we need and stores it in the class properties
     * @param result The result of a query that returns country records
     * @throws SQLException
     */
    public Record(ResultSet result, String recordType) throws SQLException
    {
        this.recordType = recordType;
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
            case App.POPULATION_RESIDENCE_REPORT:
                name = result.getString("name");
                population = result.getLong("totalPopulation");
                populationLivingInCities = result.getLong("populationInCities");
                percentageLivingInCities = (double) populationLivingInCities / (double) population * 100;
                populationNotLivingInCities = result.getLong("populationNotInCities");
                percentageNotLivingInCities = (double) populationNotLivingInCities / (double) population * 100;
                break;
            case App.LANGUAGE:
                language = result.getString("language");
                speakers = result.getInt("speakers");
                percentage = result.getInt("percentage");
                break;
            case App.POPULATION:
                name = result.getString("area");
                population = result.getLong("population");
                break;
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

    // Language constructor, just used for testing purposes.
    public Record(String language, Integer speakers, Integer percentage)
    {
        this.language = language;
        this.speakers = speakers;
        this.percentage = percentage;
        recordType = App.LANGUAGE;
    }

    public String getCountryCode() { return this.countryCode; }
    public String getName() { return this.name; }
    public String getContinent() { return this.continent; }
    public String getRegion() { return this.region; }
    public String getCountry() { return this.country; }
    public String getDistrict() { return this.district; }
    public long getPopulation() { return this.population; }
    public String getCapital() { return this.capital; }
    public String getLanguage() { return this.language; }
    public long getSpeakers() { return this.speakers; }
    public Integer getPercentage() { return this.percentage; }
    public String getRecordType() { return this.recordType; }

    /**
     * Formats the country data in a consistent, readable manner
     * @return A string representing a country record
     */
    public String toString()
    {
        String recordString = "Record.toString failed";
        switch (this.recordType)
        {
            case App.COUNTRY:
                recordString = String.format("Country code: %s | Name: %s | Continent: %s\n" +
                                "Region: %s | Population: %,d | Capital: %s\n" +
                                "-------------------------------------------------------",
                        this.countryCode,
                        this.name,
                        this.continent,
                        this.region,
                        this.population,
                        this.capital);
                break;
            case App.CAPITAL_CITY:
                recordString =  String.format("Name: %s | Country: %s | Population: %,d\n" +
                                "-------------------------------------------------------",
                        this.name,
                        this.country,
                        this.population);
                break;
            case App.CITY:
                recordString = String.format("Name: %s |  Country: %s \n" +
                                "District: %s |  Population: %,d\n" +
                                "-------------------------------------------------------",
                        this.name,
                        this.country,
                        this.district,
                        this.population);
                break;
            case App.POPULATION_RESIDENCE_REPORT:
                recordString = String.format("Area: %s | Total population: %,d\n" +
                                "Population living in Cities: %,d (%.2f%%)\n" +
                                "Population not living in Cities: %,d (%.2f%%)\n" +
                                "-------------------------------------------------------",
                        this.name,
                        this.population,
                        this.populationLivingInCities,
                        this.percentageLivingInCities,
                        this.populationNotLivingInCities,
                        this.percentageNotLivingInCities);
                break;
            case App.LANGUAGE:
                recordString = String.format("Language: %s |  Speakers: %,d | %% of world's population: %d%% \n" +
                                "-------------------------------------------------------",
                        this.language,
                        this.speakers,
                        this.percentage);
                break;
            case App.POPULATION:
                recordString = String.format("Area: %s |  Population: %,d\n" +
                                "-------------------------------------------------------",
                        this.name,
                        this.population);
        }
        return recordString;
    }
}
