# Technical Test Exercise

# To run
mvn -Dtest=CareersTest test

# Config
In resources/LocalConf.properties, set the following properties:
- BROWSER
- OS
- LANGUAGE
- COUNTRY
- PATHS TO DRIVERS

# Expected results
  1. There is a total of 5 results for Location: “Krakow”  
  As only 3 results are being returned, the test must fail.  
  
  2. There is at least 1 result for Location “Warsaw”  
  The test must pass.  
  
  3. One of the qualifications for job offer: Location: “Raleigh”/ Software QA Analyst is:  
  “Experience creating and implementing testing framework for web applications such as Selenium”  
  As there is not any "Software QA Analyst" option in the Role dropdown, the test must fail.  
  
