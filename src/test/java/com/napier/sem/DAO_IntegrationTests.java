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
        connection = App.connect("localhost:33061", App.DATABASE_DRIVER, false);
        // Create Data Access Object
        dao = new DAO(connection);
    }

    @AfterAll
    static void tearDown() { App.disconnect(connection); }

    /**
     * Integration tests covering the DAO.executeStatement method
     */
    // Tests that null is returned when a nonsense query is given
    @Test
    void executeStatement_nullWhenStatementIsInvalid()
    {
        // given
        String statementString = "SELECT ALL FROM EVERYWHERE";

        // when
        ArrayList<Record> countries= dao.executeStatement(statementString, App.COUNTRY);

        // then
        assertNull(countries);
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

    // test that countries list is empty when areaFilter and areaName are null
    @Test
    public void allCountriesIn_nullArgumentsCauseEmptyList()
    {
        // given
        String areaFilter = null;
        String areaName = null;

        // when
        ArrayList<Record> countries1 = dao.allCountriesIn(areaFilter, areaName);
        ArrayList<Record> countries2 = dao.allCountriesIn(App.REGION, areaName);
        ArrayList<Record> countries3 = dao.allCountriesIn(areaFilter, "Europe");

        // then
        assertEquals(0, countries1.size());
        assertEquals(0, countries2.size());
        assertEquals(0, countries3.size());
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

    // test that countries list is empty when areaFilter and areaName are null
    @Test
    public void topNCountriesIn_nullArgumentsCauseEmptyList()
    {
        // given
        String areaFilter = null;
        String areaName = null;

        // when
        ArrayList<Record> countries1 = dao.topNCountriesIn(areaFilter, areaName, 10);
        ArrayList<Record> countries2 = dao.topNCountriesIn(App.REGION, areaName, 10);
        ArrayList<Record> countries3 = dao.topNCountriesIn(areaFilter, "Europe", 10);

        // then
        assertEquals(0, countries1.size());
        assertEquals(0, countries2.size());
        assertEquals(0, countries3.size());
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

    // test that cities list is empty when areaFilter and areaName are null
    @Test
    public void allCitiesIn_nullArgumentsCauseEmptyList()
    {
        // given
        String areaFilter = null;
        String areaName = null;

        // when
        ArrayList<Record> cities1 = dao.allCitiesIn(areaFilter, areaName);
        ArrayList<Record> cities2 = dao.allCitiesIn(App.REGION, areaName);
        ArrayList<Record> cities3 = dao.allCitiesIn(areaFilter, "Europe");

        // then
        assertEquals(0, cities1.size());
        assertEquals(0, cities2.size());
        assertEquals(0, cities3.size());
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

    // test that cities list is empty when areaFilter and areaName are null
    @Test
    public void topNCitiesIn_nullArgumentsCauseEmptyList()
    {
        // given
        String areaFilter = null;
        String areaName = null;

        // when
        ArrayList<Record> cities1 = dao.topNCitiesIn(areaFilter, areaName, 10);
        ArrayList<Record> cities2 = dao.topNCitiesIn(App.REGION, areaName, 10);
        ArrayList<Record> cities3 = dao.topNCitiesIn(areaFilter, "Europe", 10);

        // then
        assertEquals(0, cities1.size());
        assertEquals(0, cities2.size());
        assertEquals(0, cities3.size());
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

    // test that capital cities list is empty when areaFilter and areaName are null
    @Test
    public void allCapitalCitiesIn_nullArgumentsCauseEmptyList()
    {
        // given
        String areaFilter = null;
        String areaName = null;

        // when
        ArrayList<Record> capitalCities1 = dao.allCapitalCitiesIn(areaFilter, areaName);
        ArrayList<Record> capitalCities2 = dao.allCapitalCitiesIn(App.REGION, areaName);
        ArrayList<Record> capitalCities3 = dao.allCapitalCitiesIn(areaFilter, "Europe");

        // then
        assertEquals(0, capitalCities1.size());
        assertEquals(0, capitalCities2.size());
        assertEquals(0, capitalCities3.size());
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

    // Tests that the results are listed in descending order.  Can be used instead of topNCapitalCitiesIn_resultCitiesAreExpected
    // Which has hard coded values
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

    // test that capital cities list is empty when areaFilter and areaName are null
    @Test
    public void topNCapitalCitiesIn_nullArgumentsCauseEmptyList()
    {
        // given
        String areaFilter = null;
        String areaName = null;

        // when
        ArrayList<Record> capitalCities1 = dao.topNCapitalCitiesIn(areaFilter, areaName, 10);
        ArrayList<Record> capitalCities2 = dao.topNCapitalCitiesIn(App.REGION, areaName, 10);
        ArrayList<Record> capitalCities3 = dao.topNCapitalCitiesIn(areaFilter, "Europe", 10);

        // then
        assertEquals(0, capitalCities1.size());
        assertEquals(0, capitalCities2.size());
        assertEquals(0, capitalCities3.size());
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

    // test that population residence report list is empty when areaFilter and areaName are null
    @Test
    public void populationLivingInAndNotInCities_nullArgumentsCauseEmptyList()
    {
        // given
        String areaFilter = null;
        String areaName = null;

        // when
        ArrayList<Record> populationResidenceReport1 = dao.populationLivingInAndNotInCities(areaFilter, areaName);
        ArrayList<Record> populationResidenceReport2 = dao.populationLivingInAndNotInCities(App.REGION, areaName);
        ArrayList<Record> populationResidenceReport3 = dao.populationLivingInAndNotInCities(areaFilter, "Europe");

        // then
        assertEquals(0, populationResidenceReport1.size());
        assertEquals(0, populationResidenceReport2.size());
        assertEquals(0, populationResidenceReport3.size());
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

    // test that population list is empty when areaFilter and areaName are null
    @Test
    public void populationOf_nullArgumentsCauseEmptyList()
    {
        // given
        String areaFilter = null;
        String areaName = null;

        // when
        ArrayList<Record> population1 = dao.populationOf(areaFilter, areaName);
        ArrayList<Record> population2 = dao.populationOf(App.REGION, areaName);
        ArrayList<Record> population3 = dao.populationOf(areaFilter, "Europe");

        // then
        assertEquals(0, population1.size());
        assertEquals(0, population2.size());
        assertEquals(0, population3.size());
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
