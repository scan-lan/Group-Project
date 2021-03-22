package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DAO_IntegrationTests
{
    static DAO dao;

    @BeforeAll
    static void init()
    {
        // Create database connection object
        Connection connection = App.connect("localhost:33061", "com.mysql.cj.jdbc.Driver");
        // Create Data Access Object
        dao = new DAO(connection);
    }

    /**
     * Integration tests covering the DAO.executeCountryStatement method
     */
    // Tests that an empty list is returned when a nonsense query is given
    @Test
    void executeCountryStatement_arrayIsEmptyWhenStatementIsInvalid()
    {
        // given
        String statementString = "SELECT ALL FROM EVERYWHERE";

        // when
        ArrayList<Country> countries= dao.executeCountryStatement(statementString);

        // then
        assertEquals(0, countries.size());
    }

    /**
     * Integration tests covering the DAO.executeCityStatement method
     */
    // Tests that an empty list is returned when a nonsense query is given
    @Test
    void executeCityStatement_arrayIsEmptyWhenStatementIsInvalid()
    {
        // given
        String statementString = "SELECT ALL FROM EVERYWHERE";

        // when
        ArrayList<City> cities= dao.executeCityStatement(statementString);

        // then
        assertEquals(0, cities.size());
    }

    /**
     * Integration tests covering the DAO.executeCapitalCityStatement method
     */
    // Tests that an empty list is returned when a nonsense query is given
    @Test
    void executeCapitalCityStatement_arrayIsEmptyWhenStatementIsInvalid()
    {
        // given
        String statementString = "SELECT ALL FROM EVERYWHERE";

        // when
        ArrayList<CapitalCity> capitalCities= dao.executeCapitalCityStatement(statementString);

        // then
        assertEquals(0, capitalCities.size());
    }

    /**
     * Integration tests covering the DAO.allCountriesIn method
     */
    // Tests that all countries in query are in the given area
    @Test
    void allCountriesIn_allAreasMatchFilter()
    {
        // given
        String areaFilter = "Africa";

        // when
        ArrayList<Country> countries = dao.allCountriesIn(App.CONTINENT, areaFilter);

        // then
        for (Country country: countries) assertEquals(areaFilter, country.getContinent());
    }

    // Tests that an empty array is returned when the areaName is set incorrectly
    @Test
    void allCountriesIn_arrayIsEmptyWhenAreaNameIsInvalid()
    {
        // given
        String areaName = "SmurfCity USA";

        // when
        ArrayList<Country> countries = dao.allCountriesIn(App.REGION, areaName);

        // then
        assertEquals(0, countries.size());
    }

    /**
     * Integration tests covering the DAO.topNCountriesIn method
     */
    // Tests that the number of results is less than or equal to integer n
    @Test
    void topNCountriesIn_arrayIsCorrectSize()
    {
        // given
        int n = 17;

        // when
        ArrayList<Country> countries = dao.topNCountriesIn(App.WORLD, "", n);

        // then
        assertTrue(countries.size() <= n);
    }

    // Tests that the returned results are correct and in order
    @Test
    void topNCountriesIn_resultCountriesAreExpected()
    {
        // given
        // Output string will be updated when I have access to MySQL
        String[] expectedCountries = new String[]{"...","...","..."};

        // when
        ArrayList<Country> countries = dao.topNCountriesIn(App.CONTINENT, "Asia", 3);

        // then
        for (int i = 0; i < 3; i++) assertEquals(expectedCountries[i], countries.get(i).getName());
    }

    // Tests that an empty array is returned when integer n is set to 0
    @Test
    void topNCountriesIn_whenNZeroArrayIsEmpty()
    {
        // given
        int n = 0;

        // when
        ArrayList<Country> countries = dao.topNCountriesIn(App.WORLD, "", n);

        // then
        assertEquals(0, countries.size());
    }

    // Tests that an empty array is returned when the areaName is set incorrectly
    @Test
    void topNCountriesIn_arrayIsEmptyWhenAreaNameIsInvalid()
    {
        // given
        String areaName = "Scotland";

        // when
        ArrayList<Country> countries = dao.topNCountriesIn(App.CONTINENT, areaName, 10);

        // then
        assertEquals(0, countries.size());
    }

    // Tests all countries are in the given area that was passed
    @Test
    void topNCountriesIn_allAreasMatchFilter()
    {
        // given
        String areaName = "Europe";

        // when
        ArrayList<Country> countries = dao.topNCountriesIn(App.CONTINENT, areaName, 10);

        // then
        for (Country country: countries) assertEquals("Europe", country.getContinent());
    }

    /**
     * Integration tests covering the DAO.allCitiesIn method
     */
    // Tests that all cities in query are in the given area
    @Test
    void allCitiesIn_allAreasMatchFilter()
    {
        // given
        String areaFilter = "Japan";

        // when
        ArrayList<City> cities = dao.allCitiesIn(App.COUNTRY, areaFilter);

        // then
        for (City city: cities) assertEquals(areaFilter, city.getCountry());
    }

    // Tests that an empty array is returned when the areaName is set incorrectly
    @Test
    void allCitiesIn_arrayIsEmptyWhenAreaNameIsInvalid() {
        // given
        String areaName = "Invalid Area";

        // when
        ArrayList<City> cities = dao.allCitiesIn(App.COUNTRY, areaName);

        // then
        assertEquals(0, cities.size());
    }

    /**
     * Integration tests covering the DAO.topNCitiesIn method
     */
        // Tests that the number of results is less than or equal to integer n
        @Test
        void topNCitiesIn_arrayIsCorrectSize ()
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
        void topNCitiesIn_resultCitiesAreExpected ()
        {
            // given
            String[] expectedCities = new String[]{"Moscow", "London", "St Petersburg", "Berlin", "Madrid"};

            // when
            ArrayList<City> cities = dao.topNCitiesIn(App.CONTINENT, "europe", 5);

            // then
            for (int i = 0; i < 5; i++) assertEquals(expectedCities[i], cities.get(i).getName());
        }

        // Tests that an empty array is returned when integer n is set to 0
        @Test
        void topNCitiesIn_whenNZeroArrayIsEmpty ()
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
        void topNCitiesIn_arrayIsEmptyWhenAreaNameIsInvalid ()
        {
            // given
            String areaName = "SmurfCity USA";

            // when
            ArrayList<City> cities = dao.topNCitiesIn(App.REGION, areaName, 10);

            // then
            assertEquals(0, cities.size());
        }

        // Tests all cities are in the given area that was passed
        @Test
        void topNCitiesIn_allAreasMatchFilter ()
        {
            // given
            String areaName = "United Kingdom";

            // when
            ArrayList<City> cities = dao.topNCitiesIn(App.COUNTRY, areaName, 10);

            // then
            for (City city : cities) assertEquals("United Kingdom", city.getCountry());
        }
    }
