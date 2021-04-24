package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UserPrompt_IntegrationTests
{
    static DAO dao;
    static UserPrompt userPrompt;
    static Connection connection;

    @BeforeAll
    static void init()
    {
        connection = App.connect("localhost:33061", App.DATABASE_DRIVER, false);
        dao = new DAO(connection);
        userPrompt = new UserPrompt(dao);
    }

    @AfterAll
    static void tearDown() { App.disconnect(connection); }

//    @Test
//    public void executeQueryFromInput_allQueriesRunSuccessfully()
//    {
//        // given
//        String areaFilter = App.CONTINENT;
//        String areaName = "Europe";
//        int n = 5;
//
//        for (int queryId = 1; queryId < 10; queryId++)
//        {
//            // when
//            ArrayList<Record> records = userPrompt.executeQueryFromInput(queryId, areaFilter, areaName, n);
//
//            // then
//            assertNotEquals(0, records.size());
//        }
//    }
}
