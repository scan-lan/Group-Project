# USE CASE: 8.6 Produce a report on the population of a city

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *System User* I want *to produce a report on the population of a city* so that *I can facilitate easy access to population information.*

### Scope

Company

### Level

Primary task.

### Preconditions

We know which city.  Database contains population statistics that are as up to date as possible.

### Success End Condition

A report is successfully generated that includes the following information about a city:


*The total population of the city,*



### Failed End Condition

No report is produced.

### Primary Actor

System User.

### Trigger

A request for population data from the organisation.

## MAIN SUCCESS SCENARIO

1. The organisation requests information on the population of a city
2. System user captures the name of the city to get the population information for.
3. System user extracts population information of the population of a city
4. System user provides report to the organisation.

## EXTENSIONS

3. **city does not exist**:
    1. System user informs the organisation that no such city exists

## SUB-VARIATIONS

None. 

## SCHEDULE

**DUE DATE**: Release TBC