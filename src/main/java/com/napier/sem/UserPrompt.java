package com.napier.sem;

import java.util.*;

public class UserPrompt
{
    private final DAO dao;
    private final Scanner scanner;
    boolean userWantsToQuit;
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

    /**
     * The main loop of the command line app.  This takes the user through each step of running a query and
     * getting results.  The user is given prompts which they must enter numbers to respond to, and they can
     * enter "q" at any time to exit the app.
     */
    public void start()
    {
        userWantsToQuit = false;
        while (!userWantsToQuit)
        {
            int chosenQueryId = obtainInputWithPrompt(USE_CASE_PROMPT, queryList.size());
            if (chosenQueryId == -1) continue;

            String chosenAreaFilter = "";
            if (chosenQueryId != 9)
            {
                chosenAreaFilter = obtainAreaFilterChoice(chosenQueryId);
                if (userWantsToQuit) continue;
            }

            String areaNameInput = "";
            if (chosenQueryId != 9 && !chosenAreaFilter.equals(App.WORLD))
            {
                System.out.printf("Enter the name of the %s you'd like to query%n", chosenAreaFilter);

                areaNameInput = formatInput(scanner.next());
                // exit the loop if the user enters "q" indicating they want to quit
                if (areaNameInput.equals("q"))
                {
                    userWantsToQuit = true;
                    continue;
                }
            }

            int n = 0;
            // checks if the query takes an n value, and asks for it from the use if it does
            if (Arrays.binarySearch(topNQueryParentIds, chosenQueryId) >= 0)
            {
                n = obtainInputWithPrompt("Enter the number of records you'd like to see", 4080);

                if (n == -1) continue;
            }

            executeQueryFromInput(chosenQueryId, chosenAreaFilter, areaNameInput, n);
        }

        System.out.println("Until next time");
    }

    /**
     * This displays the area filters (world, continent, region etc.) available for the selected query
     * as a numbered list, and asks the user which one they'd like to choose
     * asks the
     * @param parentQuery The ID of the type of query being run
     * @return The string representing the area filter
     */
    private String obtainAreaFilterChoice(int parentQuery)
    {
        if (parentQuery == 9) return "";

        QueryInfo queryInfo = queryList.get(parentQuery - 1);
        String[] areaFilterDescriptions = queryInfo.getAreaFilterDescriptions();

        String childQueryPrompt = String.format("Choose the type of \"%s\" query you'd like to run.\n", queryInfo.getQueryDescription());
        for (int i = 0; i < queryInfo.getAreaFilterDescriptions().length; i++)
        {
            childQueryPrompt += String.format("%d) %s %s\n", i+1, queryInfo.getQueryDescription(), areaFilterDescriptions[i]);
        }

        int childQuery = obtainInputWithPrompt(childQueryPrompt, areaFilterDescriptions.length);

        return parseQueryInputForAreaFilter(parentQuery, childQuery);
    }

    /**
     * Display a given string as a prompt for a user to select from a numbered list, and then take an
     * integer as input from the user.  If the input is not an integer or is larger than the maximum
     * passed in as an argument, the prompt is displayed again and the user is given an error message.
     * Will return -1 if the user enters "q", indicating they want to quit.
     * @param prompt A string that will be printed in the console explaining what the user should enter
     * @param maxNum An integer representing the largest number a user should be able to enter
     * @return An integer representing the user's choice
     */
    private int obtainInputWithPrompt(String prompt, int maxNum)
    {
        int validInput = 0;
        while (validInput == 0)
        {
            System.out.println(prompt);
            Integer input = obtainIntWithinRange(maxNum);
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

    /**
     * Scans input for a number from 1 to the maxNum, and returns null if there is no matching input.
     * Returns -1 if the user enters "q", indicating they want to quit.
     * @param maxNum An integer representing the largest number which should be accepted as valid input
     * @return An integer from 1 to maxNum, -1 if the user enters "q", or null if no valid input is found
     */
    private Integer obtainIntWithinRange(int maxNum)
    {
        int input = 0;
        try
        {
            input = scanner.nextInt();
        }
        catch (InputMismatchException e)
        {
            if (formatInput(scanner.next()).equals("q"))
            {
                userWantsToQuit = true;
                return -1;
            }
            else return null;
        }

        if (input < 1 || input > maxNum) return null;

        return input;
    }

    /**
     * Remove whitespace from around input strings and convert to lowercase
     * @param input The string that will be formatted
     * @return The formatted string
     */
    private String formatInput(String input)
    {
        return input.trim().toLowerCase(Locale.ROOT);
    }

    /**
     * This returns the area filter that will be used for the query, given the selection that the user has made
     * @param queryId the id of the top-level query (1-9)
     * @param areaFilterChoice the id of the area filter that the user has chosen, which can go from 1 to 6
     *                         depending on the query
     * @return the string representing the area filter
     */
    private String parseQueryInputForAreaFilter(int queryId, int areaFilterChoice)
    {
        // query 7 is the only query which can't be run on the world, so we increment the area filter choice by one
        if (queryId == 7) areaFilterChoice++;

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
                userWantsToQuit = true;
                return "";
            default:
                return null;
        }
    }

    /**
     * Runs the query corresponding to the queryId, with the areaFilter and n value that the user has given.
     * Depending on the query, areaFilter or n may not be used.  Once the query has been run, its results
     * are printed in the console.
     * @param queryId An integer from 1-9 corresponding to a unique query
     * @param areaFilter The type of area you want to run the query over e.g. "world", "continent" etc.
     * @param areaName The name of the area you'd like to query e.g. for a "country" areaFilter, "France"
     * @param n The number of results returned if the query is a "Top N" query
     */
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
            System.out.println("Your query had no results, maybe try again with different input");
            return;
        }

        for (Record record: records) System.out.println(record);
    }
}

/**
 * Object used for storing the information used while displaying queries and their area filter options
 */
class QueryInfo
{
    private final int queryId;
    private final String queryDescription;
    private final String[] areaFilterDescriptions;

    public QueryInfo(int queryId, String queryDescription, String[] areaFilterDescriptions)
    {
        this.queryId = queryId;
        this.queryDescription = queryDescription;
        this.areaFilterDescriptions = areaFilterDescriptions;
    }

    public int getQueryId() { return queryId; }
    public String getQueryDescription() { return queryDescription; }
    public String[] getAreaFilterDescriptions() { return areaFilterDescriptions; }
}