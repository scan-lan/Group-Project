package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTests
{
    static DAO dao;
    static Connection connection;
    static UserPrompt userPrompt;

    @BeforeAll
    static void init()
    {
        // Create database connection object for use in tests
        connection = App.connect("localhost:33061", App.DATABASE_DRIVER, false);
        // Create Data Access Object for use in tests
        dao = new DAO(connection);
        // Create user prompt object for use in tests
        userPrompt = new UserPrompt(dao);
    }

    private void provideInput(String data)
    {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @AfterEach
    void restoreSystemInput() { System.setIn(System.in); }

    @AfterAll
    static void tearDown() { App.disconnect(connection); }

    /**
     * Integration tests covering the app class
     */
    // Test the app runs fine with a simulate series of user inputs
    @Test
    void App_main_happyPath()
    {
        // given
        provideInput("7\n2\nOceania\nq");

        // when
        App.main(new String[]{}); // then: no failures
    }

    /**
     * Integration tests covering the DAO class
     */
    // Test that null is returned when a nonsense query is given
    @Test
    void DAO_executeStatement_nullWhenStatementIsInvalid()
    {
        // given
        String statementString = "SELECT ALL FROM EVERYWHERE";

        // when
        ArrayList<Record> countries= dao.executeStatement(statementString, App.COUNTRY);

        // then
        assertNull(countries);
    }
    // Test that all countries in query are in the given area
    @Test
    void DAO_allCountriesIn_allAreasMatchFilter()
    {
        // given
        String areaName = "Africa";

        // when
        ArrayList<Record> countries = dao.allCountriesIn(App.CONTINENT, areaName);

        // then
        for (Record country : countries) assertEquals(areaName, country.getContinent());
    }

    // Test that an empty array is returned when the areaName is set incorrectly
    @Test
    void DAO_allCountriesIn_arrayIsEmptyWhenAreaNameIsInvalid()
    {
        // given
        String areaName = "SmurfCity USA";

        // when
        ArrayList<Record> countries = dao.allCountriesIn(App.REGION, areaName);

        // then
        assertEquals(0, countries.size());
    }

    // Test that the results are listed in descending order.
    @Test
    void DAO_allCountriesIn_countriesPopulationIsInDescendingOrder()
    {
        // when
        ArrayList<Record> countries = dao.allCountriesIn(App.CONTINENT, "Asia");

        // then
        for (int i = 0; i < (countries.size()-1); i++)
        {
            assertTrue(countries.get(i).getPopulation() >= countries.get(i+1).getPopulation());
        }
    }

    // Test that countries list is empty when areaFilter and areaName are null
    @Test
    void DAO_allCountriesIn_nullArgumentsCauseEmptyList()
    {
        // when
        ArrayList<Record> countries1 = dao.allCountriesIn(null, null);
        ArrayList<Record> countries2 = dao.allCountriesIn(App.REGION, null);
        ArrayList<Record> countries3 = dao.allCountriesIn(null, "Europe");

        // then
        assertEquals(0, countries1.size());
        assertEquals(0, countries2.size());
        assertEquals(0, countries3.size());
    }

    // Test that the number of results is less than or equal to integer n
    @Test
    void DAO_topNCountriesIn_arrayIsCorrectSize()
    {
        // given
        int n = 17;

        // when
        ArrayList<Record> countries = dao.topNCountriesIn(App.WORLD, "", n);

        // then
        assertTrue(countries.size() <= n);
    }

    // Test that an empty array is returned when integer n is set to 0
    @Test
    void DAO_topNCountriesIn_whenNZeroArrayIsEmpty()
    {
        // given
        int n = 0;

        // when
        ArrayList<Record> countries = dao.topNCountriesIn(App.WORLD, "", n);

        // then
        assertEquals(0, countries.size());
    }

    // Test that an empty array is returned when the areaName is set incorrectly
    @Test
    void DAO_topNCountriesIn_arrayIsEmptyWhenAreaNameIsInvalid()
    {
        // given
        String areaName = "Scotland";

        // when
        ArrayList<Record> countries = dao.topNCountriesIn(App.CONTINENT, areaName, 10);

        // then
        assertEquals(0, countries.size());
    }

    // Test all countries are in the given area that was passed
    @Test
    void DAO_topNCountriesIn_allAreasMatchFilter()
    {
        // given
        String areaName = "Europe";

        // when
        ArrayList<Record> countries = dao.topNCountriesIn(App.CONTINENT, areaName, 10);

        // then
        for (Record country : countries) assertEquals("Europe", country.getContinent());
    }

    // Test that the results are listed in descending order.
    @Test
    void DAO_topNCountriesIn_countriesPopulationIsInDescendingOrder()
    {
        // when
        ArrayList<Record> countries = dao.topNCountriesIn(App.CONTINENT, "Europe", 5);

        // then
        for (int i = 0; i < (countries.size()-1); i++)
        {
            assertTrue(countries.get(i).getPopulation() >= countries.get(i+1).getPopulation());
        }
    }

    // Test that countries list is empty when areaFilter and areaName are null
    @Test
    void DAO_topNCountriesIn_nullArgumentsCauseEmptyList()
    {
        // when
        ArrayList<Record> countries1 = dao.topNCountriesIn(null, null, 10);
        ArrayList<Record> countries2 = dao.topNCountriesIn(App.REGION, null, 10);
        ArrayList<Record> countries3 = dao.topNCountriesIn(null, "Europe", 10);

        // then
        assertEquals(0, countries1.size());
        assertEquals(0, countries2.size());
        assertEquals(0, countries3.size());
    }

    // Test that all cities in query are in the given area
    @Test
    void DAO_allCitiesIn_allAreasMatchFilter()
    {
        // given
        String areaName = "Japan";

        // when
        ArrayList<Record> cities = dao.allCitiesIn(App.COUNTRY, areaName);

        // then
        for (Record city: cities) assertEquals(areaName, city.getCountry());
    }

    // Test that an empty array is returned when the areaName is set incorrectly
    @Test
    void DAO_allCitiesIn_arrayIsEmptyWhenAreaNameIsInvalid()
    {
        // given
        String areaName = "Invalid Area";

        // when
        ArrayList<Record> cities = dao.allCitiesIn(App.COUNTRY, areaName);

        // then
        assertEquals(0, cities.size());
    }

    // Test that the results are listed in descending order.
    @Test
    void DAO_allCitiesIn_allCitiesPopulationIsInDescendingOrder()
    {
        // when
        ArrayList<Record> cities = dao.allCitiesIn(App.COUNTRY, "United Kingdom");

        // then
        for (int i = 0; i < (cities.size()-1); i++)
        {
            assertTrue(cities.get(i).getPopulation() >= cities.get(i+1).getPopulation());
        }
    }

    // Test that cities list is empty when areaFilter and areaName are null
    @Test
    void DAO_allCitiesIn_nullArgumentsCauseEmptyList()
    {
        // when
        ArrayList<Record> cities1 = dao.allCitiesIn(null, null);
        ArrayList<Record> cities2 = dao.allCitiesIn(App.REGION, null);
        ArrayList<Record> cities3 = dao.allCitiesIn(null, "Europe");

        // then
        assertEquals(0, cities1.size());
        assertEquals(0, cities2.size());
        assertEquals(0, cities3.size());
    }

    // Test that the number of results is less than or equal to integer n
    @Test
    void DAO_topNCitiesIn_arrayIsCorrectSize ()
    {
        // given
        int n = 5;

        // when
        ArrayList<Record> cities = dao.topNCitiesIn(App.CONTINENT, "europe", n);

        // then
        assertTrue(cities.size() <= n);
    }

    // Test that an empty array is returned when integer n is set to 0
    @Test
    void DAO_topNCitiesIn_whenNZeroArrayIsEmpty ()
    {
        // given
        int n = 0;

        // when
        ArrayList<Record> cities = dao.topNCitiesIn(App.WORLD, "", n);

        // then
        assertEquals(0, cities.size());
    }

    // Test that an empty array is returned when the areaName is set incorrectly
    @Test
    void DAO_topNCitiesIn_arrayIsEmptyWhenAreaNameIsInvalid ()
    {
        // given
        String areaName = "SmurfCity USA";

        // when
        ArrayList<Record> cities = dao.topNCitiesIn(App.REGION, areaName, 10);

        // then
        assertEquals(0, cities.size());
    }

    // Test all cities are in the given area that was passed
    @Test
    void DAO_topNCitiesIn_allAreasMatchFilter ()
    {
        // given
        String areaName = "United Kingdom";

        // when
        ArrayList<Record> cities = dao.topNCitiesIn(App.COUNTRY, areaName, 10);

        // then
        for (Record city : cities) assertEquals("United Kingdom", city.getCountry());
    }

    // Test that the results are listed in descending order.
    @Test
    void DAO_topNCitiesIn_topNCitiesPopulationIsInDescendingOrder()
    {
        // when
        ArrayList<Record> cities = dao.topNCitiesIn(App.CONTINENT, "Europe", 5);

        // then
        for (int i = 0; i < (cities.size()-1); i++)
        {
            assertTrue(cities.get(i).getPopulation() >= cities.get(i+1).getPopulation());
        }
    }

    // Test that cities list is empty when areaFilter and areaName are null
    @Test
    void DAO_topNCitiesIn_nullArgumentsCauseEmptyList()
    {
        // when
        ArrayList<Record> cities1 = dao.topNCitiesIn(null, null, 10);
        ArrayList<Record> cities2 = dao.topNCitiesIn(App.REGION, null, 10);
        ArrayList<Record> cities3 = dao.topNCitiesIn(null, "Europe", 10);

        // then
        assertEquals(0, cities1.size());
        assertEquals(0, cities2.size());
        assertEquals(0, cities3.size());
    }

    // Test that all capital cities in query are in the given area
    @Test
    void DAO_allCapitalCitiesIn_allAreasMatchFilter()
    {
        // given
        String areaName = "Asia";

        // when
        ArrayList<Record> capitalCities = dao.allCapitalCitiesIn(App.CONTINENT, areaName);

        // then
        for (Record capitalCity : capitalCities) assertEquals(areaName, capitalCity.getContinent());
    }

    // Test that an empty array is returned when the areaName is set incorrectly
    @Test
    void DAO_allCapitalCitiesIn_arrayIsEmptyWhenAreaNameIsInvalid()
    {
        // given
        String areaName = "East Fife";

        // when
        ArrayList<Record> capitalCities = dao.allCapitalCitiesIn(App.CONTINENT, areaName);

        // then
        assertEquals(0, capitalCities.size());
    }

    // Test that the results are listed in descending order.
    @Test
    void DAO_allCapitalCitiesIn_capitalCitiesPopulationIsInDescendingOrder()
    {
        // when
        ArrayList<Record> capitalCities = dao.allCapitalCitiesIn(App.CONTINENT, "Asia");

        // then
        for (int i = 0; i < (capitalCities.size()-1); i++)
        {
            assertTrue(capitalCities.get(i).getPopulation() >= capitalCities.get(i+1).getPopulation());
        }
    }

    // Test that capital cities list is empty when areaFilter and areaName are null
    @Test
    void DAO_allCapitalCitiesIn_nullArgumentsCauseEmptyList()
    {
        // when
        ArrayList<Record> capitalCities1 = dao.allCapitalCitiesIn(null, null);
        ArrayList<Record> capitalCities2 = dao.allCapitalCitiesIn(App.REGION, null);
        ArrayList<Record> capitalCities3 = dao.allCapitalCitiesIn(null, "Europe");

        // then
        assertEquals(0, capitalCities1.size());
        assertEquals(0, capitalCities2.size());
        assertEquals(0, capitalCities3.size());
    }

    // Test that the number of results is less than or equal to integer n
    @Test
    void DAO_topNCapitalCitiesIn_arrayIsCorrectSize ()
    {
        // given
        int n = 5;

        // when
        ArrayList<Record> capitalCities = dao.topNCapitalCitiesIn(App.DISTRICT, "Scotland", n);

        // then
        assertTrue(capitalCities.size() <= n);
    }

    // Test that an empty array is returned when integer n is set to 0
    @Test
    void DAO_topNCapitalCitiesIn_whenNZeroArrayIsEmpty ()
    {
        // given
        int n = 0;

        // when
        ArrayList<Record> capitalCities = dao.topNCapitalCitiesIn(App.WORLD, "", n);

        // then
        assertEquals(0, capitalCities.size());
    }

    // Test that an empty array is returned when the areaName is set incorrectly
    @Test
    void DAO_topNCapitalCitiesIn_arrayIsEmptyWhenAreaNameIsInvalid ()
    {
        // given
        String areaName = "SmurfCity USA";

        // when
        ArrayList<Record> capitalCities = dao.topNCapitalCitiesIn(App.REGION, areaName, 10);

        // then
        assertEquals(0, capitalCities.size());
    }

    // Test all capital cities are in the given area that was passed
    @Test
    void DAO_topNCapitalCitiesIn_allAreasMatchFilter ()
    {
        // given
        String areaName = "United Kingdom";

        // when
        ArrayList<Record> capitalCities = dao.topNCapitalCitiesIn(App.COUNTRY, areaName, 10);

        // then
        for (Record capitalCity : capitalCities) assertEquals("United Kingdom", capitalCity.getCountry());
    }

    // Test that the results are listed in descending order.
    @Test
    void DAO_topNCapitalCitiesIn_capitalCitiesPopulationIsInDescendingOrder()
    {
        // when
        ArrayList<Record> capitalCities = dao.topNCapitalCitiesIn(App.CONTINENT, "Asia", 5);

        // then
        for (int i = 0; i < (capitalCities.size()-1); i++)
        {
            assertTrue(capitalCities.get(i).getPopulation() >= capitalCities.get(i+1).getPopulation());
        }
    }

    // Test that capital cities list is empty when areaFilter and areaName are null
    @Test
    void DAO_topNCapitalCitiesIn_nullArgumentsCauseEmptyList()
    {
        // when
        ArrayList<Record> capitalCities1 = dao.topNCapitalCitiesIn(null, null, 10);
        ArrayList<Record> capitalCities2 = dao.topNCapitalCitiesIn(App.REGION, null, 10);
        ArrayList<Record> capitalCities3 = dao.topNCapitalCitiesIn(null, "Europe", 10);

        // then
        assertEquals(0, capitalCities1.size());
        assertEquals(0, capitalCities2.size());
        assertEquals(0, capitalCities3.size());
    }

    // Test that area in query is the same as the area output
    @Test
    void DAO_populationLivingInAndNotInCities_correctAreaIsOutput()
    {
        // given
        String areaName = "Japan";

        // when
        ArrayList<Record> residenceReport = dao.populationLivingInAndNotInCities(App.COUNTRY, areaName);

        // then
        assertEquals(areaName, residenceReport.get(0).getName());
    }

    // Test that an empty array is returned when the areaName is set incorrectly
    @Test
    void DAO_populationLivingInAndNotInCities_arrayIsEmptyWhenAreaNameIsInvalid()
    {
        // given
        String areaName = "Invalid Area";

        // when
        ArrayList<Record> residenceReport = dao.populationLivingInAndNotInCities(App.REGION, areaName);

        // then
        assertEquals(0, residenceReport.size());
    }

    // Test that population residence report list is empty when areaFilter and areaName are null
    @Test
    void DAO_populationLivingInAndNotInCities_nullArgumentsCauseEmptyList()
    {
        // when
        ArrayList<Record> residenceReport1 = dao.populationLivingInAndNotInCities(null, null);
        ArrayList<Record> residenceReport2 = dao.populationLivingInAndNotInCities(App.REGION, null);
        ArrayList<Record> residenceReport3 = dao.populationLivingInAndNotInCities(null, "Europe");

        // then
        assertEquals(0, residenceReport1.size());
        assertEquals(0, residenceReport2.size());
        assertEquals(0, residenceReport3.size());
    }

    // Test that area in query is the same as the area output
    @Test
    void DAO_populationOf_correctAreaIsOutput()
    {
        // given
        String areaName = "Glasgow";

        // when
        ArrayList<Record> population = dao.populationOf(App.CITY, areaName);

        // then
        assertEquals(areaName, population.get(0).getName());
    }

    // Test that an empty array is returned when the areaName is set incorrectly
    @Test
    void DAO_populationOf_arrayIsEmptyWhenAreaNameIsInvalid()
    {
        // given
        String areaName = "Invalid Area";

        // when
        ArrayList<Record> population = dao.populationOf(App.REGION, areaName);

        // then
        assertEquals(0, population.size());
    }

    // Test that population list is empty when areaFilter and areaName are null
    @Test
    void DAO_populationOf_nullArgumentsCauseEmptyList()
    {
        // when
        ArrayList<Record> population1 = dao.populationOf(null, null);
        ArrayList<Record> population2 = dao.populationOf(App.REGION, null);
        ArrayList<Record> population3 = dao.populationOf(null, "Europe");

        // then
        assertEquals(0, population1.size());
        assertEquals(0, population2.size());
        assertEquals(0, population3.size());
    }

    // Test that 5 items are returned in the list
    @Test
    void DAO_languageReport_returnedListLengthAsExpected()
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
    void DAO_languageReport_returnedLanguagesAsExpected()
    {
        // given
        ArrayList<String> expectedLanguages = new ArrayList<>(
                Arrays.asList("Chinese", "English", "Hindi", "Arabic", "Spanish"));

        // when
        ArrayList<Record> languages = dao.languageReport();

        // then
        for (Record language : languages) assertTrue(expectedLanguages.contains(language.getName()));
    }

    // Test all queries that take area filters return empty lists when an invalid filter is passed
    @Test
    void DAO_allQueries_emptyListWhenInvalidAreaFilterPassed()
    {
        // given
        String areaFilter = "planet";
        ArrayList<ArrayList<Record>> recordsLists = new ArrayList<>();

        // when
        recordsLists.add(dao.allCountriesIn(areaFilter, "Earth"));
        recordsLists.add(dao.topNCountriesIn(areaFilter, "Earth", 10));
        recordsLists.add(dao.allCitiesIn(areaFilter, "Earth"));
        recordsLists.add(dao.topNCitiesIn(areaFilter, "Earth", 10));
        recordsLists.add(dao.allCapitalCitiesIn(areaFilter, "Earth"));
        recordsLists.add(dao.topNCapitalCitiesIn(areaFilter, "Earth", 10));
        recordsLists.add(dao.populationLivingInAndNotInCities(areaFilter, "Earth"));
        recordsLists.add(dao.populationOf(areaFilter, "Earth"));

        // then
        for (ArrayList<Record> records : recordsLists) assertEquals(0, records.size());
    }

    /**
     * Integration tests covering the UserPrompt class
     */
    // Test that there are records for all queries if valid data is passed
    @Test
    void UserPrompt_executeQueryFromInput_happyPath()
    {
        // when
        for (int queryId = 0; queryId < 10; queryId++)
        {
            ArrayList<Record> records = userPrompt.executeQueryFromInput(queryId,
                    App.CONTINENT, "South America", 10);

            // then
            if (records != null) assertNotEquals(0, records.size());
        }
    }

    // Test that the query filters we
    @Test
    void UserPrompt_parseQueryInputForAreaFilter_areaFiltersWorkForLiveQueries()
    {
        // given
        ArrayList<String> areaFilters = new ArrayList<>();
        for (int i = 1; i < 7; i++) areaFilters.add(userPrompt.parseQueryInputForAreaFilter(8, i));

        // when
        for (String areaFilter: areaFilters)
        {
            userPrompt.executeQueryFromInput(8, areaFilter, "", 10); // then: no failure
        }
    }

    // Simulate a user entering valid input at each stage of the user prompt loop
    @Test
    void UserPrompt_start_happyPath()
    {
        // given
        provideInput("4\n" +
                "3\n" +
                "Nordic Countries\n" +
                "5\n" +
                "q");
        UserPrompt userPrompt = new UserPrompt(dao);

        // when
        userPrompt.start(); // then: no failure
    }

    // Test simulated user inputs where they quit at each stage of the loop don't cause failures
    @Test
    void UserPrompt_start_userCanQuitAtAnyStage()
    {
        // given
        ArrayList<UserPrompt> userPrompts = new ArrayList<>();
        provideInput("q\n");
        userPrompts.add(new UserPrompt(dao));
        provideInput("6\n" +
                "q\n");
        userPrompts.add(new UserPrompt(dao));
        provideInput("6\n" +
                "3\n" +
                "q\n");
        userPrompts.add(new UserPrompt(dao));
        provideInput("6\n" +
                "3\n" +
                "Caribbean\n" +
                "q");
        userPrompts.add(new UserPrompt(dao));
        provideInput("6\n" +
                "3\n" +
                "Caribbean\n" +
                "0\n" +
                "q");
        userPrompts.add(new UserPrompt(dao));

        // when
        for (UserPrompt userPrompt: userPrompts)
        {
            userPrompt.start();

            // then
            assertTrue(userPrompt.getUserWantsToQuit());
        }
    }
}
