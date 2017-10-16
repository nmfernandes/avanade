import helpers.MessageFileToUser;
import helpers.Utils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import pageObjects.CareersPage;
import pageObjects.ExploreCareersPage;
import pageObjects.HomePage;

import static helpers.Drivers.destroyLastDriver;

/**
 * Test the search in the Explore Careers page.
 */
public class CareersTest {

    private static final String SESSION = "session1";
    private static final String CITY1_KEY = "KRAKOW";
    private static final String CITY2_KEY = "WARSAW";
    private static final String CITY3_KEY = "RALEIGH";
    private static final String COUNTRY1_KEY = "POLAND";
    private static final String COUNTRY2_KEY = "ALL_LOCATIONS";
    private static final String ROLE_KEY = "SOFTWARE_QA_ANALYST";
    private static final String QUALIFICATION_KEY = "QUALIFICATION";

    private static final int N_RESULTS_CITY1 = 5;
    private static final int N_RESULTS_CITY2 = 1;
    private static final int N_RESULTS_CITY3 = 1;

    public CareersTest() throws Exception {

    }

    private ExploreCareersPage support(String city, String country) throws Exception {
        String browser = Utils.getBrowser();

        HomePage home = new HomePage(SESSION, browser);
        home.clickOnExploreCareersBtn();

        CareersPage careersPage = new CareersPage(SESSION, browser);
        careersPage.clickOnExploreCareersBtn();

        ExploreCareersPage exploreCareersPage = new ExploreCareersPage(SESSION, browser);
        exploreCareersPage.writeSearchBoxFilter(Utils.getResourceBundle().getString(city));
        exploreCareersPage.selectCountry(Utils.getResourceBundle().getString(country));

        return exploreCareersPage;
    }


    /**
     * Test if there is a total of 5 results for location Krakow
     */
    @Test
    public void resultsLocation1Test() throws Exception {
        ExploreCareersPage exploreCareersPage = support(CITY1_KEY, COUNTRY1_KEY);
        exploreCareersPage.clickJobSearchBtn();
        Assert.assertEquals(String.format(MessageFileToUser.WRONG_NUMBER_RESULTS), N_RESULTS_CITY1,
                exploreCareersPage.getNumberResults());
    }

    /**
     * Test if there is at least 1 result for location Warsaw
     */
    @Test
    public void resultsLocation2Test() throws Exception {
        ExploreCareersPage exploreCareersPage = support(CITY2_KEY, COUNTRY1_KEY);
        exploreCareersPage.clickJobSearchBtn();
        Assert.assertTrue(String.format(MessageFileToUser.WRONG_NUMBER_RESULTS),
                exploreCareersPage.getNumberResults() >= N_RESULTS_CITY2);
    }

    /**
     * Test if one of the qualifications for job offer: Location: Raleigh/ Software QA Analyst is:
     Experience creating and implementing testing framework for web applications such as
     Selenium
     */
    @Test
    public void resultsLocation3Test() throws Exception {
        ExploreCareersPage exploreCareersPage = support(CITY3_KEY, COUNTRY2_KEY);
        exploreCareersPage.selectRole(ROLE_KEY);
        exploreCareersPage.clickJobSearchBtn();
        Assert.assertTrue(String.format(MessageFileToUser.WRONG_NUMBER_RESULTS),
                exploreCareersPage.getNumberResults() >= N_RESULTS_CITY3);

        exploreCareersPage.clickFirstResult();
        exploreCareersPage.findByText(QUALIFICATION_KEY);
    }

    /**
     * After each test, quit driver
     */
    @After
    public void tearDown() throws Exception {
        destroyLastDriver();
    }
}
