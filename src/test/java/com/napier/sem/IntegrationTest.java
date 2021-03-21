package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest
{
    static App app;

    // Connection to MySQL database.
    final static Connection connection = null;  // Does not work in this classfile

    // Create instance of the database access object
    DAO dao = new DAO(App.connection);

    @BeforeAll
    static void init()
    {
        // Create database connection object
        Connection connection = App.connect("localhost:33061");
    }

    // Tests that the number of results is less than or equal to Integer N
    @Test
    void topNCitiesInArrayIsCorrectSize() {
        Integer N = 5;
        ArrayList<City> cities = dao.topNCitiesIn("continent", "europe", N);
        assertTrue(cities.size() <= N);
    }

    // Tests that the returned results are correct and in order
    @Test
    void topNCitiesInResultsAreCorrect() {
        Integer N = 5;
        ArrayList<City> cities = dao.topNCitiesIn("continent", "europe", N);
        assertEquals(cities.get(0).name, "Moscow");
        assertEquals(cities.get(1).name, "London");
        assertEquals(cities.get(2).name, "St Petersburg");
        assertEquals(cities.get(3).name, "Berlin");
        assertEquals(cities.get(4).name, "Madrid");
    }

    // Tests that an empty array is returned when Integer N is set to 0
    @Test
    void topNCitiesInArrayIsEmptyIntegerIsZero() {
        Integer N = 0;
        ArrayList<City> cities = dao.topNCitiesIn("world", "", N);
        assertEquals(cities.size(), 0);
    }

    // Tests that an empty array is returned when the areaName is set incorrectly
    @Test
    void topNCitiesInArrayIsEmptyAreaFilterIsIncorrect() {
        Integer N = 10;
        ArrayList<City> cities = dao.topNCitiesIn("region", "SmurfCity USA", N);
        assertEquals(cities.size(), 0);
    }
}