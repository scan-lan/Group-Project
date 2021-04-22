package com.napier.sem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserPrompt_UnitTests
{

    /**
     * Tests for the QueryInfo object
     */
    // Tests that when a QueryInfo object is constructed, the getters and toString
    // return the same fields that we passed in.
    @Test
    public void Record_testGettersAndToStringForQueryInfo()
    {
        // given
        String queryDescription = "All countries in";
        String[] areaFilterDescriptions = {"the world", "a given continent", "a given region"};

        // when
        Record record = new Record(countryCode, name, continent, region, population, capital);

        // then
        assertEquals(countryCode, record.getCountryCode());

    }

}
