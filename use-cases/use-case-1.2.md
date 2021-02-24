# USE CASE: 1.2 Produce a report on the population of countries in a continent

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *System User* I want *to produce a report on all countries in a continent organised by largest population to smallest* so that *I can facilitate easy access to population information.*

### Scope

Company

### Level

Primary task.

### Preconditions

We know which continent.  Database contains population statistics that are as up to date as possible.

### Success End Condition

A report is successfully generated that includes the following information about the countries:
*Code,*
*Name,*
*Continent,*
*Region,*
*Population,*
*Capital.*

### Failed End Condition

No report is produced.

### Primary Actor

System User.

### Trigger

A request for population data from the organisation.

## MAIN SUCCESS SCENARIO

1. The organisation requests information on all countries in a continent organised from largest to smallest.   
2. System user captures the name of the continent to get the population information for.
3. System user extracts population information of all countries in a continent ordered largest to smallest by population.
4. System user provides report to the organisation.

## EXTENSIONS

N/A

## SUB-VARIATIONS

None. 

## SCHEDULE

**DUE DATE**: Release TBC