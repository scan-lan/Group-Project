package com.napier.sem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class recordClasses_UnitTests
{
    @Test
    public void capitalCity_ensureGettersReturnsCorrectFields() {
        // given
        String name = "Glasgow";
        String country = "Scotland";
        String region = "Great Britain";
        String continent = "Europe";
        Integer population = 6000000;

        // when
        CapitalCity capitalCity = new CapitalCity(name, country, region, continent, population);

        // then
        assertEquals(name, capitalCity.getName());
        assertEquals(country, capitalCity.getCountry());
        assertEquals(region, capitalCity.getRegion());
        assertEquals(continent, capitalCity.getContinent());
        assertEquals(population, capitalCity.getPopulation());
    }
}
