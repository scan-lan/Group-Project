package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTests
{
    static DAO dao;
    static UserPrompt userPrompt;

    @BeforeAll
    static void init()
    {
        dao = new DAO(null);
        userPrompt = new UserPrompt(dao);
    }

    /**
     * Tests for the App class
     */
    // test that the connection is null if the localhost is invalid
    @Test
    void App_main_noFailureFromInvalidLocation()
    {
        // given
        String[] args = new String[]{null};

        // when
        App.main(args); // no failure
    }

    @Test
    public void App_connect_noFailureFromInvalidLocation()
    {
        // when
        App.connect(null, App.DATABASE_DRIVER, true); // no failure
    }

    // test that the connection is null if the database driver is invalid
    @Test
    public void App_connect_noFailureFromInvalidDriver()
    {
        // given
        String databaseDriver = "mysqf";

        // when
        Connection connection = App.connect("localhost:33061", databaseDriver, true); // no failure

        // then
        assertNull(connection);
    }

    // test that if the connection is null there is no error
    @Test
    public void App_disconnect_nullConnection()
    {
        //given
        Connection connection = null;

        //then
        App.disconnect(connection);
    }

    /**
     * Tests for the DAO class
     */
    // test the whereCondition is null when the areaFilter is unexpected
    @Test
    public void DAO_getWhereCondition_unknownAreaFilterReturnsNull()
    {
        // given
        String areaFilter = "planet";

        // when
        String whereCondition = DAO.getWhereCondition(areaFilter, "Earth");

        // then
        assertNull(whereCondition);
    }

    // test that the whereCondition contains our given area name
    @Test
    public void DAO_getWhereCondition_areaNameAppearsInWhereCondition()
    {
        // given
        String areaName = "New York";

        // when
        String whereCondition = DAO.getWhereCondition(App.DISTRICT, areaName);

        // then
        assertTrue(whereCondition.contains(areaName));
    }

    // test that the whereCondition is null when areaName is null
    @Test
    public void DAO_getWhereCondition_nullAreaNameReturnsNull()
    {
        // when
        String whereCondition = DAO.getWhereCondition(App.WORLD, null);

        // then
        assertNull(whereCondition);
    }

    // test that the whereCondition is null when areaFilter is null
    @Test
    public void DAO_getWhereCondition_nullAreaFilterReturnsNull()
    {
        // when
        String whereCondition = DAO.getWhereCondition(null, "Earth");

        // then
        assertNull(whereCondition);
    }

    // test that a null whereCondition will result in queryInvalid being true
    @Test
    public void DAO_queryInvalid_falseWhenWhereConditionIsNull()
    {
        // when
        boolean result = dao.queryInvalid("query",
                null,
                App.WORLD,
                new ArrayList<>(),
                1);

        assertTrue(result);
    }

    // test that an n value of less than 1 will result in queryInvalid being true
    @Test
    public void DAO_queryInvalid_falseWhenNLessThanOne()
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

    // test that an area filter not in the valid area filters will result in queryInvalid being true
    @Test
    public void DAO_queryInvalid_falseWhenAreaFilterNotAllowed()
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

    // test that queryInvalid is false when it is passed good values
    @Test
    public void DAO_queryInvalid_happyPath()
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

    // test that countries list is empty when areaFilter and areaName are null
    @Test
    public void DAO_allCountriesIn_bothArgumentsNullListEmpty()
    {
        // when
        ArrayList<Record> countries = dao.allCountriesIn(null, null);

        // then
        assertEquals(0, countries.size());
    }

    // test that countries list is empty when areaFilter is null
    @Test
    public void DAO_allCountriesIn_areaFilterNullListEmpty()
    {
        // given
        String areaFilter = null;

        // when
        ArrayList<Record> countries = dao.allCountriesIn(areaFilter, "Scotland");

        // then
        assertEquals(0, countries.size());
    }

    // test that countries list is empty when areaName is null
    @Test
    public void DAO_allCountriesIn_areaNameNullListEmpty()
    {
        // given
        String areaName = null;

        // when
        ArrayList<Record> countries = dao.allCountriesIn(App.COUNTRY, areaName);

        // then
        assertEquals(0, countries.size());
    }

    // check countries list is empty if called with an invalid areaFilter
    @Test
    public void DAO_allCountriesIn_areaFilterWrongListEmpty()
    {
        // given
        String areaFilter = App.CITY;

        // when
        ArrayList<Record> countries = dao.allCountriesIn(areaFilter, "Glasgow");

        // then
        assertEquals(0, countries.size());
    }

    // check test passes with valid areaFilter and areaName
    @Test
    public void DAO_allCountriesIn_happyPath()
    {
        // given
        String areaFilter = App.COUNTRY;
        String areaName = "France";

        // then
        dao.allCountriesIn(areaFilter, areaName); // No Error
    }

    // test that countries list is empty when areaFilter and areaName are null
    @Test
    public void DAO_topNCountriesIn_bothArgumentsNullListEmpty()
    {
        // given
        String areaFilter = null;
        String areaName = null;

        // when
        ArrayList<Record> countries = dao.topNCountriesIn(areaFilter, areaName, 5);

        // then
        assertEquals(0, countries.size());
    }

    // test that countries list is empty when areaFilter is null
    @Test
    public void DAO_topNCountriesIn_areaFilterNullListEmpty()
    {
        // given
        String areaFilter = null;

        // when
        ArrayList<Record> countries = dao.topNCountriesIn(areaFilter, "Scotland", 10);

        // then
        assertEquals(0, countries.size());
    }

    // test that countries list is empty when areaName is null
    @Test
    public void DAO_topNCountriesIn_areaNameNullListEmpty()
    {
        // given
        String areaName = null;

        // when
        ArrayList<Record> countries = dao.topNCountriesIn(App.COUNTRY, areaName, 3);

        // then
        assertEquals(0, countries.size());
    }

    // test that n cannot be less than or equal to 0
    @Test
    public void DAO_topNCountriesIn_negativeNListEmpty()
    {
        // given
        Integer n = -3;

        // when
        ArrayList<Record> countries = dao.topNCountriesIn(App.COUNTRY, "China", n);

        // then
        assertEquals(0, countries.size());
    }

    // check countries list is empty if called with an invalid areaFilter
    @Test
    public void DAO_topNCountriesIn_areaFilterWrongListEmpty() {
        // given
        String areaFilter = App.DISTRICT;

        // when
        ArrayList<Record> countries = dao.topNCountriesIn(areaFilter, "Scotland", 15);

        // then
        assertEquals(0, countries.size());
    }

    // check test passes with valid areaFilter and areaName
    @Test
    public void DAO_topNCountriesIn_happyPath()
    {
        // given
        String areaFilter = App.COUNTRY;
        String areaName = "France";

        // then
        dao.topNCountriesIn(areaFilter, areaName, 15); // No Error
    }

    /**
     * Unit tests covering the DAO.allCitiesIn method
     */
    // test that cities list is empty when areaFilter and areaName are null
    @Test
    public void DAO_allCitiesIn_bothArgumentsNullListEmpty()
    {
        // given
        String areaFilter = null;
        String areaName = null;

        // when
        ArrayList<Record> cities = dao.allCitiesIn(areaFilter, areaName);

        // then
        assertEquals(0, cities.size());
    }

    // test that cities list is empty when areaFilter is null
    @Test
    public void DAO_allCitiesIn_areaFilterNullListEmpty()
    {
        // given
        String areaFilter = null;

        // when
        ArrayList<Record> cities = dao.allCitiesIn(areaFilter, "Scotland");

        // then
        assertEquals(0, cities.size());
    }

    // test that cities list is empty when areaName is null
    @Test
    public void DAO_allCitiesIn_areaNameNullListEmpty()
    {
        // given
        String areaName = null;

        // when
        ArrayList<Record> cities = dao.allCitiesIn(App.COUNTRY, areaName);

        // then
        assertEquals(0, cities.size());
    }

    // check cities list is empty if called with an invalid areaFilter
    @Test
    public void DAO_allCitiesIn_areaFilterWrongListEmpty()
    {
        // given
        String areaFilter = App.CITY;

        // when
        ArrayList<Record> cities = dao.allCitiesIn(areaFilter, "Glasgow");

        // then
        assertEquals(0, cities.size());
    }

    // check test passes with valid areaFilter and areaName
    @Test
    public void DAO_allCitiesIn_happyPath()
    {
        // given
        String areaFilter = App.COUNTRY;
        String areaName = "France";

        // then
        dao.allCitiesIn(areaFilter, areaName); // No Error
    }

    // test that cities list is empty when areaFilter and areaName are null
    @Test
    public void DAO_topNCitiesIn_bothArgumentsNullListEmpty()
    {
        // given
        String areaFilter = null;
        String areaName = null;

        // when
        ArrayList<Record> cities = dao.topNCitiesIn(areaFilter, areaName, 5);

        // then
        assertEquals(0, cities.size());
    }

    // test that cities list is empty when areaFilter is null
    @Test
    public void DAO_topNCitiesIn_areaFilterNullListEmpty()
    {
        // given
        String areaFilter = null;

        // when
        ArrayList<Record> cities = dao.topNCitiesIn(areaFilter, "Europe", 10);

        // then
        assertEquals(0, cities.size());
    }

    // test that cities list is empty when areaName is null
    @Test
    public void DAO_topNCitiesIn_areaNameNullListEmpty()
    {
        // given
        String areaName = null;

        // when
        ArrayList<Record> cities = dao.topNCitiesIn(App.COUNTRY, areaName, 3);

        // then
        assertEquals(0, cities.size());
    }

    // test that n cannot be less than or equal to 0
    @Test
    public void DAO_topNCitiesIn_negativeNListEmpty()
    {
        // given
        Integer n = -3;

        // when
        ArrayList<Record> cities = dao.topNCitiesIn(App.COUNTRY, "China", n);

        // then
        assertEquals(0, cities.size());
    }

    // check cities list is empty if called with an invalid areaFilter
    @Test
    public void DAO_topNCitiesIn_areaFilterWrongListEmpty() {
        // given
        String areaFilter = App.CITY;

        // when
        ArrayList<Record> cities = dao.topNCitiesIn(areaFilter, "Houston", 15);

        // then
        assertEquals(0, cities.size());
    }

    // check test passes with valid areaFilter and areaName
    @Test
    public void DAO_topNCitiesIn_happyPath()
    {
        // given
        String areaFilter = App.WORLD;
        String areaName = "";

        // then
        dao.topNCitiesIn(areaFilter, areaName, 15); // No Error
    }

    // test that capitalCities list is empty when areaFilter and areaName are null
    @Test
    public void DAO_allCapitalCitiesIn_bothArgumentsNullListEmpty()
    {
        // given
        String areaFilter = null;
        String areaName = null;

        // when
        ArrayList<Record> capitalCities = dao.allCapitalCitiesIn(areaFilter, areaName);

        // then
        assertEquals(0, capitalCities.size());
    }

    // test that capitalCities list is empty when areaFilter is null
    @Test
    public void DAO_allCapitalCitiesIn_areaFilterNullListEmpty()
    {
        // given
        String areaFilter = null;

        // when
        ArrayList<Record> capitalCities = dao.allCapitalCitiesIn(areaFilter, "Scotland");

        // then
        assertEquals(0, capitalCities.size());
    }

    // test that capitalCities list is empty when areaName is null
    @Test
    public void DAO_allCapitalCitiesIn_areaNameNullListEmpty()
    {
        // given
        String areaName = null;

        // when
        ArrayList<Record> capitalCities = dao.allCapitalCitiesIn(App.COUNTRY, areaName);

        // then
        assertEquals(0, capitalCities.size());
    }

    // check capital cities list is empty if called with an invalid areaFilter
    @Test
    public void DAO_allCapitalCitiesIn_areaFilterWrongListEmpty()
    {
        // given
        String areaFilter = App.DISTRICT;

        // when
        ArrayList<Record> capitalCities = dao.allCapitalCitiesIn(areaFilter, "Glasgow");

        // then
        assertEquals(0, capitalCities.size());
    }

    // check test passes with valid areaFilter and areaName
    @Test
    public void DAO_allCapitalCitiesIn_happyPath()
    {
        // given
        String areaFilter = App.COUNTRY;
        String areaName = "France";

        // then
        dao.allCapitalCitiesIn(areaFilter, areaName); // No Error
    }

    // test that capitalCities list is empty when areaFilter and areaName are null
    @Test
    public void topNCapitalCitiesIn_bothArgumentsNullListEmpty()
    {
        // given
        String areaFilter = null;
        String areaName = null;

        // when
        ArrayList<Record> capitalCities = dao.topNCapitalCitiesIn(areaFilter, areaName, 5);

        // then
        assertEquals(0, capitalCities.size());
    }

    // test that capitalCities list is empty when areaFilter is null
    @Test
    public void DAO_topNCapitalCitiesIn_areaFilterNullListEmpty()
    {
        // given
        String areaFilter = null;

        // when
        ArrayList<Record> capitalCities = dao.topNCapitalCitiesIn(areaFilter, "Europe", 10);

        // then
        assertEquals(0, capitalCities.size());
    }

    // test that capitalCities list is empty when areaName is null
    @Test
    public void DAO_topNCapitalCitiesIn_areaNameNullListEmpty()
    {
        // given
        String areaName = null;

        // when
        ArrayList<Record> capitalCities = dao.topNCapitalCitiesIn(App.COUNTRY, areaName, 3);

        // then
        assertEquals(0, capitalCities.size());
    }

    // test that n cannot be less than or equal to 0
    @Test
    public void DAO_topNCapitalCitiesIn_negativeNListEmpty()
    {
        // given
        Integer n = -3;

        // when
        ArrayList<Record> capitalCities = dao.topNCapitalCitiesIn(App.COUNTRY, "China", n);

        // then
        assertEquals(0, capitalCities.size());
    }

    // check capital cities list is empty if called with an invalid areaFilter
    @Test
    public void DAO_topNCapitalCitiesIn_areaFilterWrongListEmpty() {
        // given
        String areaFilter = App.CITY;

        // when
        ArrayList<Record> capitalCities = dao.topNCapitalCitiesIn(areaFilter, "Canberra", 15);

        // then
        assertEquals(0, capitalCities.size());
    }

    // check test passes with valid areaFilter and areaName
    @Test
    public void DAO_topNCapitalCitiesIn_happyPath()
    {
        // given
        String areaFilter = App.REGION;
        String areaName = "Nordic Countries";

        // then
        dao.topNCapitalCitiesIn(areaFilter, areaName, 15); // No Error
    }

    // test that residenceReport list is empty when areaFilter and areaName are null
    @Test
    public void DAO_populationLivingInAndNotInCities_bothArgumentsNullListEmpty()
    {
        // given
        String areaFilter = null;
        String areaName = null;

        // when
        ArrayList<Record> residenceReport = dao.populationLivingInAndNotInCities(areaFilter, areaName);

        // then
        assertEquals(0, residenceReport.size());
    }

    // test that residenceReport list is empty when areaFilter is null
    @Test
    public void DAO_populationLivingInAndNotInCities_areaFilterNullListEmpty()
    {
        // given
        String areaFilter = null;

        // when
        ArrayList<Record> residenceReport = dao.populationLivingInAndNotInCities(areaFilter, "Scotland");

        // then
        assertEquals(0, residenceReport.size());
    }

    // test that residenceReport list is empty when areaName is null
    @Test
    public void DAO_populationLivingInAndNotInCities_areaNameNullListEmpty()
    {
        // given
        String areaName = null;

        // when
        ArrayList<Record> residenceReport = dao.populationLivingInAndNotInCities(App.COUNTRY, areaName);

        // then
        assertEquals(0, residenceReport.size());
    }

    // check cities list is empty if called with an invalid areaFilter
    @Test
    public void DAO_populationLivingInAndNotInCities_areaFilterWrongListEmpty() {
        // given
        String areaFilter = App.WORLD;

        // when
        ArrayList<Record> cities = dao.populationLivingInAndNotInCities(areaFilter, "");

        // then
        assertEquals(0, cities.size());
    }

    // check test passes with valid areaFilter and areaName
    @Test
    public void DAO_populationLivingInAndNotInCities_happyPath()
    {
        // given
        String areaFilter = App.CONTINENT;
        String areaName = "Asia";

        // then
        dao.populationLivingInAndNotInCities(areaFilter, areaName); // No Error
    }

    /**
     * Unit tests covering the DAO.populationOf method
     */
    // test that population list is empty when areaFilter and areaName are null
    @Test
    public void DAO_populationOf_bothArgumentsNullListEmpty()
    {
        // given
        String areaFilter = null;
        String areaName = null;

        // when
        ArrayList<Record> population = dao.populationOf(areaFilter, areaName);

        // then
        assertEquals(0, population.size());
    }

    // test that population list is empty when areaFilter is null
    @Test
    public void DAO_populationOf_areaFilterNullListEmpty()
    {
        // given
        String areaFilter = null;

        // when
        ArrayList<Record> population = dao.populationOf(areaFilter, "Scotland");

        // then
        assertEquals(0, population.size());
    }

    // test that population list is empty when areaName is null
    @Test
    public void DAO_populationOf_areaNameNullListEmpty()
    {
        // given
        String areaName = null;

        // when
        ArrayList<Record> population = dao.populationOf(App.COUNTRY, areaName);

        // then
        assertEquals(0, population.size());
    }

    // check test passes with valid areaFilter and areaName
    @Test
    public void DAO_populationOf_happyPath()
    {
        // given
        String areaFilter = App.CITY;
        String areaName = "Glasgow";

        // then
        dao.populationOf(areaFilter, areaName); // No Error
    }

    // check no error is thrown when method is called with a null connection
    @Test
    public void DAO_languageReport_happyPath()
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
    public void Record_testGettersAndToStringForCountry()
    {
        // given
        String countryCode = "SCT";
        String name = "Scotland";
        String continent = "Europe";
        String region = "Great Britain";
        String capital = "Edinburgh";
        long population = 6000000L;
        String expectedRecordType = App.COUNTRY;
        String expectedToStringOutput = App.HORIZONTAL_LINE + "\n" +
                "Country code: SCT | Name: Scotland | Continent: Europe\n" +
                "Region: Great Britain | Population: 6,000,000 | Capital: Edinburgh";


        // when
        Record record = new Record(countryCode, name, continent, region, population, capital);

        // then
        assertEquals(countryCode, record.getCountryCode());
        assertEquals(name, record.getName());
        assertEquals(continent, record.getContinent());
        assertEquals(region, record.getRegion());
        assertEquals(population, record.getPopulation());
        assertEquals(capital, record.getCapital());
        assertEquals(expectedRecordType, record.getRecordType());
        assertEquals(expectedToStringOutput, record.toString());
    }

    // Tests that when a capital city object is constructed, the getters and toString
    // return the same fields that we passed in.
    @Test
    public void Record_testGettersAndToStringForCapitalCity()
    {
        // given
        String name = "Glasgow";
        String country = "Scotland";
        String region = "Great Britain";
        String continent = "Europe";
        long population = 6000000L;
        String expectedRecordType = App.CAPITAL_CITY;
        String expectedToStringOutput = App.HORIZONTAL_LINE + "\n" +
                "Name: Glasgow | Country: Scotland | Population: 6,000,000";

        // when
        Record capitalCity = new Record(name, country, region, continent, population);

        // then
        assertEquals(name, capitalCity.getName());
        assertEquals(country, capitalCity.getCountry());
        assertEquals(region, capitalCity.getRegion());
        assertEquals(continent, capitalCity.getContinent());
        assertEquals(population, capitalCity.getPopulation());
        assertEquals(expectedRecordType, capitalCity.getRecordType());
        assertEquals(expectedToStringOutput, capitalCity.toString());
    }

    // Tests that when a city object is constructed, the getters and toString
    // return the same fields that we passed in.
    @Test
    public void Record_testGettersAndToStringForCity()
    {
        // given
        String name = "Wishae";
        String country = "Scotland";
        String district = "North Lanarkshire";
        long population = 10000L;
        String expectedRecordType = App.CITY;
        String expectedToStringOutput = App.HORIZONTAL_LINE + "\n" +
                "Name: Wishae |  Country: Scotland \n" +
                "District: North Lanarkshire |  Population: 10,000";

        // when
        Record city = new Record(name, country, district, population);

        // then
        assertEquals(name, city.getName());
        assertEquals(country, city.getCountry());
        assertEquals(district, city.getDistrict());
        assertEquals(population, city.getPopulation());
        assertEquals(expectedRecordType, city.getRecordType());
        assertEquals(expectedToStringOutput, city.toString());
    }

    // Tests that when a population residence report object is constructed, the getters and toString
    // return the same fields that we passed in.
    @Test
    public void Record_testGettersAndToStringForPopulationResidenceReport()
    {
        // given
        String name = "East Kilbride";
        Long population = 7000L;
        Long populationLivingInCities = 6999L;
        Double percentageLivingInCities = 99.99D;
        Long populationNotLivingInCities = 1L;
        Double percentageNotLivingInCities = 0.01D;
        String expectedRecordType = App.RESIDENCE_REPORT;
        String expectedToStringOutput = App.HORIZONTAL_LINE + "\n" +
                "Area: East Kilbride | Total population: 7,000\n" +
                "Population living in Cities: 6,999 (99.99%)\n" +
                "Population not living in Cities: 1 (0.01%)";

        // when
        Record residenceReport = new Record(name, population, populationLivingInCities, percentageLivingInCities, populationNotLivingInCities, percentageNotLivingInCities);

        // then
        assertEquals(name, residenceReport.getName());
        assertEquals(population, residenceReport.getPopulation());
        assertEquals(populationLivingInCities, residenceReport.getPopulationLivingInCities());
        assertEquals(percentageLivingInCities, residenceReport.getPercentageLivingInCities());
        assertEquals(populationNotLivingInCities, residenceReport.getPopulationNotLivingInCities());
        assertEquals(percentageNotLivingInCities, residenceReport.getPercentageNotLivingInCities());
        assertEquals(expectedRecordType, residenceReport.getRecordType());
        assertEquals(expectedToStringOutput, residenceReport.toString());
    }

    // Tests that when a language object is constructed, the getters and toString
    // return the same fields that we passed in.
    @Test
    public void Record_testGettersAndToStringForLanguage()
    {
        // given
        String language = "Gaelic";
        Long speakers = 3L;
        Integer percentage = 0;
        String expectedRecordType = App.LANGUAGE;
        String expectedToStringOutput = App.HORIZONTAL_LINE + "\n" +
                "Language: Gaelic |  Speakers: 3 | % of world's population: 0%";

        // when
        Record Language = new Record(language, speakers, percentage);

        // then
        assertEquals(language, Language.getName());
        assertEquals(speakers, Language.getSpeakers());
        assertEquals(percentage, Language.getPercentage());
        assertEquals(expectedRecordType, Language.getRecordType());
        assertEquals(expectedToStringOutput, Language.toString());
    }

    // Tests that when a population object is constructed, the getters and toString
    // return the same fields that we passed in.
    @Test
    public void Record_testGettersAndToStringForPopulation()
    {
        // given
        String name = "China";
        Long population = 999000000L;
        String expectedRecordType = App.POPULATION;
        String expectedToStringOutput = App.HORIZONTAL_LINE + "\n" +
                "Area: China |  Population: 999,000,000";

        // when
        Record Population = new Record(name, population);

        // then
        assertEquals(name, Population.getName());
        assertEquals(population, Population.getPopulation());
        assertEquals(expectedRecordType, Population.getRecordType());
        assertEquals(expectedToStringOutput, Population.toString());
    }

    /**
     * Tests for the QueryInfo object
     */
    // Tests that when a QueryInfo object is constructed, the getters return the same fields that we passed in.
    @Test
    public void QueryInfo_testGettersForQueryInfo()
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
    public void UserPrompt_obtainAreaFilterChoice_queryIdNineResultsInEmptyString()
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
    public void UserPrompt_formatInput_removesWhiteSpaceAndCapitals()
    {
        // given
        String input = "   AbCd \t";
        String expectedOutput = "abcd";

        //when
        String output = userPrompt.formatInput(input);

        //then
        assertEquals(expectedOutput, output);
    }

    @Test
    public void UserPrompt_formatInput_noFailureWhenNullEntered()
    {
        // when
        String formattedInput = userPrompt.formatInput(null); // no failure

        // then
        assertNull(formattedInput); // no failure
    }

    // Test that parseQueryInputForAreaFilter returns null on receiving invalid input
    @Test
    public void UserPrompt_parseQueryInputForAreaFilter_invalidParametersReturnNull()
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
    public void UserPrompt_parseQueryInputForAreaFilter_handlesQuerySevenCorrectly()
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
    public void UserPrompt_parseQueryInputForAreaFilter_happyPaths()
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
    public void UserPrompt_executeQueryFromInput_invalidEntryResultsInEmptyList()
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
    public void UserPrompt_executeQueryFromInput_returnsNullWhenPassedInvalidQueryId()
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
    public void UserPrompt_executeQueryFromInput_happyPaths()
    {
        // given
        String areaFilter = App.CONTINENT;
        String areaName = "Asia";
        int n = 10;

        // when
        for (int queryId = 1; queryId < 10; queryId ++)
        {
            userPrompt.executeQueryFromInput(queryId, areaFilter, areaName, n); // no failures
        }
    }

    // Test that show records throws no errors when passed null, an empty list or a list with entries
    @Test
    public void UserPrompt_showRecords_noFailuresOnAllInputs()
    {
        // given
        ArrayList<Record> records1 = new ArrayList<>();
        ArrayList<Record> records2 = new ArrayList<>(Arrays.asList(
                new Record("English", 800000000, 15),
                new Record("Chinese", 1000000000, 50)));

        // when
        userPrompt.showRecords(null);
        userPrompt.showRecords(records1);
        userPrompt.showRecords(records2); // no failures
    }
}
