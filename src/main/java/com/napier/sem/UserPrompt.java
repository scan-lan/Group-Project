package com.napier.sem;

import java.util.*;

public class UserPrompt
{
    private final DAO dao;
    private final Scanner scanner;
    private static final int[] topNQueryParentIds = new int[]{2, 4, 6};
    private static final String USE_CASE_PROMPT = "Enter the number corresponding to the type of query you'd like to run\n" +
            "Your options are as follows:\n" +
            "1) All countries in...\n" +
            "2) Top N countries in...\n" +
            "3) All cities in...\n" +
            "4) Top N cities in...\n" +
            "5) All capital cities in...\n" +
            "6) Top N capital cities in...\n" +
            "7) Residence report of people living in...\n" +
            "8) Population of...\n" +
            "9) Report on number of speakers per language for chinese, english, hindi, spanish and arabic\n" +
            "All results will be sorted in order of largest population to smallest\n" +
            "Enter 'q' at any time to exit";
    private final ArrayList<QueryInfo> queryList = new ArrayList<>();

    public UserPrompt(DAO dao)
    {
        this.dao = dao;
        this.scanner = new Scanner(System.in);

        queryList.add(new QueryInfo(1,
                "All countries in",
                new String[]{"the world", "a given continent", "a given region"}));
        queryList.add(new QueryInfo(2,
                "Top N countries in",
                new String[]{"the world", "a given continent", "a given region"}));
        queryList.add(new QueryInfo(3,
                "All cities in",
                new String[]{"the world", "a given continent", "a given region", "a given country", "a given district"}));
        queryList.add(new QueryInfo(4,
                "Top N cities in",
                new String[]{"the world", "a given continent", "a given region", "a given country", "a given district"}));
        queryList.add(new QueryInfo(5,
                "All capital cities in",
                new String[]{"the world", "a given continent", "a given region"}));
        queryList.add(new QueryInfo(6,
                "Top N capital cities in",
                new String[]{"the world", "a given continent", "a given region"}));
        queryList.add(new QueryInfo(7,
                "Report on people living in vs not in cities in",
                new String[]{"a given continent", "a given region", "a given country"}));
        queryList.add(new QueryInfo(8,
                "The population of",
                new String[]{"the world", "a given continent", "a given region", "a given country", "a given district", "a given city"}));
        queryList.add(new QueryInfo(9,
                "Report on the number of speakers of Chinese, English, Spanish, Hindi and Arabic worldwide",
                new String[]{}));
    }

    public void enterLoop()
    {
        int parentQueryChoice = obtainParentQueryChoice();

        String areaFilter = "";
        if (parentQueryChoice != 9)
        {
            areaFilter = obtainChildQueryChoice(parentQueryChoice);
        }

        String areaNameInput = "";
        if (parentQueryChoice != 9 && areaFilter != App.WORLD)
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

    private int obtainParentQueryChoice()
    {
        int parentQueryChoice = -1;

        while (parentQueryChoice < 1)
        {
            System.out.println(USE_CASE_PROMPT);
            Integer input = obtainValidIntWithinRange(queryList.size());
            if (input == null)
            {
                System.out.println("Sorry, that's not a valid choice. Pick a number from 1 to " + queryList.size());
            }
            else
            {
                parentQueryChoice = input;
            }
        }
        return parentQueryChoice;
    }

    private String obtainChildQueryChoice(int parentQuery)
    {
        if (parentQuery == 9) return "";

        QueryInfo queryInfo = queryList.get(parentQuery - 1);
        String[] childQueries = queryInfo.getChildQueries();

        String childQueryPrompt = String.format("Choose the type of \"%s\" query you'd like to run.\n", queryInfo.getParentQuery());
        for (int i = 0; i < queryInfo.getChildQueries().length; i++)
        {
            childQueryPrompt += String.format("%d) %s %s\n", i+1, queryInfo.getParentQuery(), childQueries[i]);
        }

        int childQuery = -1;
        while (childQuery < 1)
        {
            System.out.println(childQueryPrompt);
            Integer input = obtainValidIntWithinRange(childQueries.length);
            if (input == null)
            {
                System.out.println("Sorry, that's not a valid choice. Pick a number from 1 to " + childQueries.length);
            }
            else
            {
                childQuery = input;
            }
        }

        return parseQueryInputForAreaFilter(parentQuery, childQuery);
    }

    private Integer obtainValidIntWithinRange(int maxNum)
    {
        int input = scanner.nextInt();

        if (input < 1 || input > maxNum) return null;

        return input;
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
            case (3):
                records = dao.allCitiesIn(areaFilter, areaName);
                break;
            case (4):
                records = dao.topNCitiesIn(areaFilter, areaName, n);
                break;
            case (5):
                records = dao.allCapitalCitiesIn(areaFilter, areaName);
                break;
            case (6):
                records = dao.topNCapitalCitiesIn(areaFilter, areaName, n);
                break;
            case (7):
                records = dao.populationLivingInAndNotInCities(areaFilter, areaName);
                break;
            case (8):
                records = dao.populationOf(areaFilter, areaName);
                break;
            case (9):
                records = dao.languageReport();
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

class QueryInfo
{
    private final int queryId;
    private final String parentQuery;
    private final String[] childQueries;

    public QueryInfo(int queryId, String parentQuery, String[] childQueries)
    {
        this.queryId = queryId;
        this.parentQuery = parentQuery;
        this.childQueries = childQueries;
    }

    public int getQueryId() { return queryId; }
    public String getParentQuery() { return parentQuery; }
    public String[] getChildQueries() { return childQueries; }
}