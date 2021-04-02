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

    //test that the whereCondition is null when areaName is null
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

    //test that the whereCondition is null when areaFilter is null
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

    @Test
    public void allCountriesIn_areaNameNullListEmpty()
    {
        // given
        String areaFilter = null;

        // when
        ArrayList<Record> countries = dao.allCountriesIn(App.COUNTRY, areaFilter);

        // then
        assertEquals(0, countries.size());
    }

    @Test
    public void allCountriesIn_happyPath()
    {
        // given
        String areaFilter = App.COUNTRY;
        String areaName = "France";

        // then
        ArrayList<Record> countries = dao.allCountriesIn(areaFilter, areaName); // No Error
    }
}
