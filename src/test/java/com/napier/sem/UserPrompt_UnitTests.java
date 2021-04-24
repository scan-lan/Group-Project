package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class UserPrompt_UnitTests
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
     * Tests for the UserPrompt class
     */
    // Tests that obtainAreaFilterChoice outputs an empty string when 9 is entered
    @Test
    public void obtainAreaFilterChoice_queryIdNineResultsInEmptyString()
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
    public void formatInput_removesWhiteSpaceAndCapitals()
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
    public void formatInput_noFailureWhenNullEntered()
    {
        // when
        String formattedInput = userPrompt.formatInput(null); // no failure

        // then
        assertNull(formattedInput);
    }

    // Test that parseQueryInputForAreaFilter returns null on receiving invalid input
    @Test
    public void parseQueryInputForAreaFilter_invalidParametersReturnNull()
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

    // Test that a query choice of 1 results in an area filter of world for all query ids except 7, which cannot
    // have world as a query filter
    @Test
    public void parseQueryInputForAreaFilter_handlesQuerySevenCorrectly()
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
    public void parseQueryInputForAreaFilter_happyPaths()
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
    public void executeQueryFromInput_invalidEntryResultsInEmptyList()
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

    @Test
    public void executeQueryFromInput_returnsNullWhenPassedInvalidQueryId()
    {
        // given
        int queryId = -10;

        // when
        ArrayList<Record> records = userPrompt.executeQueryFromInput(queryId, App.CONTINENT, "Europe", 10);

        // then
        assertNull(records);
    }

    // given
    @Test
    public void executeQueryFromInput_happyPaths()
    {
        // given
        String areaFilter = App.CONTINENT;
        String areaName = "Asia";
        int n = 10;

        // when
        for (int queryId = 1; queryId < 10; queryId ++)
        {
            ArrayList<Record> records = userPrompt.executeQueryFromInput(queryId, areaFilter, areaName, n);

            // then
            assertNotNull(records);
        }
    }

    // Test that show records throws no errors when passed null, an empty list or a list with entries
    @Test
    public void showRecords_noFailuresOnAllInputs()
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
}
