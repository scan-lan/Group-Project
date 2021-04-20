package com.napier.sem;

import java.util.*;

public class UserPrompt
{
    private final DAO dao;
    private final Scanner scanner;
    boolean userWantsToContinue;
    private static final int[] topNQueryParentIds = new int[]{2, 4, 6};
    private static final String USE_CASE_PROMPT = "--------------------------------------------------------------------" +
            "--------------------------------------------------------------------\n" +
            "Enter the number corresponding to the type of query you'd like to run\n" +
            "Your options are as follows:\n\n" +
            "1) All countries in...\n" +
            "2) Top N countries in...\n" +
            "3) All cities in...\n" +
            "4) Top N cities in...\n" +
            "5) All capital cities in...\n" +
            "6) Top N capital cities in...\n" +
            "7) Residence report of people living in...\n" +
            "8) Population of...\n" +
            "9) Report on number of speakers per language for chinese, english, hindi, spanish and arabic\n\n" +
            "All results will be sorted in order of largest population to smallest\n" +
            "Enter 'q' at any time to exit\n" +
            "--------------------------------------------------------------------" +
            "--------------------------------------------------------------------";
    private final ArrayList<QueryInfo> queryList = new ArrayList<>();

    public UserPrompt(DAO dao)
    {
        this.dao = dao;
        this.scanner = new Scanner(System.in).useDelimiter("\\n");

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

    public void start()
    {
        userWantsToContinue = true;
        while (userWantsToContinue)
        {
            int parentQueryChoice = obtainInputWithPrompt(USE_CASE_PROMPT, queryList.size());
            if (parentQueryChoice == -1)
            {
                userWantsToContinue = false;
                continue;
            }

            String areaFilter = "";
            if (parentQueryChoice != 9)
            {
                areaFilter = obtainChildQueryChoice(parentQueryChoice);
                if (!userWantsToContinue) continue;
            }

            String areaNameInput = "";
            if (parentQueryChoice != 9 && !areaFilter.equals(App.WORLD))
            {
                System.out.printf("Enter the name of the %s you'd like to query%n", areaFilter);

                areaNameInput = scanner.next();
            }

            int n = 0;
            // checks if the query takes an n value, and asks for it from the use if it does
            if (Arrays.binarySearch(topNQueryParentIds, parentQueryChoice) >= 0)
            {
                n = obtainInputWithPrompt("Enter the number of records you'd like to see", 4080);

                if (n == -1)
                {
                    userWantsToContinue = false;
                    continue;
                }
            }

            executeQueryFromInput(parentQueryChoice, areaFilter, areaNameInput, n);

            System.out.println("--------------------------------------------------------------------" +
                    "--------------------------------------------------------------------\n" +
                    "Do you want to run another query? (y)es/(n)o");
            String continueInput = scanner.next();
            while (!(continueInput.equals("y") || continueInput.equals("n")))
            {
                System.out.println("Please enter 'y' or 'n'");
                continueInput = scanner.next();
            }

            if (formatInput(continueInput).equals("n")) userWantsToContinue = false;
        }
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

        int childQuery = obtainInputWithPrompt(childQueryPrompt, childQueries.length);

        return parseQueryInputForAreaFilter(parentQuery, childQuery);
    }

    private int obtainInputWithPrompt(String prompt, int maxNum)
    {
        int validInput = 0;
        while (validInput == 0)
        {
            System.out.println(prompt);
            Integer input = obtainValidIntWithinRange(maxNum);
            if (input == null)
            {
                System.out.println("Sorry, that's not a valid choice. Pick a number from 1 to " + maxNum);
            }
            else
            {
                validInput = input;
            }
        }
        return validInput;
    }

    private Integer obtainValidIntWithinRange(int maxNum)
    {
        int input = 0;
        try
        {
            input = scanner.nextInt();
        }
        catch (InputMismatchException e)
        {
            if (formatInput(scanner.next()).equals("q")) return -1;
            else return null;
        }

        if (input < 1 || input > maxNum) return null;

        return input;
    }

    private String formatInput(String input)
    {
        return input.trim().toLowerCase(Locale.ROOT);
    }

    /**
     * This returns the area filter that will be used for the query, given the selection that the user has made
     * @param parentQueryChoice the id of the top-level query (1-9)
     * @param areaFilterChoice the id of the area filter that the user has chosen, which can go from 1 to 6
     *                         depending on the query
     * @return the string representing the area filter
     */
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
            case (-1):
                userWantsToContinue = false;
                return "";
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

        if (records.size() == 0)
        {
            System.out.println("Your query had no results");
            return;
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