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
 * Created by nnnn1 on 15/10/2017.
 */
public class CareersTest {


    public static final String SESSION = "session1";
    public static final String CITY1 = "Krakow";
    public static final String CITY2 = "Warsaw";
    public static final String COUNTRY = "Poland";
    //public static final String ROLE = "Software QA Analyst";
    public static final String ROLE = "Software Engineering";
    public static final String QUALIFICATION_KEY = "QUALIFICATION";

    public static final int N_RESULTS_CITY1 = 5;
    public static final int N_RESULTS_CITY2 = 1;
    public static final int N_RESULTS_CITY3 = 1;

    public CareersTest() throws Exception {

    }

    public ExploreCareersPage support(String city, String country) throws Exception {
        String browser = Utils.getBrowser();

        HomePage home = new HomePage(SESSION, browser);
        home.clickOnExploreCareersBtn();

        CareersPage careersPage = new CareersPage(SESSION,browser);
        careersPage.clickOnExploreCareersBtn();

        ExploreCareersPage exploreCareersPage = new ExploreCareersPage(SESSION,browser);
        exploreCareersPage.writeSearchBoxFilter(city);
        exploreCareersPage.selectCountry(country);

        return exploreCareersPage;
    }

    public void resultsLocation1Test() throws Exception {

        ExploreCareersPage exploreCareersPage = support(CITY1, COUNTRY);
        exploreCareersPage.clickJobSearchBtn();
        Assert.assertEquals(String.format(MessageFileToUser.WRONG_NUMBER_RESULTS),N_RESULTS_CITY1,
                exploreCareersPage.getNumberResults());
    }


    public void resultsLocation2Test() throws Exception {
        ExploreCareersPage exploreCareersPage = support(CITY2, COUNTRY);
        exploreCareersPage.clickJobSearchBtn();
        Assert.assertTrue(String.format(MessageFileToUser.WRONG_NUMBER_RESULTS),
                exploreCareersPage.getNumberResults() >= N_RESULTS_CITY2);
    }

    @Test
    public void resultsLocation3Test() throws Exception {
        ExploreCareersPage exploreCareersPage = support(CITY2, COUNTRY);
        exploreCareersPage.selectRole(ROLE);
        exploreCareersPage.clickJobSearchBtn();
        Assert.assertTrue(String.format(MessageFileToUser.WRONG_NUMBER_RESULTS),
                exploreCareersPage.getNumberResults() >= N_RESULTS_CITY3);

        exploreCareersPage.clickFirstResult();
        exploreCareersPage.findByText(QUALIFICATION_KEY);
    }

    @After
    public void tearDown() throws Exception {
        destroyLastDriver();
    }


}
