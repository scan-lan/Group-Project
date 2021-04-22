package com.napier.sem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Record_UnitTests
{
    /**
     * Tests for the Record class
     */
    // Tests that when a country object is constructed, the getters and toString
    // return the same fields that we passed in.
    @Test
    public void Record_testGettersAndToStringForCountry()
    {
        // given
        String countryCode = "SCT";
        String name = "Scotland";
        String continent = "Europe";
        String region = "Great Britain";
        String capital = "Edinburgh";
        long population = 6000000L;
        String expectedRecordType = App.COUNTRY;
        String expectedToStringOutput = App.HORIZONTAL_LINE + "\n" +
                "Country code: SCT | Name: Scotland | Continent: Europe\n" +
                "Region: Great Britain | Population: 6,000,000 | Capital: Edinburgh";


        // when
        Record record = new Record(countryCode, name, continent, region, population, capital);

        // then
        assertEquals(countryCode, record.getCountryCode());
        assertEquals(name, record.getName());
        assertEquals(continent, record.getContinent());
        assertEquals(region, record.getRegion());
        assertEquals(population, record.getPopulation());
        assertEquals(capital, record.getCapital());
        assertEquals(expectedRecordType, record.getRecordType());
        assertEquals(expectedToStringOutput, record.toString());
    }

    // Tests that when a capital city object is constructed, the getters and toString
    // return the same fields that we passed in.
    @Test
    public void Record_testGettersAndToStringForCapitalCity()
    {
        // given
        String name = "Glasgow";
        String country = "Scotland";
        String region = "Great Britain";
        String continent = "Europe";
        long population = 6000000L;
        String expectedRecordType = App.CAPITAL_CITY;
        String expectedToStringOutput = App.HORIZONTAL_LINE + "\n" +
                "Name: Glasgow | Country: Scotland | Population: 6,000,000";

        // when
        Record capitalCity = new Record(name, country, region, continent, population);

        // then
        assertEquals(name, capitalCity.getName());
        assertEquals(country, capitalCity.getCountry());
        assertEquals(region, capitalCity.getRegion());
        assertEquals(continent, capitalCity.getContinent());
        assertEquals(population, capitalCity.getPopulation());
        assertEquals(expectedRecordType, capitalCity.getRecordType());
        assertEquals(expectedToStringOutput, capitalCity.toString());
    }

    // Tests that when a city object is constructed, the getters and toString
    // return the same fields that we passed in.
    @Test
    public void Record_testGettersAndToStringForCity()
    {
        // given
        String name = "Wishae";
        String country = "Scotland";
        String district = "North Lanarkshire";
        long population = 10000L;
        String expectedRecordType = App.CITY;
        String expectedToStringOutput = App.HORIZONTAL_LINE + "\n" +
                "Name: Wishae |  Country: Scotland \n" +
                "District: North Lanarkshire |  Population: 10,000";

        // when
        Record city = new Record(name, country, district, population);

        // then
        assertEquals(name, city.getName());
        assertEquals(country, city.getCountry());
        assertEquals(district, city.getDistrict());
        assertEquals(population, city.getPopulation());
        assertEquals(expectedRecordType, city.getRecordType());
        assertEquals(expectedToStringOutput, city.toString());
    }

    // Tests that when a population residence report object is constructed, the getters and toString
    // return the same fields that we passed in.
    @Test
    public void Record_testGettersAndToStringForPopulationResidenceReport()
    {
        // given
        String name = "East Kilbride";
        Long population = 7000L;
        Long populationLivingInCities = 6999L;
        Double percentageLivingInCities = 99.99D;
        Long populationNotLivingInCities = 1L;
        Double percentageNotLivingInCities = 0.01D;
        String expectedRecordType = App.POPULATION_RESIDENCE_REPORT;
        String expectedToStringOutput = App.HORIZONTAL_LINE + "\n" +
                "Area: East Kilbride | Total population: 7,000\n" +
                "Population living in Cities: 6,999 (99.99%)\n" +
                "Population not living in Cities: 1 (0.01%)";

        // when
        Record populationResidenceReport = new Record(name, population, populationLivingInCities, percentageLivingInCities, populationNotLivingInCities, percentageNotLivingInCities);

        // then
        assertEquals(name, populationResidenceReport.getName());
        assertEquals(population, populationResidenceReport.getPopulation());
        assertEquals(populationLivingInCities, populationResidenceReport.getPopulationLivingInCities());
        assertEquals(percentageLivingInCities, populationResidenceReport.getPercentageLivingInCities());
        assertEquals(populationNotLivingInCities, populationResidenceReport.getPopulationNotLivingInCities());
        assertEquals(percentageNotLivingInCities, populationResidenceReport.getPercentageNotLivingInCities());
        assertEquals(expectedRecordType, populationResidenceReport.getRecordType());
        assertEquals(expectedToStringOutput, populationResidenceReport.toString());
    }

    // Tests that when a language object is constructed, the getters and toString
    // return the same fields that we passed in.
    @Test
    public void Record_testGettersAndToStringForLanguage()
    {
        // given
        String language = "Gaelic";
        Long speakers = 3L;
        Integer percentage = 0;
        String expectedRecordType = App.LANGUAGE;
        String expectedToStringOutput = App.HORIZONTAL_LINE + "\n" +
                "Language: Gaelic |  Speakers: 3 | % of world's population: 0%";

        // when
        Record Language = new Record(language, speakers, percentage);

        // then
        assertEquals(language, Language.getName());
        assertEquals(speakers, Language.getSpeakers());
        assertEquals(percentage, Language.getPercentage());
        assertEquals(expectedRecordType, Language.getRecordType());
        assertEquals(expectedToStringOutput, Language.toString());
    }

    // Tests that when a population object is constructed, the getters and toString
    // return the same fields that we passed in.
    @Test
    public void Record_testGettersAndToStringForPopulation()
    {
        // given
        String name = "China";
        Long population = 999000000L;
        String expectedRecordType = App.POPULATION;
        String expectedToStringOutput = App.HORIZONTAL_LINE + "\n" +
                "Area: China |  Population: 999,000,000";

        // when
        Record Population = new Record(name, population);

        // then
        assertEquals(name, Population.getName());
        assertEquals(population, Population.getPopulation());
        assertEquals(expectedRecordType, Population.getRecordType());
        assertEquals(expectedToStringOutput, Population.toString());
    }
}
