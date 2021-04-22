package com.napier.sem;

import org.junit.jupiter.api.Test;

import javax.management.Query;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserPrompt_UnitTests
{
    /**
     * Tests for the UserPrompt class
     */
    // Tests that a UserPrompt object can be successfully created
    @Test
    public void UserPrompt_testConstructor()
    {
        // when
        DAO dao = new DAO(null);

        //then
        UserPrompt userPrompt = new UserPrompt(dao);
    }

    // Tests that obtainAreaFilterChoice outputs nothing when 9 is entered
    @Test
    public void UserPrompt_ObtainAreaFilterChoiceHappyPath9()
    {
        // given
        DAO dao = new DAO(null);
        UserPrompt userPrompt = new UserPrompt(dao);
        int queryID = 9;
        String expectedOutput = "";

        //when
        String output = userPrompt.obtainAreaFilterChoice(queryID);

        //then
        assertEquals(expectedOutput,output);
    }

    // Tests that formatInput successfully removes spaces and capital letters
    @Test
    public void UserPrompt_formatInput()
    {
        // given
        DAO dao = new DAO(null);
        UserPrompt userPrompt = new UserPrompt(dao);
        String input = "   AbCd ";
        String expectedOutput = "abcd";

        //when
        String output = userPrompt.formatInput(input);

        //then
        assertEquals(expectedOutput,output);
    }

    // Tests that parseQueryInputForAreaFilter returns the expected output
    @Test
    public void UserPrompt_parseQueryInputForAreaFilter()
    {
        // given
        DAO dao = new DAO(null);
        UserPrompt userPrompt = new UserPrompt(dao);
        int queryId = 1;
        String[] expectedOutputs = {"", null, App.WORLD, App.CONTINENT, App.REGION, App.COUNTRY, App.DISTRICT, App.CITY};

        for (int i = -1 ; i < 7 ; i++)
        {
            //when
            String output = userPrompt.parseQueryInputForAreaFilter(queryId, i);

            //then
            assertEquals(expectedOutputs[i+1],output);
        }
    }

    // Tests that executeQueryFromInput outputs an error message when invalid data is input
    @Test
    public void UserPrompt_executeQueryFromInputInvalidEntry()
    {
        // given
        DAO dao = new DAO(null);
        UserPrompt userPrompt = new UserPrompt(dao);
        String areaFilter = "Continent";
        String areaName = "Europe";
        int n = 5;

        for (int i = -1 ; i < 10 ; i++)
        {
            //then
            userPrompt.executeQueryFromInput(i, areaFilter, areaName, n);
        }
    }

    /**
     * Tests for the QueryInfo object
     */
    // Tests that when a QueryInfo object is constructed, the getters and toString
    // return the same fields that we passed in.
    @Test
    public void QueryInfo_testGettersAndToStringForQueryInfo()
    {
        // given
        String queryDescription = "All countries in";
        String[] areaFilterDescriptions = {"the world", "a given continent", "a given region"};

        // when
        QueryInfo queryInfo = new QueryInfo(queryDescription, areaFilterDescriptions);

        // then
        assertEquals(queryDescription, queryInfo.getQueryDescription());
        assertEquals(areaFilterDescriptions,queryInfo.getAreaFilterDescriptions());

    }

}
