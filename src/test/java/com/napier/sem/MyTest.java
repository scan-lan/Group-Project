package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MyTest
{
    static App app;

    // Connection to MySQL database.
    final static Connection connection = null;

    // Create instance of the database access object
    DAO dao = new DAO(MyTest.connection);

    @BeforeAll
    static void init()
    {
        // Create new Application
        App app = new App();
        // Connect to database
        app.connect("localhost:33060");
    }

    @Test
    void topNCitiesInTest1()
    {
        Integer N = 5;
        ArrayList<City> cities = dao.topNCitiesIn("continent","europe",N);
        assertTrue(cities.size() <= N);
        assertEquals(cities.get(0).name, "Moscow");
        assertEquals(cities.get(1).name, "London");
        assertEquals(cities.get(2).name, "St Petersburg");
        assertEquals(cities.get(3).name, "Berlin");
        assertEquals(cities.get(4).name, "Madrid");
    }
}