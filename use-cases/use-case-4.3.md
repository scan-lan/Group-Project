# USE CASE: 4.3 Produce a report on the top N populated cities in a region where N is provided by the user.

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *System User* I want *to produce a report on the top N populated cities in a region where N is provided by the user.* so that *I can facilitate easy access to population information.*

### Scope

Company

### Level

Primary task.

### Preconditions

1. User is able to specify N.
2. N must be an integer.
3. We know the Region.  
4. Database contains population statistics that are as up to date as possible.

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

1. The organisation requests information on the top N populated cities in a region where N is provided by the user.   
2. System user captures the value of N.
3. System user extracts population information of the top N populated cities in a region where N is provided by the user.
4. System user provides report to the organisation.

## EXTENSIONS

3. **Region does not exist**:
   1. System user informs the organisation that no such region exists

## SUB-VARIATIONS

None. 

## SCHEDULE

**DUE DATE**: Release TBC