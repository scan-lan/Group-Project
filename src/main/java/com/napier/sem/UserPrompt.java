package com.napier.sem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class UserPrompt
{
    private final DAO dao;
    private final Scanner scanner;
    private static final int[] topNQueryParentIds = new int[]{2, 4, 6};
    private static final String USE_CASE_PROMPT = "Enter the number corresponding to the type of query you'd like to run\n" +
            "Your options are as follows:";

    public UserPrompt(DAO dao)
    {
        this.dao = dao;
        this.scanner = new Scanner(System.in);
    }

    public void enterLoop()
    {
        System.out.println(USE_CASE_PROMPT);

        int parentQueryChoice = scanner.nextInt();

        System.out.println("Choose the area you'd like to restrict the query to: 1, 2 or 3");

        int childQueryChoice = scanner.nextInt();

        String areaFilter = parseQueryInputForAreaFilter(parentQueryChoice, childQueryChoice);

        String areaNameInput = "";
        if (areaFilter != App.WORLD)
        {
            System.out.println("Enter the area you'd like to query");

            areaNameInput = scanner.next();
        }

        int n = -1;
        if (Arrays.binarySearch(topNQueryParentIds, parentQueryChoice) >= 0)
        {
            System.out.println("Enter the number of records you'd like to see");

            n = scanner.nextInt();
        }

        executeQueryFromInput(parentQueryChoice, areaFilter, areaNameInput, n);
    }

    private String parseQueryInputForAreaFilter(int parentQueryChoice, int areaFilterChoice)
    {
        if (parentQueryChoice == 7) areaFilterChoice++;

        switch (areaFilterChoice)
        {
            case (1):
                return App.WORLD;
            case (2):
                return App.CONTINENT;
            case (3):
                return App.REGION;
            case (4):
                return App.COUNTRY;
            case (5):
                return App.DISTRICT;
            case (6):
                return App.CITY;
            default:
                return null;
        }
    }

    private void executeQueryFromInput(int queryId, String areaFilter, String areaName, int n)
    {
        ArrayList<Record> records;

        switch (queryId)
        {
            case (1):
                records = dao.allCountriesIn(areaFilter, areaName);
                break;
            case (2):
                records = dao.topNCountriesIn(areaFilter, areaName, n);
                break;
            default:
                records = new ArrayList<>();
                break;
        }

        for (Record record: records)
        {
            System.out.println(record);
        }
    }
}

//class ParentQuery
//{
//    private final int parentID;
//    private final String description;
//    private final String[] childQueries;
//
//    public ParentQuery(int id, String description, String[] childQueries)
//    {
//        parentID = id;
//        this.description = description;
//        this.childQueries = childQueries;
//    }
//}