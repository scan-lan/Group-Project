package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class DAO_IntegrationTests
{
    static DAO dao;
    static Connection connection;

    @BeforeAll
    static void init()
    {
        // Create database connection object
        connection = App.connect("localhost:33061", App.databaseDriver, false);
        // Create Data Access Object
        dao = new DAO(connection);
    }

    @AfterAll
    static void tearDown() { App.disconnect(connection); }

    /**
     * Integration tests covering the DAO.executeStatement method
     */
    // Tests that an empty list is returned when a nonsense query is given
    @Test
    void executeStatement_arrayIsEmptyWhenStatementIsInvalid()
    {
        // given
        String statementString = "SELECT ALL FROM EVERYWHERE";

        // when
        ArrayList<Record> countries= dao.executeStatement(statementString, App.COUNTRY);

        // then
        assertEquals(0, countries.size());
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
        ArrayList<Record> countries = dao.allCountriesIn(App.CONTINENT, areaFilter);

        // then
        for (Record country : countries) assertEquals(areaFilter, country.getContinent());
    }

    // Tests that an empty array is returned when the areaName is set incorrectly
    @Test
    void allCountriesIn_arrayIsEmptyWhenAreaNameIsInvalid()
    {
        // given
        String areaName = "SmurfCity USA";

        // when
        ArrayList<Record> countries = dao.allCountriesIn(App.REGION, areaName);

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
        ArrayList<Record> countries = dao.topNCountriesIn(App.WORLD, "", n);

        // then
        assertTrue(countries.size() <= n);
    }

    // Tests that the returned results are correct and in order
    @Test
    void topNCountriesIn_resultCountriesAreExpected()
    {
        // given
        // Output string will be updated when I have access to MySQL
        String[] expectedCountries = new String[]{"China", "India", "Indonesia"};

        // when
        ArrayList<Record> countries = dao.topNCountriesIn(App.CONTINENT, "Asia", 3);

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
        ArrayList<Record> countries = dao.topNCountriesIn(App.WORLD, "", n);

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
        ArrayList<Record> countries = dao.topNCountriesIn(App.CONTINENT, areaName, 10);

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
        ArrayList<Record> countries = dao.topNCountriesIn(App.CONTINENT, areaName, 10);

        // then
        for (Record country : countries) assertEquals("Europe", country.getContinent());
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
        ArrayList<Record> cities = dao.allCitiesIn(App.COUNTRY, areaFilter);

        // then
        for (Record city: cities) assertEquals(areaFilter, city.getCountry());
    }

    // Tests that an empty array is returned when the areaName is set incorrectly
    @Test
    void allCitiesIn_arrayIsEmptyWhenAreaNameIsInvalid()
    {
        // given
        String areaName = "Invalid Area";

        // when
        ArrayList<Record> cities = dao.allCitiesIn(App.COUNTRY, areaName);

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
        ArrayList<Record> cities = dao.topNCitiesIn(App.CONTINENT, "europe", n);

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
        ArrayList<Record> cities = dao.topNCitiesIn(App.CONTINENT, "europe", 5);

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
        ArrayList<Record> cities = dao.topNCitiesIn(App.WORLD, "", n);

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
        ArrayList<Record> cities = dao.topNCitiesIn(App.REGION, areaName, 10);

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
        ArrayList<Record> cities = dao.topNCitiesIn(App.COUNTRY, areaName, 10);

        // then
        for (Record city : cities) assertEquals("United Kingdom", city.getCountry());
    }

    /**
     * Integration tests covering the DAO.allCapitalCitiesIn method
     */
    // Tests that all capital cities in query are in the given area
    @Test
    void allCapitalCitiesIn_allAreasMatchFilter()
    {
        // given
        String areaFilter = "Asia";

        // when
        ArrayList<Record> capitalCities = dao.allCapitalCitiesIn(App.CONTINENT, areaFilter);

        // then
        for (Record capitalCity : capitalCities) assertEquals(areaFilter, capitalCity.getContinent());
    }

    // Tests that an empty array is returned when the areaName is set incorrectly
    @Test
    void allCapitalCitiesIn_arrayIsEmptyWhenAreaNameIsInvalid()
    {
        // given
        String areaName = "East Fife";

        // when
        ArrayList<Record> capitalCities = dao.allCapitalCitiesIn(App.CONTINENT, areaName);

        // then
        assertEquals(0, capitalCities.size());
    }

    // Tests that the returned results are correct
    @Test
    void allCapitalCitiesIn_resultCapitalCitiesAreExpected()
    {
        // given
        String[] expectedCapitalCity = new String[]{"Canberra", "Wellington", "Kingston", "Flying Fish Cove", "West Island",};

        // when
        ArrayList<Record> capitalCities = dao.allCapitalCitiesIn(App.REGION, "Australia and New Zealand");

        // then
        for (int i = 0; i < 5; i++) assertEquals(expectedCapitalCity[i], capitalCities.get(i).getName());
    }

    /**
     * Integration tests covering the DAO.languageReport method
     */
    // Test that 5 items are returned in the list
    @Test
    void languageReport_returnedListLengthAsExpected()
    {
        // given
        Integer expectedLength = 5;

        // when
        ArrayList<Record> languages = dao.languageReport();

        // then
        assertEquals(expectedLength, languages.size());
    }

    // Test that the 5 expected languages are returned
    @Test
    void languageReport_returnedLanguagesAsExpected()
    {
        // given
        ArrayList<String> expectedLanguages = new ArrayList<String>(
                Arrays.asList("Chinese", "English", "Hindi", "Arabic", "Spanish"));

        // when
        ArrayList<Record> languages = dao.languageReport();

        // then
        for (int i = 0; i < 5; i++) assertTrue(expectedLanguages.contains(languages.get(i).getLanguage()));
    }
}

