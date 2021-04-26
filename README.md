# Software Engineering Methods

Starring: Lukas Paling and Marco De Luca as the developers or "Script Monkeys"

With Craig Gordon as the Scrum Master or "Scrum Bag" and Luke Scanlan as the lead developer or "Head Honcho"

# High level overview of the application, and it's functionality

The application:
- Creates a docker container with the world database with port 3306 open for connections
- Prompts the user in the console to input their selected parent query and sub query details
- Runs SQL queries against the database that satisfy the user requirements
- Presents the output to the user 
- It then loops back to allowing the user to select a query and lets them exit at any point


Build Status
-
- Master Build Status
  [![Build Status](https://travis-ci.com/scan-lan/group-project.svg?branch=master)](https://travis-ci.com/Craig180885-napier/Group-Project)
- Develop Build Status
  [![Build Status](https://travis-ci.com/scan-lan/group-project.svg?branch=develop)](https://travis-ci.com/Craig180885-napier/Group-Project)
  [![LICENSE](https://img.shields.io/github/license/scan-lan/group-project.svg)](https://github.com/Craig180885-napier/group-project/blob/master/LICENSE)
  [![Releases](https://img.shields.io/github/release/scan-lan/group-project/all.svg)](https://github.com/Craig180885-napier/group-project/releases)
- Code Coverage
  [![codecov](https://codecov.io/gh/scan-lan/Group-Project/branch/master/graph/badge.svg?token=XBP764GI1F)](https://codecov.io/gh/Craig180885-napier/Group-Project)
  
## Requirements Met

32 requirements of 32 requirements have been implemented, which is 100%

| ID  |                                                  Name                                                  |  Met  |                   Screenshot                           |
|----:|:------------------------------------------------------------------------------------------------------|------:|-------------------------------------------------------:|
| 1.1 |  All the countries in the world organised by largest population to smallest.                           |  Yes  |![Use Case 1 World](Screenshots/UseCase1.1.jpg)         |        
| 1.2 |  All the countries in a continent organised by largest population to smallest.                         |  Yes  |![Use Case 1 Continent](Screenshots/UseCase1.2.jpg)     |                          
| 1.3 |  All the countries in a region organised by largest population to smallest.                            |  Yes  |![Use Case 1 Region](Screenshots/UseCase1.3.jpg)        |
| 2.1 |  The top N populated countries in the world where N is provided by the user.                           |  Yes  |![Use Case 2 World](Screenshots/UseCase2.1.jpg)         |
| 2.2 |  The top N populated countries in a continent where N is provided by the user.                         |  Yes  |![Use Case 2 Continent](Screenshots/UseCase2.2.jpg)     |
| 2.3 |  The top N populated countries in a region where N is provided by the user.                            |  Yes  |![Use Case 2 Region](Screenshots/UseCase2.3.jpg)        |
| 3.1 |  All the cities in the world organised by largest population to smallest.                              |  Yes  |![Use Case 3 World](Screenshots/UseCase3.1.jpg)         |
| 3.2 |  All the cities in a continent organised by largest population to smallest.                            |  Yes  |![Use Case 3 Conintent](Screenshots/UseCase3.2.jpg)     |
| 3.3 |  All the cities in a region organised by largest population to smallest.                               |  Yes  |![Use Case 3 Region](Screenshots/UseCase3.3.jpg)        |
| 3.4 |  All the cities in a country organised by largest population to smallest.                              |  Yes  |![Use Case 3 Country](Screenshots/UseCase3.4.jpg)       |
| 3.5 |  All the cities in a district organised by largest population to smallest.                             |  Yes  |![Use Case 3 District](Screenshots/UseCase3.5.jpg)      |
| 4.1 |  The top N populated cities in the world where N is provided by the user.                              |  Yes  |![Use Case 4 World](Screenshots/UseCase4.1.jpg)         |
| 4.2 |  The top N populated cities in a continent where N is provided by the user.                            |  Yes  |![Use Case 4 Continent](Screenshots/UseCase4.2.jpg)     |
| 4.3 |  The top N populated cities in a region where N is provided by the user.                               |  Yes  |![Use Case 4 Region](Screenshots/UseCase4.3.jpg)        |
| 4.4 |  The top N populated cities in a country where N is provided by the user.                              |  Yes  |![Use Case 4 Country](Screenshots/UseCase4.4.jpg)       |
| 4.5 |  The top N populated cities in a district where N is provided by the user.                             |  Yes  |![Use Case 4 District](Screenshots/UseCase4.5.jpg)      |
| 5.1 |  All the capital cities in the world organised by largest population to smallest.                      |  Yes  |![Use Case 5 World](Screenshots/UseCase5.1.jpg)         |
| 5.2 |  All the capital cities in a continent organised by largest population to smallest.                    |  Yes  |![Use Case 5 Continent](Screenshots/UseCase5.2.jpg)     |
| 5.3 |  All the capital cities in the region organised by largest population to smallest.                     |  Yes  |![Use Case 5 Region](Screenshots/UseCase5.3.jpg)        |
| 6.1 |  The top N populated capital cities in the world where N is provided by the user.                      |  Yes  |![Use Case 6 World](Screenshots/UseCase6.1.jpg)         |
| 6.2 |  The top N populated capital cities in a continent where N is provided by the user.                    |  Yes  |![Use Case 6 Continent](Screenshots/UseCase6.2.jpg)     |
| 6.3 |  The top N populated capital cities in a region where N is provided by the user.                       |  Yes  |![Use Case 6 Region](Screenshots/UseCase6.3.jpg)        |
| 7.1 |  The population of people, people living in cities, and people not living in cities in each continent. |  Yes  |![Use Case 7 Each Continent](Screenshots/UseCase7.1.jpg)|
| 7.2 |  The population of people, people living in cities, and people not living in cities in each region.    |  Yes  |![Use Case 7 Each Region](Screenshots/UseCase7.2.jpg)   |
| 7.3 |  The population of people, people living in cities, and people not living in cities in each country.   |  Yes  |![Use Case 7 Each Country](Screenshots/UseCase7.3.jpg)  |
| 8.1 |  The population of the world.                                                                          |  Yes  |![Use Case 8 World](Screenshots/UseCase8.1.jpg)         |
| 8.2 |  The population of the world.                                                                          |  Yes  |![Use Case 8 Continent](Screenshots/UseCase8.2.jpg)     |
| 8.3 |  The population of the world.                                                                          |  Yes  |![Use Case 8 Region](Screenshots/UseCase8.3.jpg)        |
| 8.4 |  The population of the world.                                                                          |  Yes  |![Use Case 8 Country](Screenshots/UseCase8.4.jpg)       |
| 8.5 |  The population of the world.                                                                          |  Yes  |![Use Case 8 District](Screenshots/UseCase8.5.jpg)      |
| 8.6 |  The population of the world.                                                                          |  Yes  |![Use Case 8 City](Screenshots/UseCase8.6.jpg)          |
| 9.1 |  The number of people who speak the following the following languages from greatest number to smallest, including the percentage of the world population:     |  Yes  |![Use Case 9 Languages](Screenshots/UseCase9.1.jpg) |
