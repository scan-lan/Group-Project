WITH x AS (SELECT SUM(population) AS world_population FROM country)
SELECT `language`, speakers, (speakers / world_population * 100) AS percentage
FROM x, (
    SELECT `language`,
       CEILING(SUM(population * (percentage / 100))) AS speakers
    FROM countrylanguage
             JOIN country
                  ON countrycode = code
    GROUP BY `language`
    ORDER BY speakers DESC
    LIMIT 5) AS language_info;