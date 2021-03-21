package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DAOIntegrationTests
{
    static DAO dao;

    @BeforeAll
    static void init()
    {
        // Create database connection object
        Connection connection = App.connect("localhost:33061");
        // Create Data Access Object
        dao = new DAO(connection);
    }

    // Tests that the number of results is less than or equal to integer n
    @Test
    void topNCitiesInArrayIsCorrectSize()
    {
        // given
        int n = 5;

        // when
        ArrayList<City> cities = dao.topNCitiesIn(App.CONTINENT, "europe", n);

        // then
        assertTrue(cities.size() <= n);
    }

    // Tests that the returned results are correct and in order
    @Test
    void topNCitiesIn_resultCitiesAreExpected()
    {
        // given
        String[] expectedCities = new String[]{"Moscow", "London", "St Petersburg", "Berlin", "Madrid"};

        // when
        ArrayList<City> cities = dao.topNCitiesIn(App.CONTINENT, "europe", 5);

        // then
        for (int i = 0; i < 5; i++) assertEquals(expectedCities[i], cities.get(i).name);
    }

    // Tests that an empty array is returned when integer n is set to 0
    @Test
    void topNCitiesIn_whenNZeroArrayIsEmpty()
    {
        // given
        int n = 0;

        // when
        ArrayList<City> cities = dao.topNCitiesIn(App.WORLD, "", n);

        // then
        assertEquals(0, cities.size());
    }

    // Tests that an empty array is returned when the areaName is set incorrectly
    @Test
    void topNCitiesIn_ArrayIsEmptyWhenAreaNameIsInvalid()
    {
        // given
        String areaName = "SmurfCity USA";

        // when
        ArrayList<City> cities = dao.topNCitiesIn(App.REGION, areaName, 10);

        // then
        assertEquals(cities.size(), 0);
    }

    // Tests all cities are in the given area that was passed
    @Test
    void topNCitiesIn_AllAreasMatchFilter()
    {
        // given
        String areaName = "United Kingdom";

        // when
        ArrayList<City> cities = dao.topNCitiesIn(App.COUNTRY, areaName, 10);

        // then
        for (City city: cities) assertEquals("United Kingdom", city.country);
    }
}