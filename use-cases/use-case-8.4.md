# USE CASE: 8.4 Produce a report on the population of a country

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *System User* I want *to produce a report on the population of a country* so that *I can facilitate easy access to population information.*

### Scope

Company

### Level

Primary task.

### Preconditions

We know which country.  Database contains population statistics that are as up to date as possible.

### Success End Condition

A report is successfully generated that includes the following information about a country:

*The name of the country*
*The total population of the country,*
*The total population of the country living in cities (including a %),*
*The total population of the country not living in cities (including a %).*


### Failed End Condition

No report is produced.

### Primary Actor

System User.

### Trigger

A request for population data from the organisation.

## MAIN SUCCESS SCENARIO

1. The organisation requests information on the population of a country
2. System user captures the name of the country to get the population information for.
3. System user extracts population information of the population of a country
4. System user provides report to the organisation.

## EXTENSIONS

3. **country does not exist**:
    1. System user informs the organisation that no such country exists

## SUB-VARIATIONS

None. 

## SCHEDULE

**DUE DATE**: Release TBC