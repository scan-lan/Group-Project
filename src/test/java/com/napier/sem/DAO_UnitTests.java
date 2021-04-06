package com.napier.sem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DAO_UnitTests
{
    static DAO dao;

    @BeforeAll
    static void init()
    {
        dao = new DAO(null);
    }

    /**
     * Unit tests covering the DAO.getWhereCondition method
     */
    // test the whereCondition is null when the areaFilter is unexpected
    @Test
    public void getWhereCondition_unknownAreaFilterReturnsNull()
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
    public void getWhereCondition_areaNameAppearsInWhereCondition()
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
    public void getWhereCondition_nullAreaNameReturnsNull()
    {
        // given
        String areaName = null;

        // when
        String whereCondition = DAO.getWhereCondition(App.WORLD, areaName);

        // then
        assertNull(whereCondition);
    }

    // test that the whereCondition is null when areaFilter is null
    @Test
    public void getWhereCondition_nullAreaFilterReturnsNull()
    {
        // given
        String areaFilter = null;

        // when
        String whereCondition = DAO.getWhereCondition(areaFilter, "Earth");

        // then
        assertNull(whereCondition);
    }

    /**
     * Unit tests covering the DAO.allCountriesIn method
     */
    // test that countries list is empty when areaFilter and areaName are null
    @Test
    public void allCountriesIn_bothArgumentsNullListEmpty()
    {
        // given
        String areaFilter = null;
        String areaName = null;

        // when
        ArrayList<Record> countries = dao.allCountriesIn(areaFilter, areaName);

        // then
        assertEquals(0, countries.size());
    }

    // test that countries list is empty when areaFilter is null
    @Test
    public void allCountriesIn_areaFilterNullListEmpty()
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
    public void allCountriesIn_areaNameNullListEmpty()
    {
        // given
        String areaName = null;

        // when
        ArrayList<Record> countries = dao.allCountriesIn(App.COUNTRY, areaName);

        // then
        assertEquals(0, countries.size());
    }

    // check test passes with valid areaFilter and areaName
    @Test
    public void allCountriesIn_happyPath()
    {
        // given
        String areaFilter = App.COUNTRY;
        String areaName = "France";

        // then
        dao.allCountriesIn(areaFilter, areaName); // No Error
    }

    /**
     * Unit tests covering the DAO.topNCountriesIn method
     */
    // test that countries list is empty when areaFilter and areaName are null
    @Test
    public void topNCountriesIn_bothArgumentsNullListEmpty()
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
    public void topNCountriesIn_areaFilterNullListEmpty()
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
    public void topNCountriesIn_areaNameNullListEmpty()
    {
        // given
        String areaName = null;

        // when
        ArrayList<Record> countries = dao.topNCountriesIn(App.COUNTRY, areaName, 3);

        // then
        assertEquals(0, countries.size());
    }

    // test that n is greater than or equal to 0
    @Test
    public void topNCountriesIn_negativeNListEmpty()
    {
        // given
        Integer n = -3;

        // when
        ArrayList<Record> countries = dao.topNCountriesIn(App.COUNTRY, "China", n);

        // then
        assertEquals(0, countries.size());
    }

    // check test passes with valid areaFilter and areaName
    @Test
    public void topNCountriesIn_happyPath()
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
    public void allCitiesIn_bothArgumentsNullListEmpty()
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
    public void allCitiesIn_areaFilterNullListEmpty()
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
    public void allCitiesIn_areaNameNullListEmpty()
    {
        // given
        String areaName = null;

        // when
        ArrayList<Record> cities = dao.allCitiesIn(App.COUNTRY, areaName);

        // then
        assertEquals(0, cities.size());
    }

    // check test passes with valid areaFilter and areaName
    @Test
    public void allCitiesIn_happyPath()
    {
        // given
        String areaFilter = App.COUNTRY;
        String areaName = "France";

        // then
        dao.allCitiesIn(areaFilter, areaName); // No Error
    }

    /**
     * Unit tests covering the DAO.topNCitiesIn method
     */
    // test that cities list is empty when areaFilter and areaName are null
    @Test
    public void topNCitiesIn_bothArgumentsNullListEmpty()
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
    public void topNCitiesIn_areaFilterNullListEmpty()
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
    public void topNCitiesIn_areaNameNullListEmpty()
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
    public void topNCitiesIn_negativeNListEmpty()
    {
        // given
        Integer n = -3;

        // when
        ArrayList<Record> cities = dao.topNCitiesIn(App.COUNTRY, "China", n);

        // then
        assertEquals(0, cities.size());
    }

    // check test passes with valid areaFilter and areaName
    @Test
    public void topNCitiesIn_happyPath()
    {
        // given
        String areaFilter = App.COUNTRY;
        String areaName = "France";

        // then
        dao.topNCitiesIn(areaFilter, areaName, 15); // No Error
    }

    /**
     * Unit tests covering the DAO.allCapitalCitiesIn method
     */
    // test that captialCities list is empty when areaFilter and areaName are null
    @Test
    public void allCapitalCitiesIn_bothArgumentsNullListEmpty()
    {
        // given
        String areaFilter = null;
        String areaName = null;

        // when
        ArrayList<Record> capitalCities = dao.allCapitalCitiesIn(areaFilter, areaName);

        // then
        assertEquals(0,capitalCities.size());
    }

    // test that capitalCities list is empty when areaFilter is null
    @Test
    public void allCapitalCitiesIn_areaFilterNullListEmpty()
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
    public void allCapitalCitiesIn_areaNameNullListEmpty()
    {
        // given
        String areaName = null;

        // when
        ArrayList<Record> capitalCities = dao.allCapitalCitiesIn(App.COUNTRY, areaName);

        // then
        assertEquals(0, capitalCities.size());
    }

    // check test passes with valid areaFilter and areaName
    @Test
    public void allCapitalCitiesIn_happyPath()
    {
        // given
        String areaFilter = App.COUNTRY;
        String areaName = "France";

        // then
        dao.allCitiesIn(areaFilter, areaName); // No Error
    }

    /**
     * Unit tests covering the DAO.topNCapitalCitiesIn method
     */
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
    public void topNCapitalCitiesIn_areaFilterNullListEmpty()
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
    public void topNCapitalCitiesIn_areaNameNullListEmpty()
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
    public void topNCapitalCitiesIn_negativeNListEmpty()
    {
        // given
        Integer n = -3;

        // when
        ArrayList<Record> capitalCities = dao.topNCapitalCitiesIn(App.COUNTRY, "China", n);

        // then
        assertEquals(0, capitalCities.size());
    }

    // check test passes with valid areaFilter and areaName
    @Test
    public void topNCapitalCitiesIn_happyPath()
    {
        // given
        String areaFilter = App.COUNTRY;
        String areaName = "France";

        // then
        dao.topNCapitalCitiesIn(areaFilter, areaName, 15); // No Error
    }

}
