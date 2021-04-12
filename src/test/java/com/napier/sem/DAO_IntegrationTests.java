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
        String areaName = "Africa";

        // when
        ArrayList<Record> countries = dao.allCountriesIn(App.CONTINENT, areaName);

        // then
        for (Record country : countries) assertEquals(areaName, country.getContinent());
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

    // Tests that the results are listed in descending order.
    @Test
    void allCountriesIn_countriesPopulationIsInDescendingOrder()
    {
        // when
        ArrayList<Record> countries = dao.allCountriesIn(App.CONTINENT, "Asia");

        // then
        for (int i = 0; i < (countries.size()-1); i++)
        {
            assertTrue(countries.get(i).getPopulation() >= countries.get(i+1).getPopulation());
        }
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

    // Tests that the results are listed in descending order.
    @Test
    void topNCountriesIn_countriesPopulationIsInDescendingOrder()
    {
        // when
        ArrayList<Record> countries = dao.topNCountriesIn(App.CONTINENT, "Europe", 5);

        // then
        for (int i = 0; i < (countries.size()-1); i++)
        {
            assertTrue(countries.get(i).getPopulation() >= countries.get(i+1).getPopulation());
        }
    }

    /**
     * Integration tests covering the DAO.allCitiesIn method
     */
    // Tests that all cities in query are in the given area
    @Test
    void allCitiesIn_allAreasMatchFilter()
    {
        // given
        String areaName = "Japan";

        // when
        ArrayList<Record> cities = dao.allCitiesIn(App.COUNTRY, areaName);

        // then
        for (Record city: cities) assertEquals(areaName, city.getCountry());
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

    // Tests that the results are listed in descending order.
    @Test
    void allCitiesIn_allCitiesPopulationIsInDescendingOrder()
    {
        // when
        ArrayList<Record> cities = dao.allCitiesIn(App.COUNTRY, "United Kingdom");

        // then
        for (int i = 0; i < (cities.size()-1); i++)
        {
            assertTrue(cities.get(i).getPopulation() >= cities.get(i+1).getPopulation());
        }
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

    // Tests that the results are listed in descending order.
    @Test
    void topNCitiesIn_topNCitiesPopulationIsInDescendingOrder()
    {
        // when
        ArrayList<Record> cities = dao.topNCitiesIn(App.CONTINENT, "Europe", 5);

        // then
        for (int i = 0; i < (cities.size()-1); i++)
        {
            assertTrue(cities.get(i).getPopulation() >= cities.get(i+1).getPopulation());
        }
    }

    /**
     * Integration tests covering the DAO.allCapitalCitiesIn method
     */
    // Tests that all capital cities in query are in the given area
    @Test
    void allCapitalCitiesIn_allAreasMatchFilter()
    {
        // given
        String areaName = "Asia";

        // when
        ArrayList<Record> capitalCities = dao.allCapitalCitiesIn(App.CONTINENT, areaName);

        // then
        for (Record capitalCity : capitalCities) assertEquals(areaName, capitalCity.getContinent());
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

    // Tests that the results are listed in descending order.
    @Test
    void allCapitalCitiesIn_capitalCitiesPopulationIsInDescendingOrder()
    {
        // when
        ArrayList<Record> capitalCities = dao.allCapitalCitiesIn(App.CONTINENT, "Asia");

        // then
        for (int i = 0; i < (capitalCities.size()-1); i++)
        {
            assertTrue(capitalCities.get(i).getPopulation() >= capitalCities.get(i+1).getPopulation());
        }
    }

    /**
     * Integration tests covering the DAO.topNCapitalCitiesIn method
     */
    // Tests that the number of results is less than or equal to integer n
    @Test
    void topNCapitalCitiesIn_arrayIsCorrectSize ()
    {
        // given
        int n = 5;

        // when
        ArrayList<Record> capitalCities = dao.topNCapitalCitiesIn(App.DISTRICT, "Scotland", n);

        // then
        assertTrue(capitalCities.size() <= n);
    }

    // Tests that an empty array is returned when integer n is set to 0
    @Test
    void topNCapitalCitiesIn_whenNZeroArrayIsEmpty ()
    {
        // given
        int n = 0;

        // when
        ArrayList<Record> capitalCities = dao.topNCapitalCitiesIn(App.WORLD, "", n);

        // then
        assertEquals(0, capitalCities.size());
    }

    // Tests that an empty array is returned when the areaName is set incorrectly
    @Test
    void topNCapitalCitiesIn_arrayIsEmptyWhenAreaNameIsInvalid ()
    {
        // given
        String areaName = "SmurfCity USA";

        // when
        ArrayList<Record> capitalCities = dao.topNCapitalCitiesIn(App.REGION, areaName, 10);

        // then
        assertEquals(0, capitalCities.size());
    }

    // Tests all capital cities are in the given area that was passed
    @Test
    void topNCapitalCitiesIn_allAreasMatchFilter ()
    {
        // given
        String areaName = "United Kingdom";

        // when
        ArrayList<Record> capitalCities = dao.topNCapitalCitiesIn(App.COUNTRY, areaName, 10);

        // then
        for (Record capitalCity : capitalCities) assertEquals("United Kingdom", capitalCity.getCountry());
    }

    // Tests that the results are listed in descending order.
    @Test
    void topNCapitalCitiesIn_capitalCitiesPopulationIsInDescendingOrder()
    {
        // when
        ArrayList<Record> capitalCities = dao.topNCapitalCitiesIn(App.CONTINENT, "Asia", 5);

        // then
        for (int i = 0; i < (capitalCities.size()-1); i++)
        {
            assertTrue(capitalCities.get(i).getPopulation() >= capitalCities.get(i+1).getPopulation());
        }
    }

    /**
     * Integration tests covering the DAO.populationLivingInAndNotInCities method
     */
    // Tests that area in query is the same as the area output
    @Test
    void populationLivingInAndNotInCities_correctAreaIsOutput()
    {
        // given
        String areaName = "Japan";

        // when
        ArrayList<Record> populationResidenceReport = dao.populationLivingInAndNotInCities(App.COUNTRY, areaName);

        // then
        assertEquals(areaName, populationResidenceReport.get(0).getName());
    }

    // Tests that an empty array is returned when the areaName is set incorrectly
    @Test
    void populationLivingInAndNotInCities_arrayIsEmptyWhenAreaNameIsInvalid()
    {
        // given
        String areaName = "Invalid Area";

        // when
        ArrayList<Record> populationResidenceReport = dao.populationLivingInAndNotInCities(App.REGION, areaName);

        // then
        assertEquals(0, populationResidenceReport.size());
    }

    /**
     * Integration tests covering the DAO.populationOf method
     */
    // Tests that area in query is the same as the area output
    @Test
    void populationOf_correctAreaIsOutput()
    {
        // given
        String areaName = "Japan";

        // when
        ArrayList<Record> population = dao.populationOf(App.COUNTRY, areaName);

        // then
        assertEquals(areaName, population.get(0).getName());
    }

    // Tests that an empty array is returned when the areaName is set incorrectly
    @Test
    void populationOf_arrayIsEmptyWhenAreaNameIsInvalid()
    {
        // given
        String areaName = "Invalid Area";

        // when
        ArrayList<Record> population = dao.populationOf(App.REGION, areaName);

        // then
        assertEquals(0, population.size());
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
        ArrayList<String> expectedLanguages = new ArrayList<>(
                Arrays.asList("Chinese", "English", "Hindi", "Arabic", "Spanish"));

        // when
        ArrayList<Record> languages = dao.languageReport();

        // then
        for (Record language : languages) assertTrue(expectedLanguages.contains(language.getName()));
    }
}
