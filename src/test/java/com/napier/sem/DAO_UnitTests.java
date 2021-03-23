package com.napier.sem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DAO_UnitTests
{
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
}
