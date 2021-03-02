# USE CASE: 3.5 Produce a report on the population of cities in a district

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *System User* I want *to produce a report on all cities in a district organised by largest population to smallest* so that *I can facilitate easy access to population information.*

### Scope

Company

### Level

Primary task.

### Preconditions

We know the district.  Database contains population statistics that are as up to date as possible.

### Success End Condition

A report is successfully generated that includes the following information about the cities:

*Name,*
*Country,*
*District,*
*Population.*


### Failed End Condition

No report is produced.

### Primary Actor

System User.

### Trigger

A request for population data from the organisation.

## MAIN SUCCESS SCENARIO

1. The organisation requests information on all cities in a district organised from largest to smallest.
2. System user captures the name of the district to get the population information for.
3. System user extracts population information of all cities in a district ordered largest to smallest by population.
4. System user provides report to the organisation.

## EXTENSIONS

3. **district does not exist**:
    1. System user informs the organisation that no such district exists

## SUB-VARIATIONS

None. 

## SCHEDULE

**DUE DATE**: Release TBC