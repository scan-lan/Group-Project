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
    // For some reason this doesn't work, it has to be called from the App
    // DAO dao = new DAO(MyTest.connection);
    DAO dao = new DAO(App.connection);


    @BeforeAll
    static void init() {
        // Create new Application
        App app = new App();
        // Connect to database
        app.connect("localhost:33061");
    }

    @Test
    void topNCitiesInTest1()
    {
        Integer N = 5;
        ArrayList<City> cities = dao.topNCitiesIn("continent", "europe", N);
        assertTrue(cities.size() <= N);
        assertEquals(cities.get(0).name, "Moscow");
        assertEquals(cities.get(1).name, "London");
        assertEquals(cities.get(2).name, "St Petersburg");
        assertEquals(cities.get(3).name, "Berlin");
        assertEquals(cities.get(4).name, "Madrid");
    }

    @Test
    void topNCitiesInTest2()
    {
        Integer N = 0;
        ArrayList<City> cities = dao.topNCitiesIn("world", "", N);
        assertEquals(cities.size(), 0);
    }

    @Test
    void topNCitiesInTest3()
    {
        Integer N = 10;
        ArrayList<City> cities = dao.topNCitiesIn("region","delaware",N);
        assertEquals(cities.size(), 0);
    }
}