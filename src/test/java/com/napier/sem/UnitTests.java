package com.napier.sem;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTests
{
    static DAO dao;
    static UserPrompt userPrompt;

    private void provideInput(String data)
    {
        ByteArrayInputStream TestIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(TestIn);
    }

    @BeforeAll
    static void init()
    {
        dao = new DAO(null);
        userPrompt = new UserPrompt(dao);
    }

    @AfterEach
    void restoreSystemInput() { System.setIn(System.in); }

    /**
     * Tests for the App class
     */
    // Test that the connection is null if the localhost is invalid
    @Test
    void App_main_noFailureFromInvalidLocation()
    {
        // given
        String[] args = new String[]{null};

        // when
        App.main(args); // then: no failure
    }

    @Test
    void App_connect_noFailureFromInvalidLocation()
    {
        // when
        App.connect(null, App.DATABASE_DRIVER, true); // then: no failure
    }

    // Test that the connection is null if the database driver is invalid
    @Test
    void App_connect_noFailureFromInvalidDriver()
    {
        // given
        String databaseDriver = "mysqf";

        // when
        Connection connection = App.connect("localhost:33061", databaseDriver, true); // then: no failure

        // then
        assertNull(connection);
    }

    // Test that if the connection is null there is no error
    @Test
    void App_disconnect_nullConnection()
    {
        // when
        App.disconnect(null); // then: no failure
    }

    /**
     * Tests for the DAO class
     */
    // Test the whereCondition is null when the areaFilter is unexpected
    @Test
    void DAO_getWhereCondition_unknownAreaFilterReturnsNull()
    {
        // given
        String areaFilter = "planet";

        // when
        String whereCondition = DAO.getWhereCondition(areaFilter, "Earth");

        // then
        assertNull(whereCondition);
    }

    // Test that the whereCondition contains our given area name
    @Test
    void DAO_getWhereCondition_areaNameAppearsInWhereCondition()
    {
        // given
        String areaName = "New York";

        // when
        String whereCondition = DAO.getWhereCondition(App.DISTRICT, areaName);

        // then
        assertTrue(whereCondition.contains(areaName));
    }

    // Test that the whereCondition is null when areaName is null
    @Test
    void DAO_getWhereCondition_nullAreaNameReturnsNull()
    {
        // when
        String whereCondition = DAO.getWhereCondition(App.WORLD, null);

        // then
        assertNull(whereCondition);
    }

    // Test that the whereCondition is null when areaFilter is null
    @Test
    void DAO_getWhereCondition_nullAreaFilterReturnsNull()
    {
        // when
        String whereCondition = DAO.getWhereCondition(null, "Earth");

        // then
        assertNull(whereCondition);
    }

    // Test that a null whereCondition will result in queryInvalid being true
    @Test
    void DAO_queryInvalid_falseWhenWhereConditionIsNull()
    {
        // when
        boolean result = dao.queryInvalid("query",
                null,
                App.WORLD,
                new ArrayList<>(),
                1);

        assertTrue(result);
    }

    // Test that an n value of less than 1 will result in queryInvalid being true
    @Test
    void DAO_queryInvalid_falseWhenNLessThanOne()
    {
        // given
        int n = 0;

        // when
        boolean result = dao.queryInvalid("query",
                "country.code LIKE '%'",
                App.WORLD,
                new ArrayList<>(),
                n);

        assertTrue(result);
    }

    // Test that an area filter not in the valid area filters will result in queryInvalid being true
    @Test
    void DAO_queryInvalid_falseWhenAreaFilterNotAllowed()
    {
        // given
        ArrayList<String> validAreaFilters = new ArrayList<>(Arrays.asList(App.WORLD, App.CONTINENT, App.REGION));
        String areaFilter = App.COUNTRY;

        // when
        boolean result = dao.queryInvalid("query",
                "country.name LIKE '%'",
                areaFilter,
                validAreaFilters,
                1);

        assertTrue(result);
    }

    // Test that queryInvalid is false when it is passed good values
    @Test
    void DAO_queryInvalid_happyPath()
    {
        // given
        String queryName = "query";
        String whereCondition = "country.name LIKE '%'";
        ArrayList<String> validAreaFilters = new ArrayList<>(Arrays.asList(App.WORLD, App.CONTINENT, App.REGION));
        String areaFilter = App.WORLD;
        int n = 5;

        // when
        boolean result = dao.queryInvalid(queryName,
                whereCondition,
                areaFilter,
                validAreaFilters,
                n);

        assertFalse(result);
    }

    // Test that countries list is empty when areaFilter and areaName are null
    @Test
    void DAO_allCountriesIn_bothArgumentsNullListEmpty()
    {
        // when
        ArrayList<Record> countries = dao.allCountriesIn(null, null);

        // then
        assertEquals(0, countries.size());
    }

    // Test that countries list is empty when areaFilter is null
    @Test
    void DAO_allCountriesIn_areaFilterNullListEmpty()
    {
        // when
        ArrayList<Record> countries = dao.allCountriesIn(null, "Scotland");

        // then
        assertEquals(0, countries.size());
    }

    // Test that countries list is empty when areaName is null
    @Test
    void DAO_allCountriesIn_areaNameNullListEmpty()
    {
        // when
        ArrayList<Record> countries = dao.allCountriesIn(App.COUNTRY, null);

        // then
        assertEquals(0, countries.size());
    }

    // check countries list is empty if called with an invalid areaFilter
    @Test
    void DAO_allCountriesIn_areaFilterWrongListEmpty()
    {
        // given
        String areaFilter = App.CITY;

        // when
        ArrayList<Record> countries = dao.allCountriesIn(areaFilter, "Glasgow");

        // then
        assertEquals(0, countries.size());
    }

    // check Test passes with valid areaFilter and areaName
    @Test
    void DAO_allCountriesIn_happyPath()
    {
        // given
        String areaFilter = App.COUNTRY;
        String areaName = "France";

        // then
        dao.allCountriesIn(areaFilter, areaName); // No Error
    }

    // Test that countries list is empty when areaFilter and areaName are null
    @Test
    void DAO_topNCountriesIn_bothArgumentsNullListEmpty()
    {
        // when
        ArrayList<Record> countries = dao.topNCountriesIn(null, null, 5);

        // then
        assertEquals(0, countries.size());
    }

    // Test that countries list is empty when areaFilter is null
    @Test
    void DAO_topNCountriesIn_areaFilterNullListEmpty()
    {
        // when
        ArrayList<Record> countries = dao.topNCountriesIn(null, "Scotland", 10);

        // then
        assertEquals(0, countries.size());
    }

    // Test that countries list is empty when areaName is null
    @Test
    void DAO_topNCountriesIn_areaNameNullListEmpty()
    {
        // when
        ArrayList<Record> countries = dao.topNCountriesIn(App.COUNTRY, null, 3);

        // then
        assertEquals(0, countries.size());
    }

    // Test that n cannot be less than 1
    @Test
    void DAO_topNCountriesIn_negativeNListEmpty()
    {
        // given
        int n = -3;

        // when
        ArrayList<Record> countries = dao.topNCountriesIn(App.COUNTRY, "China", n);

        // then
        assertEquals(0, countries.size());
    }

    // Check countries list is empty if called with an invalid areaFilter
    @Test
    void DAO_topNCountriesIn_areaFilterWrongListEmpty() {
        // given
        String areaFilter = App.DISTRICT;

        // when
        ArrayList<Record> countries = dao.topNCountriesIn(areaFilter, "Scotland", 15);

        // then
        assertEquals(0, countries.size());
    }

    // Check Test passes with valid areaFilter and areaName
    @Test
    void DAO_topNCountriesIn_happyPath()
    {
        // given
        String areaFilter = App.COUNTRY;
        String areaName = "France";

        // then
        dao.topNCountriesIn(areaFilter, areaName, 15); // No Error
    }

    /**
     * Unit Tests covering the DAO.allCitiesIn method
     */
    // Test that cities list is empty when areaFilter and areaName are null
    @Test
    void DAO_allCitiesIn_bothArgumentsNullListEmpty()
    {
        // when
        ArrayList<Record> cities = dao.allCitiesIn(null, null);

        // then
        assertEquals(0, cities.size());
    }

    // Test that cities list is empty when areaFilter is null
    @Test
    void DAO_allCitiesIn_areaFilterNullListEmpty()
    {
        // when
        ArrayList<Record> cities = dao.allCitiesIn(null, "Scotland");

        // then
        assertEquals(0, cities.size());
    }

    // Test that cities list is empty when areaName is null
    @Test
    void DAO_allCitiesIn_areaNameNullListEmpty()
    {
        // when
        ArrayList<Record> cities = dao.allCitiesIn(App.COUNTRY, null);

        // then
        assertEquals(0, cities.size());
    }

    // Check cities list is empty if called with an invalid areaFilter
    @Test
    void DAO_allCitiesIn_areaFilterWrongListEmpty()
    {
        // given
        String areaFilter = App.CITY;

        // when
        ArrayList<Record> cities = dao.allCitiesIn(areaFilter, "Glasgow");

        // then
        assertEquals(0, cities.size());
    }

    // Check Test passes with valid areaFilter and areaName
    @Test
    void DAO_allCitiesIn_happyPath()
    {
        // given
        String areaFilter = App.COUNTRY;
        String areaName = "France";

        // then
        dao.allCitiesIn(areaFilter, areaName); // No Error
    }

    // Test that cities list is empty when areaFilter and areaName are null
    @Test
    void DAO_topNCitiesIn_bothArgumentsNullListEmpty()
    {
        // when
        ArrayList<Record> cities = dao.topNCitiesIn(null, null, 5);

        // then
        assertEquals(0, cities.size());
    }

    // Test that cities list is empty when areaFilter is null
    @Test
    void DAO_topNCitiesIn_areaFilterNullListEmpty()
    {
        // when
        ArrayList<Record> cities = dao.topNCitiesIn(null, "Europe", 10);

        // then
        assertEquals(0, cities.size());
    }

    // Test that cities list is empty when areaName is null
    @Test
    void DAO_topNCitiesIn_areaNameNullListEmpty()
    {
        // when
        ArrayList<Record> cities = dao.topNCitiesIn(App.COUNTRY, null, 3);

        // then
        assertEquals(0, cities.size());
    }

    // Test that n cannot be less than or equal to 0
    @Test
    void DAO_topNCitiesIn_negativeNListEmpty()
    {
        // given
        Integer n = -3;

        // when
        ArrayList<Record> cities = dao.topNCitiesIn(App.COUNTRY, "China", n);

        // then
        assertEquals(0, cities.size());
    }

    // Check cities list is empty if called with an invalid areaFilter
    @Test
    void DAO_topNCitiesIn_areaFilterWrongListEmpty() {
        // given
        String areaFilter = App.CITY;

        // when
        ArrayList<Record> cities = dao.topNCitiesIn(areaFilter, "Houston", 15);

        // then
        assertEquals(0, cities.size());
    }

    // Check Test passes with valid areaFilter and areaName
    @Test
    void DAO_topNCitiesIn_happyPath()
    {
        // given
        String areaFilter = App.WORLD;
        String areaName = "";

        // then
        dao.topNCitiesIn(areaFilter, areaName, 15); // No Error
    }

    // Test that capitalCities list is empty when areaFilter and areaName are null
    @Test
    void DAO_allCapitalCitiesIn_bothArgumentsNullListEmpty()
    {
        // when
        ArrayList<Record> capitalCities = dao.allCapitalCitiesIn(null, null);

        // then
        assertEquals(0, capitalCities.size());
    }

    // Test that capitalCities list is empty when areaFilter is null
    @Test
    void DAO_allCapitalCitiesIn_areaFilterNullListEmpty()
    {
        // when
        ArrayList<Record> capitalCities = dao.allCapitalCitiesIn(null, "Scotland");

        // then
        assertEquals(0, capitalCities.size());
    }

    // Test that capitalCities list is empty when areaName is null
    @Test
    void DAO_allCapitalCitiesIn_areaNameNullListEmpty()
    {
        // when
        ArrayList<Record> capitalCities = dao.allCapitalCitiesIn(App.COUNTRY, null);

        // then
        assertEquals(0, capitalCities.size());
    }

    // Check capital cities list is empty if called with an invalid areaFilter
    @Test
    void DAO_allCapitalCitiesIn_areaFilterWrongListEmpty()
    {
        // given
        String areaFilter = App.DISTRICT;

        // when
        ArrayList<Record> capitalCities = dao.allCapitalCitiesIn(areaFilter, "Glasgow");

        // then
        assertEquals(0, capitalCities.size());
    }

    // Check Test passes with valid areaFilter and areaName
    @Test
    void DAO_allCapitalCitiesIn_happyPath()
    {
        // given
        String areaFilter = App.COUNTRY;
        String areaName = "France";

        // then
        dao.allCapitalCitiesIn(areaFilter, areaName); // No Error
    }

    // Test that capitalCities list is empty when areaFilter and areaName are null
    @Test
    void topNCapitalCitiesIn_bothArgumentsNullListEmpty()
    {
        // when
        ArrayList<Record> capitalCities = dao.topNCapitalCitiesIn(null, null, 5);

        // then
        assertEquals(0, capitalCities.size());
    }

    // Test that capitalCities list is empty when areaFilter is null
    @Test
    void DAO_topNCapitalCitiesIn_areaFilterNullListEmpty()
    {
        // when
        ArrayList<Record> capitalCities = dao.topNCapitalCitiesIn(null, "Europe", 10);

        // then
        assertEquals(0, capitalCities.size());
    }

    // Test that capitalCities list is empty when areaName is null
    @Test
    void DAO_topNCapitalCitiesIn_areaNameNullListEmpty()
    {
        // when
        ArrayList<Record> capitalCities = dao.topNCapitalCitiesIn(App.COUNTRY, null, 3);

        // then
        assertEquals(0, capitalCities.size());
    }

    // Test that n cannot be less than or equal to 0
    @Test
    void DAO_topNCapitalCitiesIn_negativeNListEmpty()
    {
        // given
        Integer n = -3;

        // when
        ArrayList<Record> capitalCities = dao.topNCapitalCitiesIn(App.COUNTRY, "China", n);

        // then
        assertEquals(0, capitalCities.size());
    }

    // Check capital cities list is empty if called with an invalid areaFilter
    @Test
    void DAO_topNCapitalCitiesIn_areaFilterWrongListEmpty() {
        // given
        String areaFilter = App.CITY;

        // when
        ArrayList<Record> capitalCities = dao.topNCapitalCitiesIn(areaFilter, "Canberra", 15);

        // then
        assertEquals(0, capitalCities.size());
    }

    // Check Test passes with valid areaFilter and areaName
    @Test
    void DAO_topNCapitalCitiesIn_happyPath()
    {
        // given
        String areaFilter = App.REGION;
        String areaName = "Nordic Countries";

        // then
        dao.topNCapitalCitiesIn(areaFilter, areaName, 15); // No Error
    }

    // Test that residenceReport list is empty when areaFilter and areaName are null
    @Test
    void DAO_populationLivingInAndNotInCities_bothArgumentsNullListEmpty()
    {
        // when
        ArrayList<Record> residenceReport = dao.populationLivingInAndNotInCities(null, null);

        // then
        assertEquals(0, residenceReport.size());
    }

    // Test that residenceReport list is empty when areaFilter is null
    @Test
    void DAO_populationLivingInAndNotInCities_areaFilterNullListEmpty()
    {
        // when
        ArrayList<Record> residenceReport = dao.populationLivingInAndNotInCities(null, "Scotland");

        // then
        assertEquals(0, residenceReport.size());
    }

    // Test that residenceReport list is empty when areaName is null
    @Test
    void DAO_populationLivingInAndNotInCities_areaNameNullListEmpty()
    {
        // when
        ArrayList<Record> residenceReport = dao.populationLivingInAndNotInCities(App.COUNTRY, null);

        // then
        assertEquals(0, residenceReport.size());
    }

    // Check cities list is empty if called with an invalid areaFilter
    @Test
    void DAO_populationLivingInAndNotInCities_areaFilterWrongListEmpty()
    {
        // given
        String areaFilter = App.WORLD;

        // when
        ArrayList<Record> cities = dao.populationLivingInAndNotInCities(areaFilter, "");

        // then
        assertEquals(0, cities.size());
    }

    // Check Test passes with valid areaFilter and areaName
    @Test
    void DAO_populationLivingInAndNotInCities_happyPath()
    {
        // given
        String areaFilter = App.CONTINENT;
        String areaName = "Asia";

        // then
        dao.populationLivingInAndNotInCities(areaFilter, areaName); // No Error
    }

    /**
     * Unit Tests covering the DAO.populationOf method
     */
    // Test that population list is empty when areaFilter and areaName are null
    @Test
    void DAO_populationOf_bothArgumentsNullListEmpty()
    {
        // when
        ArrayList<Record> population = dao.populationOf(null, null);

        // then
        assertEquals(0, population.size());
    }

    // Test that population list is empty when areaFilter is null
    @Test
    void DAO_populationOf_areaFilterNullListEmpty()
    {
        // when
        ArrayList<Record> population = dao.populationOf(null, "Scotland");

        // then
        assertEquals(0, population.size());
    }

    // Test that population list is empty when areaName is null
    @Test
    void DAO_populationOf_areaNameNullListEmpty()
    {
        // when
        ArrayList<Record> population = dao.populationOf(App.COUNTRY, null);

        // then
        assertEquals(0, population.size());
    }

    // Check Test passes with valid areaFilter and areaName
    @Test
    void DAO_populationOf_happyPath()
    {
        // given
        String areaFilter = App.CITY;
        String areaName = "Glasgow";

        // then
        dao.populationOf(areaFilter, areaName); // No Error
    }

    // Check no error is thrown when method is called with a null connection
    @Test
    void DAO_languageReport_happyPath()
    {
        // then
        dao.languageReport(); // No Error
    }

    /**
     * Tests for the Record class
     */
    // Tests that when a country object is constructed, the getters and toString
    // return the same fields that we passed in.
    @Test
    void Record_TestGettersAndToStringForCountry()
    {
        // given
        String countryCode = "SCT";
        String name = "Scotland";
        String continent = "Europe";
        String region = "Great Britain";
        String capital = "Edinburgh";
        long population = 6000000L;
        String expectedToStringOutput = App.HORIZONTAL_LINE + "\n" +
                "Country code: SCT | Name: Scotland | Continent: Europe\n" +
                "Region: Great Britain | Population: 6,000,000 | Capital: Edinburgh";


        // when
        Record record = new Record(countryCode, name, continent, region, population, capital);

        // then
        assertEquals(name, record.getName());
        assertEquals(continent, record.getContinent());
        assertEquals(population, record.getPopulation());
        assertEquals(expectedToStringOutput, record.toString());
    }

    // Tests that when a capital city object is constructed, the getters and toString
    // return the same fields that we passed in.
    @Test
    void Record_TestGettersAndToStringForCapitalCity()
    {
        // given
        String name = "Glasgow";
        String country = "Scotland";
        String region = "Great Britain";
        String continent = "Europe";
        long population = 6000000L;
        String expectedToStringOutput = App.HORIZONTAL_LINE + "\n" +
                "Name: Glasgow | Country: Scotland | Population: 6,000,000";

        // when
        Record capitalCity = new Record(name, country, region, continent, population);

        // then
        assertEquals(name, capitalCity.getName());
        assertEquals(country, capitalCity.getCountry());
        assertEquals(continent, capitalCity.getContinent());
        assertEquals(population, capitalCity.getPopulation());
        assertEquals(expectedToStringOutput, capitalCity.toString());
    }

    // Tests that when a city object is constructed, the getters and toString
    // return the same fields that we passed in.
    @Test
    void Record_TestGettersAndToStringForCity()
    {
        // given
        String name = "Wishae";
        String country = "Scotland";
        String district = "North Lanarkshire";
        long population = 10000L;
        String expectedToStringOutput = App.HORIZONTAL_LINE + "\n" +
                "Name: Wishae |  Country: Scotland \n" +
                "District: North Lanarkshire |  Population: 10,000";

        // when
        Record city = new Record(name, country, district, population);

        // then
        assertEquals(name, city.getName());
        assertEquals(country, city.getCountry());
        assertEquals(population, city.getPopulation());
        assertEquals(expectedToStringOutput, city.toString());
    }

    // Tests that when a population residence report object is constructed, the getters and toString
    // return the same fields that we passed in.
    @Test
    void Record_TestGettersAndToStringForPopulationResidenceReport()
    {
        // given
        String name = "East Kilbride";
        long population = 7000L;
        long populationLivingInCities = 6999L;
        Double percentageLivingInCities = 99.99D;
        long populationNotLivingInCities = 1L;
        Double percentageNotLivingInCities = 0.01D;
        String expectedToStringOutput = App.HORIZONTAL_LINE + "\n" +
                "Area: East Kilbride | Total population: 7,000\n" +
                "Population living in Cities: 6,999 (99.99%)\n" +
                "Population not living in Cities: 1 (0.01%)";

        // when
        Record residenceReport = new Record(name, population, populationLivingInCities, percentageLivingInCities, populationNotLivingInCities, percentageNotLivingInCities);

        // then
        assertEquals(name, residenceReport.getName());
        assertEquals(population, residenceReport.getPopulation());
        assertEquals(expectedToStringOutput, residenceReport.toString());
    }

    // Tests that when a language object is constructed, the getters and toString
    // return the same fields that we passed in.
    @Test
    void Record_TestGettersAndToStringForLanguage()
    {
        // given
        String language = "Gaelic";
        long speakers = 3L;
        Integer percentage = 0;
        String expectedToStringOutput = App.HORIZONTAL_LINE + "\n" +
                "Language: Gaelic |  Speakers: 3 | % of world's population: 0%";

        // when
        Record Language = new Record(language, speakers, percentage);

        // then
        assertEquals(language, Language.getName());
        assertEquals(expectedToStringOutput, Language.toString());
    }

    // Tests that when a population object is constructed, the getters and toString
    // return the same fields that we passed in.
    @Test
    void Record_TestGettersAndToStringForPopulation()
    {
        // given
        String name = "China";
        Long population = 999000000L;
        String expectedToStringOutput = App.HORIZONTAL_LINE + "\n" +
                "Area: China |  Population: 999,000,000";

        // when
        Record Population = new Record(name, population);

        // then
        assertEquals(name, Population.getName());
        assertEquals(population, Population.getPopulation());
        assertEquals(expectedToStringOutput, Population.toString());
    }

    /**
     * Tests for the QueryInfo object
     */
    // Tests that when a QueryInfo object is constructed, the getters return the same fields that we passed in.
    @Test
    void QueryInfo_TestGettersForQueryInfo()
    {
        // given
        String queryDescription = "All countries in";
        String[] areaFilterDescriptions = {"the world", "a given continent", "a given region"};

        // when
        QueryInfo queryInfo = new QueryInfo(queryDescription, areaFilterDescriptions);

        // then
        assertEquals(queryDescription, queryInfo.getQueryDescription());
        assertEquals(areaFilterDescriptions, queryInfo.getAreaFilterDescriptions());
    }

    /**
     * Tests for the UserPrompt class
     */
    // Tests that obtainAreaFilterChoice outputs an empty string when 9 is entered
    @Test
    void UserPrompt_obtainAreaFilterChoice_queryIdNineResultsInEmptyString()
    {
        // given
        int queryID = 9;
        String expectedOutput = "";

        //when
        String output = userPrompt.obtainAreaFilterChoice(queryID);

        //then
        assertEquals(expectedOutput, output);
    }

    // Tests that formatInput successfully removes spaces and capital letters
    @Test
    void UserPrompt_formatInput_removesWhiteSpaceAndCapitals()
    {
        // given
        String input = "   AbCd \t";
        String expectedOutput = "abcd";

        //when
        String output = userPrompt.formatInput(input);

        //then
        assertEquals(expectedOutput, output);
    }

    // Test that the user can enter null input without the app crashing
    @Test
    void UserPrompt_formatInput_noFailureWhenNullEntered()
    {
        // when
        String formattedInput = userPrompt.formatInput(null); // then: no failure

        // then
        assertNull(formattedInput); // then: no failure
    }

    // Test that parseQueryInputForAreaFilter returns null on receiving invalid input
    @Test
    void UserPrompt_parseQueryInputForAreaFilter_invalidParametersReturnNull()
    {
        // given
        int areaChoice = -1;
        int queryId = -1;

        // when
        String areaFilter1 = userPrompt.parseQueryInputForAreaFilter(1, areaChoice);
        String areaFilter2 = userPrompt.parseQueryInputForAreaFilter(queryId, 1);

        // then
        assertNull(areaFilter1);
        assertNull(areaFilter2);
    }

    // Test that a query choice of 1 results in an area filter of "world" for all query ids except 7, which cannot
    // have "world" as a query filter
    @Test
    void UserPrompt_parseQueryInputForAreaFilter_handlesQuerySevenCorrectly()
    {
        // given
        int areaFilterChoice = 1;

        for (int i = 1; i < 9; i++)
        {
            // when
            String areaFilter = userPrompt.parseQueryInputForAreaFilter(i, areaFilterChoice);

            // then
            if (i == 7) assertEquals(App.CONTINENT, areaFilter);
            else assertEquals(App.WORLD, areaFilter);
        }
    }

    // Tests that parseQueryInputForAreaFilter returns the expected output
    @Test
    void UserPrompt_parseQueryInputForAreaFilter_happyPaths()
    {
        // given
        int queryId = 8;
        String[] expectedOutputs = new String[]{App.WORLD, App.CONTINENT,
                App.REGION, App.COUNTRY, App.DISTRICT, App.CITY};

        for (int i = 1; i < 7; i++)
        {
            //when
            String output = userPrompt.parseQueryInputForAreaFilter(queryId, i);

            //then
            assertEquals(expectedOutputs[i-1], output);
        }
    }

    // Tests that executeQueryFromInput outputs an empty list when passed bad parameters
    @Test
    void UserPrompt_executeQueryFromInput_invalidEntryResultsInEmptyList()
    {
        // given
        String areaFilter = "planet";
        int n = -2;

        // when
        ArrayList<Record> records1 = userPrompt.executeQueryFromInput(1, areaFilter, "Earth", 5);
        ArrayList<Record> records2 = userPrompt.executeQueryFromInput(2, App.REGION, "Caribbean", n);

        // then
        assertEquals(0, records1.size());
        assertEquals(0, records2.size());
    }

    // Test that an invalid query ID results in executeQueryFromInput returning null
    @Test
    void UserPrompt_executeQueryFromInput_returnsNullWhenPassedInvalidQueryId()
    {
        // given
        int queryId = -10;

        // when
        ArrayList<Record> records = userPrompt.executeQueryFromInput(queryId, App.CONTINENT, "Europe", 10);

        // then
        assertNull(records);
    }

    // Test that there are no failures when expected input is passed
    @Test
    void UserPrompt_executeQueryFromInput_happyPaths()
    {
        // given
        String areaFilter = App.CONTINENT;
        String areaName = "Asia";
        int n = 10;

        // when
        for (int queryId = 1; queryId < 10; queryId ++)
        {
            userPrompt.executeQueryFromInput(queryId, areaFilter, areaName, n); // then: no failures
        }
    }

    // Test that show records throws no errors when passed null, an empty list or a list with entries
    @Test
    void UserPrompt_showRecords_noFailuresOnAllInputs()
    {
        // given
        ArrayList<Record> records1 = new ArrayList<>();
        ArrayList<Record> records2 = new ArrayList<>(Arrays.asList(
                new Record("English", 800000000, 15),
                new Record("Chinese", 1000000000, 50)));

        // when
        userPrompt.showRecords(null);
        userPrompt.showRecords(records1);
        userPrompt.showRecords(records2); // then: no failures
    }

    // Tests that the expected area filter is returned with simulated user input
    @Test
    void UserPrompt_obtainAreaFilterChoice_happyPath()
    {
        // given
        int queryId = 1;
        provideInput("2\n");
        UserPrompt userPrompt = new UserPrompt(dao);

        // when
        String areaFilter = userPrompt.obtainAreaFilterChoice(queryId);

        // then
        assertEquals(App.CONTINENT, areaFilter);
    }

    // Test that area filter is null if the user quits during entry
    @Test
    void UserPrompt_obtainAreaFilterChoice_nullIfUserQuits()
    {
        // given
        int queryId = 1;
        provideInput("q\n");
        UserPrompt userPrompt = new UserPrompt(dao);

        // when
        String areaFilter = userPrompt.obtainAreaFilterChoice(queryId);

        // then
        assertNull(areaFilter);
    }

    // Test that when invalid input is entered, the query loops
    @Test
    void UserPrompt_obtainInputWithPrompt_loopsUntilValidInput()
    {
        // given
        provideInput("invalid\nstring\n100\n0\n1");
        UserPrompt userPrompt = new UserPrompt(dao);

        // when
        int input = userPrompt.obtainInputWithPrompt("Test, enter 1-10", 10);

        // then
        assertEquals(1, input);
    }

    // Test that -1 is returned when the user enters q
    @Test
    void UserPrompt_obtainInputWithPrompt_returnsNegativeOneWhenQEntered()
    {
        // given
        provideInput("q");
        UserPrompt userPrompt = new UserPrompt(dao);

        // when
        int input = userPrompt.obtainInputWithPrompt("Test, enter 1-10", 10);

        // then
        assertEquals(-1, input);
    }

    // Test that the input number is returned if it's valid
    @Test
    void UserPrompt_obtainInputWithPrompt_returnsIntegerIfValid()
    {
        // given
        provideInput("4");
        UserPrompt userPrompt = new UserPrompt(dao);

        // when
        int input = userPrompt.obtainInputWithPrompt("Test, enter 1-10", 10);

        // then
        assertEquals(4, input);
    }

    // Test that the user can quit when prompted for the area name
    @Test
    void UserPrompt_start_userCanQuitDuringAreaNameEntry()
    {
        // given
        provideInput("6\n" +
                "2\n" +
                "q");
        UserPrompt userPrompt = new UserPrompt(dao);

        // when
        userPrompt.start(); // then: no failure
    }

    // Test that valid input does not cause a failure
    @Test
    void UserPrompt_start_happyPathNoFailures()
    {
        // given
        provideInput("6\n" +
                "2\n" +
                "South America\n" +
                "10\n" +
                "q");
        UserPrompt userPrompt = new UserPrompt(dao);

        // when
        userPrompt.start(); // then: no failure
    }
}
