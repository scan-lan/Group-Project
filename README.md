# SEM Group Project
[![Build Status](https://img.shields.io/travis/com/scan-lan/Group-Project/master?label=master%20build)](https://travis-ci.com/scan-lan/Group-Project)
[![Build Status](https://img.shields.io/travis/com/scan-lan/Group-Project/develop?label=develop%20build)](https://travis-ci.com/scan-lan/Group-Project)
[![codecov](https://codecov.io/gh/scan-lan/Group-Project/branch/master/graph/badge.svg?token=XBP764GI1F)](https://codecov.io/gh/scan-lan/Group-Project)
[![Releases](https://img.shields.io/github/release/scan-lan/group-project/all.svg)](https://github.com/scan-lan/group-project/releases)
[![LICENSE](https://img.shields.io/github/license/scan-lan/group-project.svg)](https://github.com/scan-lan/group-project/blob/master/LICENSE)

Starring: Lukas Paling and Marco De Luca as the developers or "Script Monkeys"

With Craig Gordon as the Scrum Master or "Scrumbag" and Luke Scanlan as the lead developer or "Head Honcho"

## Overview of the application, and its functionality
The application:
- Creates a docker container with the world database open for connections
- Prompts the user in the console to select a report type
- Runs SQL queries against the database that generate the user's requested report
- Presents the output to the user
- Allows the user to exit at any point in the process
- Loops back to allow the user to select another query

## Requirements Met

32 requirements of 32 requirements have been implemented, which is 100%

| ID  |                                                  Name                                                  |  Met  |                   Screenshot                           |
|----:|:-------------------------------------------------------------------------------------------------------|------:|-------------------------------------------------------:|
| 1.1 |  All the countries in the world organised by largest population to smallest.                           |  Yes  |![Use Case 1 World](screenshots/useCase1.1.png)         |
| 1.2 |  All the countries in a continent organised by largest population to smallest.                         |  Yes  |![Use Case 1 Continent](screenshots/useCase1.2.png)     |
| 1.3 |  All the countries in a region organised by largest population to smallest.                            |  Yes  |![Use Case 1 Region](screenshots/useCase1.3.png)        |
| 2.1 |  The top N most populous countries in the world where N is provided by the user.                       |  Yes  |![Use Case 2 World](screenshots/useCase2.1.png)         |
| 2.2 |  The top N most populous countries in a continent where N is provided by the user.                     |  Yes  |![Use Case 2 Continent](screenshots/useCase2.2.png)     |
| 2.3 |  The top N most populous countries in a region where N is provided by the user.                        |  Yes  |![Use Case 2 Region](screenshots/useCase2.3.png)        |
| 3.1 |  All the cities in the world organised by largest population to smallest.                              |  Yes  |![Use Case 3 World](screenshots/useCase3.1.png)         |
| 3.2 |  All the cities in a continent organised by largest population to smallest.                            |  Yes  |![Use Case 3 Continent](screenshots/useCase3.2.png)     |
| 3.3 |  All the cities in a region organised by largest population to smallest.                               |  Yes  |![Use Case 3 Region](screenshots/useCase3.3.png)        |
| 3.4 |  All the cities in a country organised by largest population to smallest.                              |  Yes  |![Use Case 3 Country](screenshots/useCase3.4.png)       |
| 3.5 |  All the cities in a district organised by largest population to smallest.                             |  Yes  |![Use Case 3 District](screenshots/useCase3.5.png)      |
| 4.1 |  The top N most populous cities in the world where N is provided by the user.                          |  Yes  |![Use Case 4 World](screenshots/useCase4.1.png)         |
| 4.2 |  The top N most populous cities in a continent where N is provided by the user.                        |  Yes  |![Use Case 4 Continent](screenshots/useCase4.2.png)     |
| 4.3 |  The top N most populous cities in a region where N is provided by the user.                           |  Yes  |![Use Case 4 Region](screenshots/useCase4.3.png)        |
| 4.4 |  The top N most populous cities in a country where N is provided by the user.                          |  Yes  |![Use Case 4 Country](screenshots/useCase4.4.png)       |
| 4.5 |  The top N most populous cities in a district where N is provided by the user.                         |  Yes  |![Use Case 4 District](screenshots/useCase4.5.png)      |
| 5.1 |  All the capital cities in the world organised by largest population to smallest.                      |  Yes  |![Use Case 5 World](screenshots/useCase5.1.png)         |
| 5.2 |  All the capital cities in a continent organised by largest population to smallest.                    |  Yes  |![Use Case 5 Continent](screenshots/useCase5.2.png)     |
| 5.3 |  All the capital cities in the region organised by largest population to smallest.                     |  Yes  |![Use Case 5 Region](screenshots/useCase5.3.png)        |
| 6.1 |  The top N most populous capital cities in the world where N is provided by the user.                  |  Yes  |![Use Case 6 World](screenshots/useCase6.1.png)         |
| 6.2 |  The top N most populous capital cities in a continent where N is provided by the user.                |  Yes  |![Use Case 6 Continent](screenshots/useCase6.2.png)     |
| 6.3 |  The top N most populous capital cities in a region where N is provided by the user.                   |  Yes  |![Use Case 6 Region](screenshots/useCase6.3.png)        |
| 7.1 |  The population of people, people living in cities, and people not living in cities in a continent.    |  Yes  |![Use Case 7 Each Continent](screenshots/useCase7.1.png)|
| 7.2 |  The population of people, people living in cities, and people not living in cities in a region.       |  Yes  |![Use Case 7 Each Region](screenshots/useCase7.2.png)   |
| 7.3 |  The population of people, people living in cities, and people not living in cities in a country.      |  Yes  |![Use Case 7 Each Country](screenshots/useCase7.3.png)  |
| 8.1 |  The population of the world                                                                           |  Yes  |![Use Case 8 World](screenshots/useCase8.1.png)         |
| 8.2 |  The population of a specified continent                                                               |  Yes  |![Use Case 8 Continent](screenshots/useCase8.2.png)     |
| 8.3 |  The population of a specified region                                                                  |  Yes  |![Use Case 8 Region](screenshots/useCase8.3.png)        |
| 8.4 |  The population of a specified country                                                                 |  Yes  |![Use Case 8 Country](screenshots/useCase8.4.png)       |
| 8.5 |  The population of a specified district                                                                |  Yes  |![Use Case 8 District](screenshots/useCase8.5.png)      |
| 8.6 |  The population of a specified city                                                                    |  Yes  |![Use Case 8 City](screenshots/useCase8.6.png)          |
| 9.1 |  The number of people who speak the following the following languages from greatest number to smallest, including the percentage of the world population:                                           |  Yes  |![Use Case 9 Languages](screenshots/useCase9.1.png) |
