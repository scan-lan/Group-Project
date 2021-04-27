package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    // Test the app runs fine with a simulate series of user inputs
    @Test
    void App_main_happyPath()
    {
        // given
        provideInput("7\n1\nOceania\nq");

        // when
        App.main(new String[]{}); // then: no failures
    }

    // Test that null is returned when a nonsense query is given
    @Test
    void DAO_executeStatement_nullWhenStatementIsInvalid()
    {
        // given
        String statementString = "SELECT ALL FROM EVERYWHERE";

        // when
        List<Record> countries= dao.executeStatement(statementString, App.COUNTRY);

        // then
        assertNull(countries,
                "Test that null is returned when a nonsense query is given");
    }

    // Test that all countries in query are in the given area
    @Test
    void DAO_allCountriesIn_allAreasMatchFilter()
    {
        // given
        String areaName = "Africa";

        // when
        List<Record> countries = dao.allCountriesIn(App.CONTINENT, areaName);

        // then
        for (Record country : countries)
        {
            assertEquals(areaName,
                    country.getContinent(),
                    "Test that all countries in query are in the given area");
        }
    }

    // Test that an empty array is returned when the areaName is set incorrectly
    @Test
    void DAO_allCountriesIn_arrayIsEmptyWhenAreaNameIsInvalid()
    {
        // given
        String areaName = "SmurfCity USA";

        // when
        List<Record> countries = dao.allCountriesIn(App.REGION, areaName);

        // then
        assertEquals(0, countries.size(),
                "Test that an empty array is returned when the areaName is set incorrectly");
    }

    // Test that the results are listed in descending order.
    @Test
    void DAO_allCountriesIn_countriesPopulationIsInDescendingOrder()
    {
        // when
        List<Record> countries = dao.allCountriesIn(App.CONTINENT, "Asia");

        // then
        for (int i = 0; i < (countries.size()-1); i++)
        {
            assertTrue(countries.get(i).getPopulation() >= countries.get(i+1).getPopulation(),
                    "Test that the results are listed in descending order.");
        }
    }

    // Test that countries list is empty when areaFilter and areaName are null
    @Test
    void DAO_allCountriesIn_nullArgumentsCauseEmptyList()
    {
        // given
        List<List<Record>> countriesLists = new ArrayList<>();

        // when
        countriesLists.add(dao.allCountriesIn(null, null));
        countriesLists.add(dao.allCountriesIn(App.REGION, null));
        countriesLists.add(dao.allCountriesIn(null, "Europe"));

        // then
        for (List<Record> countries : countriesLists)
        {
            assertEquals(0, countries.size(),
                    "Test that countries list is empty when areaFilter and areaName are null");
        }
    }

    // Test that the number of results is less than or equal to integer n
    @Test
    void DAO_topNCountriesIn_arrayIsCorrectSize()
    {
        // given
        int n = 17;

        // when
        List<Record> countries = dao.topNCountriesIn(App.WORLD, "", n);

        // then
        assertTrue(countries.size() <= n,
                "Test that the number of results is less than or equal to integer n");
    }

    // Test that an empty array is returned when integer n is set to 0
    @Test
    void DAO_topNCountriesIn_whenNZeroArrayIsEmpty()
    {
        // given
        int n = 0;

        // when
        List<Record> countries = dao.topNCountriesIn(App.WORLD, "", n);

        // then
        assertEquals(0, countries.size(),
                "Test that an empty array is returned when integer n is set to 0");
    }

    // Test that an empty array is returned when the areaName is set incorrectly
    @Test
    void DAO_topNCountriesIn_arrayIsEmptyWhenAreaNameIsInvalid()
    {
        // given
        String areaName = "Scotland";

        // when
        List<Record> countries = dao.topNCountriesIn(App.CONTINENT, areaName, 10);

        // then
        assertEquals(0, countries.size(),
                "Test that an empty array is returned when the areaName is set incorrectly");
    }

    // Test all countries are in the given area that was passed
    @Test
    void DAO_topNCountriesIn_allAreasMatchFilter()
    {
        // given
        String areaName = "Europe";

        // when
        List<Record> countries = dao.topNCountriesIn(App.CONTINENT, areaName, 10);

        // then
        for (Record country : countries) assertEquals("Europe", country.getContinent(),
                "Test all countries are in the given area that was passed");
    }

    // Test that the results are listed in descending order.
    @Test
    void DAO_topNCountriesIn_countriesPopulationIsInDescendingOrder()
    {
        // when
        List<Record> countries = dao.topNCountriesIn(App.CONTINENT, "Europe", 5);

        // then
        for (int i = 0; i < (countries.size()-1); i++)
        {
            assertTrue(countries.get(i).getPopulation() >= countries.get(i+1).getPopulation(),
                    "Test that the results are listed in descending order.");
        }
    }

    // Test that countries list is empty when areaFilter and areaName are null
    @Test
    void DAO_topNCountriesIn_nullArgumentsCauseEmptyList()
    {
        // given
        List<List<Record>> countriesLists = new ArrayList<>();

        // when
        countriesLists.add(dao.topNCountriesIn(null, null, 10));
        countriesLists.add(dao.topNCountriesIn(App.REGION, null, 10));
        countriesLists.add(dao.topNCountriesIn(null, "Europe", 10));

        // then
        for (List<Record> countries: countriesLists)
        {
        assertEquals(0, countries.size(),
                "Test that countries list is empty when areaFilter and areaName are null");
        }
    }

    // Test that all cities in query are in the given area
    @Test
    void DAO_allCitiesIn_allAreasMatchFilter()
    {
        // given
        String areaName = "Japan";

        // when
        List<Record> cities = dao.allCitiesIn(App.COUNTRY, areaName);

        // then
        for (Record city: cities) assertEquals(areaName, city.getCountry(),
                "Test that all cities in query are in the given area");
    }

    // Test that an empty array is returned when the areaName is set incorrectly
    @Test
    void DAO_allCitiesIn_arrayIsEmptyWhenAreaNameIsInvalid()
    {
        // given
        String areaName = "Invalid Area";

        // when
        List<Record> cities = dao.allCitiesIn(App.COUNTRY, areaName);

        // then
        assertEquals(0, cities.size(),
                "Test that an empty array is returned when the areaName is set incorrectly");
    }

    // Test that the results are listed in descending order.
    @Test
    void DAO_allCitiesIn_allCitiesPopulationIsInDescendingOrder()
    {
        // when
        List<Record> cities = dao.allCitiesIn(App.COUNTRY, "United Kingdom");

        // then
        for (int i = 0; i < (cities.size()-1); i++)
        {
            assertTrue(cities.get(i).getPopulation() >= cities.get(i+1).getPopulation(),
                    "Test that the results are listed in descending order.");
        }
    }

    // Test that cities list is empty when areaFilter and areaName are null
    @Test
    void DAO_allCitiesIn_nullArgumentsCauseEmptyList()
    {
        // given
        List<List<Record>> citiesLists = new ArrayList<>();

        // when
        citiesLists.add(dao.allCitiesIn(null, null));
        citiesLists.add(dao.allCitiesIn(App.REGION, null));
        citiesLists.add(dao.allCitiesIn(null, "Europe"));

        // then
        for (List<Record> cities: citiesLists)
        {
            assertEquals(0, cities.size(),
                    "Test that cities list is empty when areaFilter and areaName are null");
        }
    }

    // Test that the number of results is less than or equal to integer n
    @Test
    void DAO_topNCitiesIn_arrayIsCorrectSize ()
    {
        // given
        int n = 5;

        // when
        List<Record> cities = dao.topNCitiesIn(App.CONTINENT, "europe", n);

        // then
        assertTrue(cities.size() <= n,
                "Test that the number of results is less than or equal to integer n");
    }

    // Test that an empty array is returned when integer n is set to 0
    @Test
    void DAO_topNCitiesIn_whenNZeroArrayIsEmpty ()
    {
        // given
        int n = 0;

        // when
        List<Record> cities = dao.topNCitiesIn(App.WORLD, "", n);

        // then
        assertEquals(0, cities.size(),
                "Test that an empty array is returned when integer n is set to 0");
    }

    // Test that an empty array is returned when the areaName is set incorrectly
    @Test
    void DAO_topNCitiesIn_arrayIsEmptyWhenAreaNameIsInvalid ()
    {
        // given
        String areaName = "SmurfCity USA";

        // when
        List<Record> cities = dao.topNCitiesIn(App.REGION, areaName, 10);

        // then
        assertEquals(0, cities.size(),
                "Test that an empty array is returned when the areaName is set incorrectly");
    }

    // Test all cities are in the given area that was passed
    @Test
    void DAO_topNCitiesIn_allAreasMatchFilter ()
    {
        // given
        String areaName = "United Kingdom";

        // when
        List<Record> cities = dao.topNCitiesIn(App.COUNTRY, areaName, 10);

        // then
        for (Record city : cities) assertEquals("United Kingdom", city.getCountry(),
                "Test all cities are in the given area that was passed");
    }

    // Test that the results are listed in descending order.
    @Test
    void DAO_topNCitiesIn_topNCitiesPopulationIsInDescendingOrder()
    {
        // when
        List<Record> cities = dao.topNCitiesIn(App.CONTINENT, "Europe", 5);

        // then
        for (int i = 0; i < (cities.size()-1); i++)
        {
            assertTrue(cities.get(i).getPopulation() >= cities.get(i+1).getPopulation(),
                    "Test that the results are listed in descending order.");
        }
    }

    // Test that cities list is empty when areaFilter and areaName are null
    @Test
    void DAO_topNCitiesIn_nullArgumentsCauseEmptyList()
    {
        // given
        List<List<Record>> citiesList = new ArrayList<>();

        // when
        citiesList.add(dao.topNCitiesIn(null, null, 10));
        citiesList.add(dao.topNCitiesIn(App.REGION, null, 10));
        citiesList.add(dao.topNCitiesIn(null, "Europe", 10));

        // then
        for (List<Record> cities: citiesList)
        {
            assertEquals(0, cities.size(),
                    "Test that cities list is empty when areaFilter and areaName are null");
        }
    }

    // Test that all capital cities in query are in the given area
    @Test
    void DAO_allCapitalCitiesIn_allAreasMatchFilter()
    {
        // given
        String areaName = "Asia";

        // when
        List<Record> capitalCities = dao.allCapitalCitiesIn(App.CONTINENT, areaName);

        // then
        for (Record capitalCity : capitalCities) assertEquals(areaName, capitalCity.getContinent(),
                "Test that all capital cities in query are in the given area");
    }

    // Test that an empty array is returned when the areaName is set incorrectly
    @Test
    void DAO_allCapitalCitiesIn_arrayIsEmptyWhenAreaNameIsInvalid()
    {
        // given
        String areaName = "East Fife";

        // when
        List<Record> capitalCities = dao.allCapitalCitiesIn(App.CONTINENT, areaName);

        // then
        assertEquals(0, capitalCities.size(),
                "Test that an empty array is returned when the areaName is set incorrectly");
    }

    // Test that the results are listed in descending order.
    @Test
    void DAO_allCapitalCitiesIn_capitalCitiesPopulationIsInDescendingOrder()
    {
        // when
        List<Record> capitalCities = dao.allCapitalCitiesIn(App.CONTINENT, "Asia");

        // then
        for (int i = 0; i < (capitalCities.size()-1); i++)
        {
            assertTrue(capitalCities.get(i).getPopulation() >= capitalCities.get(i+1).getPopulation(),
                    "Test that the results are listed in descending order.");
        }
    }

    // Test that capital cities list is empty when areaFilter and areaName are null
    @Test
    void DAO_allCapitalCitiesIn_nullArgumentsCauseEmptyList()
    {
        // given
        List<List<Record>> capitalCitiesLists = new ArrayList<>();

        // when
        capitalCitiesLists.add(dao.allCapitalCitiesIn(null, null));
        capitalCitiesLists.add(dao.allCapitalCitiesIn(App.REGION, null));
        capitalCitiesLists.add(dao.allCapitalCitiesIn(null, "Europe"));

        // then
        for (List<Record> capitalCities: capitalCitiesLists)
        {
            assertEquals(0, capitalCities.size(),
                    "Test that capital cities list is empty when areaFilter and areaName are null");
        }
    }

    // Test that the number of results is less than or equal to integer n
    @Test
    void DAO_topNCapitalCitiesIn_arrayIsCorrectSize ()
    {
        // given
        int n = 5;

        // when
        List<Record> capitalCities = dao.topNCapitalCitiesIn(App.DISTRICT, "Scotland", n);

        // then
        assertTrue(capitalCities.size() <= n,
                "Test that the number of results is less than or equal to integer n");
    }

    // Test that an empty array is returned when integer n is set to 0
    @Test
    void DAO_topNCapitalCitiesIn_whenNZeroArrayIsEmpty ()
    {
        // given
        int n = 0;

        // when
        List<Record> capitalCities = dao.topNCapitalCitiesIn(App.WORLD, "", n);

        // then
        assertEquals(0, capitalCities.size(),
        "Test that an empty array is returned when integer n is set to 0");
    }

    // Test that an empty array is returned when the areaName is set incorrectly
    @Test
    void DAO_topNCapitalCitiesIn_arrayIsEmptyWhenAreaNameIsInvalid ()
    {
        // given
        String areaName = "SmurfCity USA";

        // when
        List<Record> capitalCities = dao.topNCapitalCitiesIn(App.REGION, areaName, 10);

        // then
        assertEquals(0, capitalCities.size(),
                "Test that an empty array is returned when the areaName is set incorrectly");
    }

    // Test all capital cities are in the given area that was passed
    @Test
    void DAO_topNCapitalCitiesIn_allAreasMatchFilter ()
    {
        // given
        String areaName = "United Kingdom";

        // when
        List<Record> capitalCities = dao.topNCapitalCitiesIn(App.COUNTRY, areaName, 10);

        // then
        for (Record capitalCity : capitalCities) assertEquals("United Kingdom", capitalCity.getCountry(),
                "Test all capital cities are in the given area that was passed");
    }

    // Test that the results are listed in descending order.
    @Test
    void DAO_topNCapitalCitiesIn_capitalCitiesPopulationIsInDescendingOrder()
    {
        // when
        List<Record> capitalCities = dao.topNCapitalCitiesIn(App.CONTINENT, "Asia", 5);

        // then
        for (int i = 0; i < (capitalCities.size()-1); i++)
        {
            assertTrue(capitalCities.get(i).getPopulation() >= capitalCities.get(i+1).getPopulation(),
                    "Test that the results are listed in descending order.");
        }
    }

    // Test that capital cities list is empty when areaFilter and areaName are null
    @Test
    void DAO_topNCapitalCitiesIn_nullArgumentsCauseEmptyList()
    {
        // given
        List<List<Record>> capitalCitiesLists = new ArrayList<>();

        // when
        capitalCitiesLists.add(dao.topNCapitalCitiesIn(null, null, 10));
        capitalCitiesLists.add(dao.topNCapitalCitiesIn(App.REGION, null, 10));
        capitalCitiesLists.add(dao.topNCapitalCitiesIn(null, "Europe", 10));

        // then
        for (List<Record> capitalCities: capitalCitiesLists)
        {
            assertEquals(0, capitalCities.size(),
                    "Test that capital cities list is empty when areaFilter and areaName are null");
        }
    }

    // Test that area in query is the same as the area output
    @Test
    void DAO_populationLivingInAndNotInCities_correctAreaIsOutput()
    {
        // given
        String areaName = "Japan";

        // when
        List<Record> residenceReport = dao.populationLivingInAndNotInCities(App.COUNTRY, areaName);

        // then
        assertEquals(areaName, residenceReport.get(0).getName(),
                "Test that area in query is the same as the area output");
    }

    // Test that an empty array is returned when the areaName is set incorrectly
    @Test
    void DAO_populationLivingInAndNotInCities_arrayIsEmptyWhenAreaNameIsInvalid()
    {
        // given
        String areaName = "Invalid Area";

        // when
        List<Record> residenceReport = dao.populationLivingInAndNotInCities(App.REGION, areaName);

        // then
        assertEquals(0, residenceReport.size(),
                "Test that an empty array is returned when the areaName is set incorrectly");
    }

    // Test that population residence report list is empty when areaFilter and areaName are null
    @Test
    void DAO_populationLivingInAndNotInCities_nullArgumentsCauseEmptyList()
    {
        // given
        List<List<Record>> residenceReports = new ArrayList<>();

        // when
        residenceReports.add(dao.populationLivingInAndNotInCities(null, null));
        residenceReports.add(dao.populationLivingInAndNotInCities(App.REGION, null));
        residenceReports.add(dao.populationLivingInAndNotInCities(null, "Europe"));

        // then
        for (List<Record> residenceReport: residenceReports)
        {
            assertEquals(0, residenceReport.size(),
                    "Test that population residence report list is empty when areaFilter and areaName are null");
        }
    }

    // Test that area in query is the same as the area output
    @Test
    void DAO_populationOf_correctAreaIsOutput()
    {
        // given
        String areaName = "Glasgow";

        // when
        List<Record> population = dao.populationOf(App.CITY, areaName);

        // then
        assertEquals(areaName, population.get(0).getName(),
                "Test that area in query is the same as the area output");
    }

    // Test that an empty array is returned when the areaName is set incorrectly
    @Test
    void DAO_populationOf_arrayIsEmptyWhenAreaNameIsInvalid()
    {
        // given
        String areaName = "Invalid Area";

        // when
        List<Record> population = dao.populationOf(App.REGION, areaName);

        // then
        assertEquals(0, population.size(),
                "Test that an empty array is returned when the areaName is set incorrectly");
    }

    // Test that population list is empty when areaFilter and areaName are null
    @Test
    void DAO_populationOf_nullArgumentsCauseEmptyList()
    {
        // given
        List<List<Record>> populations = new ArrayList<>();

        // when
        populations.add(dao.populationOf(null, null));
        populations.add(dao.populationOf(App.REGION, null));
        populations.add(dao.populationOf(null, "Europe"));

        // then
        for (List<Record> population: populations)
        {
            assertEquals(0, population.size(),
                    "Test that population list is empty when areaFilter and areaName are null");
        }
    }

    // Test that 5 items are returned in the list
    @Test
    void DAO_languageReport_returnedListLengthAsExpected()
    {
        // given
        Integer expectedLength = 5;

        // when
        List<Record> languages = dao.languageReport();

        // then
        assertEquals(expectedLength, languages.size(),
                "Test that 5 items are returned in the list");
    }

    // Test that the 5 expected languages are returned
    @Test
    void DAO_languageReport_returnedLanguagesAsExpected()
    {
        // given
        ArrayList<String> expectedLanguages = new ArrayList<>(
                Arrays.asList("Chinese", "English", "Hindi", "Arabic", "Spanish"));

        // when
        List<Record> languages = dao.languageReport();

        // then
        for (Record language : languages) assertTrue(expectedLanguages.contains(language.getName()),
                "Test that the 5 expected languages are returned");
    }

    // Test all queries that take area filters return empty lists when an invalid filter is passed
    @Test
    void DAO_allQueries_emptyListWhenInvalidAreaFilterPassed()
    {
        // given
        String areaFilter = "planet";
        ArrayList<List<Record>> recordsLists = new ArrayList<>();

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
        for (List<Record> records : recordsLists) assertEquals(0, records.size(),
                "Test all queries that take area filters return empty lists when an invalid filter is passed");
    }

    // Test that there are records for all queries if valid data is passed
    @Test
    void UserPrompt_executeQueryFromInput_happyPath()
    {
        // when
        for (int queryId = 0; queryId < 10; queryId++)
        {
            List<Record> records = userPrompt.executeQueryFromInput(queryId,
                    App.CONTINENT, "South America", 10);

            // then
            if (records != null) assertNotEquals(0, records.size(),
                    "Test that there are records for all queries if valid data is passed");
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

    // Test that the user can quit at any stage of the loop
    @Test
    void UserPrompt_start_userCanQuitAtAnyStage()
    {
        // given
        ArrayList<UserPrompt> userPrompts = new ArrayList<>();
        provideInput("q\n");
        userPrompts.add(new UserPrompt(dao));
        provideInput("9\n" +
                "q\n");
        userPrompts.add(new UserPrompt(dao));
        provideInput("1\n" +
                "1\n" +
                "q\n");
        userPrompts.add(new UserPrompt(dao));
        provideInput("8\n" +
                "3\n" +
                "Caribbean\n" +
                "q");
        userPrompts.add(new UserPrompt(dao));
        provideInput("6\n" +
                "3\n" +
                "Caribbean\n" +
                "10\n" +
                "q");
        userPrompts.add(new UserPrompt(dao));

        // when
        for (UserPrompt userPrompt: userPrompts)
        {
            // then
            userPrompt.start(); // no failure
            assertTrue(userPrompt.getUserWantsToQuit(),
                    "Test that the user can quit at any stage of the loop");
        }
    }
}
