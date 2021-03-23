package com.napier.sem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class recordClasses_UnitTests
{
    @Test
    public void capitalCity_testGettersAndToString()
    {
        // given
        String name = "Glasgow";
        String country = "Scotland";
        String region = "Great Britain";
        String continent = "Europe";
        Integer population = 6000000;
        String expectedToStringOutput = "Name: Glasgow | Country: Scotland | Population: 6000000\n" +
                "-------------------------------------------------------";

        // when
        CapitalCity capitalCity = new CapitalCity(name, country, region, continent, population);

        // then
        assertEquals(name, capitalCity.getName());
        assertEquals(country, capitalCity.getCountry());
        assertEquals(region, capitalCity.getRegion());
        assertEquals(continent, capitalCity.getContinent());
        assertEquals(population, capitalCity.getPopulation());
        assertEquals(expectedToStringOutput, capitalCity.toString());
    }

    @Test
    public void city_testGettersAndToString()
    {
        // given
        String name = "Wishae";
        String country = "Scotland";
        String district = "North Lanarkshire";
        Integer population = 10000;
        String expectedToStringOutput = "Name: Wishae |  Country: Scotland \n" +
                "District: North Lanarkshire |  Population: 10000\n" +
                "-------------------------------------------------------";

        // when
        City city = new City(name, country, district, population);

        // then
        assertEquals(name, city.getName());
        assertEquals(country, city.getCountry());
        assertEquals(district, city.getDistrict());
        assertEquals(population, city.getPopulation());
        assertEquals(expectedToStringOutput, city.toString());
    }

    @Test
    public void country_testGettersAndToString()
    {
        // given
        String code = "SCT";
        String name = "Scotland";
        String continent = "Europe";
        String region = "Great Britain";
        Integer population = 6000000;
        String capital = "Edinburgh";
        String expectedToStringOutput = "Country code: SCT | Name: Scotland | Continent: Europe\n" +
                "Region: Great Britain | Population: 6000000 | Capital: Edinburgh\n" +
                "-------------------------------------------------------";


        // when
        Country country = new Country(code, name, continent, region, population, capital);

        // then
        assertEquals(code, country.getCode());
        assertEquals(name, country.getName());
        assertEquals(continent, country.getContinent());
        assertEquals(region, country.getRegion());
        assertEquals(population, country.getPopulation());
        assertEquals(capital, country.getCapital());
        assertEquals(expectedToStringOutput, country.toString());
    }
}
