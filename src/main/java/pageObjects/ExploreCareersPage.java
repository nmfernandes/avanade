package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page class of the Explore Careers page
 * Extends PageObject class
 */
public class ExploreCareersPage extends PageObject {
    private static final String TITLE = "EXPLORE_CAREERS_TITLE";
    private static final String COUNTRIES_DROPDOWN = "Countries dropdown";
    private static final String ROLES_DROPDOWN = "Roles dropdown";
    private static final String RESULTS_COUNT = "Results count";
    private static final String RESULTS_LIST = "Results list";
    private WebElement searchboxfilter;
    private WebElement countries;
    private WebElement jobsearchclick;
    @FindBy(css = "div[class='search-meta-container']")
    private WebElement resultCount;
    private WebElement list3;
    private WebElement list4;
    @FindBy(css = "div[class='search-results ng-scope']")
    private WebElement resultList;

    public ExploreCareersPage(String session, String browser) throws Exception {
        super(session, browser);
        verifyPageOpenIsExpected(TITLE);
    }

    /**
     * Write in search box
     * @param text
     */
    public void writeSearchBoxFilter(String text) {
        searchboxfilter.sendKeys(text);
    }

    /**
     * Select the country
     * @param text
     * @throws Exception
     */
    public void selectCountry(String text) throws Exception {

        waitToBeVisible(list3, COUNTRIES_DROPDOWN);
        list3.click();
        WebElement item = findByTextInContainerGivenParentElement(text, list3, COUNTRIES_DROPDOWN);
        item.click();
    }

    /**
     * Select the role
     * @param text
     * @throws Exception
     */
    public void selectRole(String text) throws Exception {
        waitToBeVisible(list4, ROLES_DROPDOWN);
        list4.click();
        WebElement item = findByTextInContainerGivenParentElement(text, list4, ROLES_DROPDOWN);
        item.click();
    }

    /**
     * Click on the search button
     */
    public void clickJobSearchBtn() {
        jobsearchclick.click();
    }

    /**
     * Get the number of results
     * @return
     * @throws Exception
     */
    public int getNumberResults() throws Exception {
        waitToBeVisible(resultCount, RESULTS_COUNT);
        String text = resultCount.getText();
        String[] splitted = text.split(" ");
        return Integer.parseInt(splitted[0]);
    }

    /**
     * Click on the first result
     * @throws Exception
     */
    public void clickFirstResult() throws Exception {
        waitToBeVisible(resultList, RESULTS_LIST);
        WebElement link = resultList.findElement(By.xpath(".//td/a[2]"));
        link.click();
    }
}
