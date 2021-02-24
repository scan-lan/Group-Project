# USE CASE: 3.3 Produce a report on the population of cities in a region

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *System User* I want *to produce a report on all cities in a region organised by largest population to smallest* so that *I can facilitate easy access to population information.*

### Scope

Company

### Level

Primary task.

### Preconditions

We know the region.  Database contains population statistics that are as up to date as possible.

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

1. The organisation requests information on all cities in a region organised from largest to smallest.
2. System user captures the name of the region to get the population information for.
3. System user extracts population information of all cities in a region ordered largest to smallest by population.
4. System user provides report to the organisation.

## EXTENSIONS

3. **region does not exist**:
    1. System user informs the organisation that no such region exists

## SUB-VARIATIONS

None. 

## SCHEDULE

**DUE DATE**: Release TBC