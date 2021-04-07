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
        String expectedToStringOutput = "Country code: SCT | Name: Scotland | Continent: Europe\n" +
                "Region: Great Britain | Population: 6,000,000 | Capital: Edinburgh\n" +
                "-------------------------------------------------------";


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
        String expectedToStringOutput = "Name: Glasgow | Country: Scotland | Population: 6,000,000\n" +
                "-------------------------------------------------------";

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
        String expectedToStringOutput = "Name: Wishae |  Country: Scotland \n" +
                "District: North Lanarkshire |  Population: 10,000\n" +
                "-------------------------------------------------------";

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
}
